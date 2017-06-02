package swmm.runoff.infil
import swmm.cmigrate.SwmmConst._
/**
  * Created by ning on 11/6/15.
  */
class TCurveNum extends InfilT {
//  double        Smax            // max. infiltration capacity (ft)
//  double        regen           // infil. capacity regeneration constant (1/sec)
//  double        Tmax            // maximum inter-event time (sec)
//  //-----------------------------
//  double        S               // current infiltration capacity (ft)
//  double        F               // current cumulative infiltration (ft)
//  double        P               // current cumulative precipitation (ft)
//  double        T               // current inter-event time (sec)
//  double        Se              // current event infiltration capacity (ft)
//  double        f               // previous infiltration rate (ft/sec)
   var Smax: Double = .0
   var regen: Double = .0
   var Tmax: Double = .0
   var S: Double = .0
   var F: Double = .0
   var P: Double = .0
   var T: Double = .0
   var Se: Double = .0
   var f: Double = .0
  def initState( infil:TCurveNum)=
    //
    //  Input:   infil = ptr. to Curve Number infiltration object
    //  Output:  none
    //  Purpose: initializes state of Curve Number infiltration for a subcatchment.
    //
  {
    infil.S  = infil.Smax
    infil.P  = 0.0
    infil.F  = 0.0
    infil.T  = 0.0
    infil.Se = infil.Smax
    infil.f  = 0.0
  }
  //=============================================================================

  def  setParams( p:Array[Double]):TCurveNum =
  //
  //  Input:   infil = ptr. to Curve Number infiltration object
  //           p() = array of parameter values
  //  Output:  returns TRUE if parameters are valid, FALSE otherwise
  //  Purpose: assigns Curve Number infiltration parameters to a subcatchment.
  //
  {
    val infil = new TCurveNum
    // --- convert Curve Number to max. infil. capacity
    if ( p(0) < 10.0 ) p(0) = 10.0
    if ( p(0) > 99.0 ) p(0) = 99.0
    infil.Smax    = (1000.0 / p(0) - 10.0) / 12.0
  //  if ( infil.Smax < 0.0 ) return FALSE

    //// ---- linear regeneration constant and inter-event --- ////
    ////      time now computed directly from drying time     ////
    ////      hydraulic conductivity no longer used.           ////

    // --- convert drying time (days) to a regeneration const. (1/sec)
    if ( p(2) > 0.0 )  infil.regen =  1.0 / (p(2) * SECperDAY)
   // else return FALSE

    // --- compute inter-event time from regeneration const. as in Green-Ampt
    infil.Tmax = 0.06 / infil.regen

    infil
  }



  def  getState(infil:TCurveNum,x:Array[Double])=
  {
    x(0) = infil.S
    x(1) = infil.P
    x(2) = infil.F
    x(3) = infil.T
    x(4) = infil.Se
    x(5) = infil.f
  }

  def  setState(infil:TCurveNum,x:Array[Double])=
  {
    infil.S  = x(0)
    infil.P  = x(1)
    infil.F  = x(2)
    infil.T  = x(3)
    infil.Se = x(4)
    infil.f  = x(5)
  }

  //=============================================================================

  def getInfil(infil:TCurveNum,  tstep:Double,  irate:Double,
     depth:Double):Double =
    //
    //  Input:   infil = ptr. to Curve Number infiltration object
    //           tstep = runoff time step (sec),
    //           irate = rainfall rate (ft/sec)
    //           depth = depth of runon + ponded water (ft)
    //  Output:  returns infiltration rate (ft/sec)
    //  Purpose: computes infiltration rate using the Curve Number method.
    //  Note:    this function treats runon from other subcatchments as part
    //           of the ponded depth and not as an effective rainfall rate.
  {
    var fF1=.0                         // new cumulative infiltration (ft)
    var f1 = 0.0                   // new infiltration rate (ft/sec)
    var fa = irate + depth/tstep   // max. available infil. rate (ft/sec)

    // --- case where there is rainfall
    if ( irate > ZERO )
    {
      // --- check if new rain event
      if ( infil.T >= infil.Tmax )
      {
        infil.P = 0.0
        infil.F = 0.0
        infil.f = 0.0
        infil.Se = infil.S
      }
      infil.T = 0.0

      // --- update cumulative precip.
      infil.P += irate * tstep

      // --- find potential new cumulative infiltration
      fF1 = infil.P * (1.0 - infil.P / (infil.P + infil.Se))

      // --- compute potential infiltration rate
      f1 = (fF1 - infil.F) / tstep
      if ( f1 < 0.0 || infil.S <= 0.0 ) f1 = 0.0

    }

    // --- case of no rainfall
    else
    {
      // --- if there is ponded water then use previous infil. rate
      if ( depth > MIN_TOTAL_DEPTH && infil.S > 0.0 )
      {
        f1 = infil.f
        if ( f1*tstep > infil.S ) f1 = infil.S / tstep
      }

      // --- otherwise update inter-event time
      else infil.T += tstep
    }

    // --- if there is some infiltration
    if ( f1 > 0.0 )
    {
      // --- limit infil. rate to max. available rate
      f1 = Math.min(f1, fa)
      f1 = Math.max(f1, 0.0)

      // --- update actual cumulative infiltration
      infil.F += f1 * tstep

      // --- reduce infil. capacity if a regen. constant was supplied
      if ( infil.regen > 0.0 )
      {
        infil.S -= f1 * tstep
        if ( infil.S < 0.0 ) infil.S = 0.0
      }
    }

    // --- otherwise regenerate infil. capacity
    else
    {
      infil.S += infil.regen * infil.Smax * tstep * evap.recoveryFactor
      if ( infil.S > infil.Smax ) infil.S = infil.Smax
    }
    infil.f = f1
    f1
  }

}