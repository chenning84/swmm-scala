package swmm.runoff.infil
import swmm.cmigrate.SwmmConst._
import swmm.configdata.jnodes.{SwmmEnum, GlobalContext}
import swmm.util.FunctionUtil._
import swmm.util.Project

import scala.util.control.Breaks

/**
  * Created by Ning on 11/6/15.
  */
//---------------------
// Horton Infiltration
//---------------------
object THorton extends InfilT
{
  def setParams(  p:Array[Double]):THorton =
  //
  //  Input:   infil = ptr. to Horton infiltration object
  //           p[] = array of parameter values
  //  Output:  returns TRUE if parameters are valid, FALSE otherwise
  //  Purpose: assigns Horton infiltration parameters to a subcatchment.
  //
  {
    val     infil = new THorton
    //  if(!p.forall(_<0.0)) return 0

    // --- max. & min. infil rates (ft/sec)
    infil.f0   = p(0) / UCF(RAINFALL)
    infil.fmin = p(1) / UCF(RAINFALL)

    // --- convert decay const. to 1/sec
    infil.decay = p(2) / 3600.0

    // --- convert drying time (days) to a regeneration const. (1/sec)
    //     assuming that former is time to reach 98% dry along an
    //     exponential drying curve
    if (p(3) == 0.0 ) p(3) = TINY
    infil.regen = -Math.log(1.0-0.98) / p(3) / SECperDAY

    // --- optional max. infil. capacity (ft) (p(4) = 0 if no value supplied)
    infil.Fmax = p(4) / UCF(RAINDEPTH)
    //  if ( infil.f0 < infil.fmin ) return FALSE
    infil
  }

  //=============================================================================

  def  initState(infil:THorton)=
  //
  //  Input:   infil = ptr. to Horton infiltration object
  //  Output:  none
  //  Purpose: initializes time on Horton infiltration curve for a subcatchment.
  //
  {
    infil.tp = 0.0
    infil.Fe = 0.0
  }

  //=============================================================================

  def  getState(infil:THorton, x:Array[Double])=
  {
    x(0) = infil.tp
    x(1) = infil.Fe
  }

  def  setState(infil:THorton, x:Array[Double])=
  {
    infil.tp = x(0)
    infil.Fe = x(1)
  }

  //=============================================================================

  def getInfil(infil:THorton,  tstep:Double,  irate:Double,  depth:Double) :Double =

  //
  //  Input:   infil = ptr. to Horton infiltration object
  //           tstep =  runoff time step (sec),
  //           irate = net "rainfall" rate (ft/sec),
  //                 = rainfall + snowmelt + runon - evaporation
  //           depth = depth of ponded water (ft).
  //  Output:  returns infiltration rate (ft/sec)
  //  Purpose: computes Horton infiltration for a subcatchment.
  //
  {

    // --- assign local variables
    // var    iter=0
    var fa, fp = 0.0
    var Fp, F1, t1, tlim, ex, kt=.0
    var FF, FF1, r =.0
    var f0   = infil.f0 * adjust.hydconFactor                             //(5.1.008)
  var fmin = infil.fmin * adjust.hydconFactor                           //(5.1.008)
  var Fmax = infil.Fmax
    var tp   = infil.tp
    var df   = f0 - fmin                                                   //(5.1.008)
  var kd   = infil.decay
    var kr   = infil.regen * evap.recoveryFactor

    // --- special cases of no infil. or constant infil
    if ( df < 0.0 || kd < 0.0 || kr < 0.0 ) return 0.0
    if ( df == 0.0 || kd == 0.0 )
    {
      fp = f0                                                               //(5.1.008)
      fa = irate + depth / tstep
      if ( fp > fa ) fp = fa
      return Math.max(0.0, fp)
    }

    // --- compute water available for infiltration
    fa = irate + depth / tstep

    // --- case where there is water to infiltrate
    if ( fa > ZERO )
    {
      // --- compute average infil. rate over time step
      t1 = tp + tstep         // future cumul. time
      tlim = 16.0 / kd        // for tp >= tlim, f = fmin
      if ( tp >= tlim )
      {
        Fp = fmin * tp + df / kd
        F1 = Fp + fmin * tstep
      }
      else
      {
        Fp = fmin * tp + df / kd * (1.0 - Math.exp(-kd * tp))
        F1 = fmin * t1 + df / kd * (1.0 - Math.exp(-kd * t1))
      }
      fp = (F1 - Fp) / tstep

      // --- limit infil rate to available infil
      if ( fp > fa ) fp = fa

      // --- if fp on flat portion of curve then increase tp by tstep
      if ( t1 > tlim ) tp = t1

      // --- if infil < available capacity then increase tp by tstep
      else if ( fp < fa ) tp = t1

      // --- if infil limited by available capacity then
      //     solve F(tp) - F1 = 0 using Newton-Raphson method
      else
      {
        F1 = Fp + fp * tstep
        tp = tp + tstep / 2.0
        def testFunc: Double = {
          kt = Math.min(60.0, kd * tp)
          ex = Math.exp(-kt)
          FF = fmin * tp + df / kd * (1.0 - ex) - F1
          FF1 = fmin + df * ex
          r = FF / FF1
          tp = tp - r
          r
        }

        val loop = new Breaks;
        loop.breakable {
          for ( iter <- 1 to 20 )
          {
            testFunc
            if ( Math.abs(testFunc) <= 0.001 * tstep ) loop.break
          }

        }

      }

      // --- limit cumulative infiltration to Fmax
      if ( Fmax > 0.0 )
      {
        if ( infil.Fe + fp * tstep > Fmax )
          fp = (Fmax - infil.Fe) / tstep
        fp = Math.max(fp, 0.0)
        infil.Fe += fp * tstep
      }
    }

    // --- case where infil. capacity is regenerating update tp.
    else if (kr > 0.0)
    {
      r = Math.exp(-kr * tstep)
      tp = 1.0 - Math.exp(-kd * tp)
      tp = -Math.log(1.0 - r*tp) / kd

      // reduction in cumulative infiltration
      if ( Fmax > 0.0 )
      {
        infil.Fe = fmin*tp + (df/kd)*(1.0 - Math.exp(-kd*tp))
      }
    }
    infil.tp = tp
    fp
  }

}
class THorton extends InfilT
{

  var        f0:Double =0.0              // initial infil. rate (ft/sec)
  var        fmin:Double =0.0            // minimum infil. rate (ft/sec)
  var        Fmax:Double =0.0            // maximum total infiltration (ft):Double =0.0
  var        decay:Double =0.0           // decay coeff. of infil. rate (1/sec)
  var        regen:Double =0.0           // regeneration coeff. of infil. rate (1/sec)
  //-----------------------------
  var        tp:Double =0.0              // present time on infiltration curve (sec)

  var        Fe:Double=.0              // cumulative infiltration (ft)
  //=============================================================================
  val        globalContext = Project.getInstance


}
