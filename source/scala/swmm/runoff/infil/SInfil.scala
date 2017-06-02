package swmm.runoff.infil

import swmm.configdata.jnodes.SwmmEnum.ConversionType
//import swmm.configdata.jnodes.TExfil
import swmm.configdata.jnodes.InfilH.InfilType._
import swmm.configdata.jnodes.types.{TStorage, TExfil, TBase}

import swmm.util.{ FunctionUtil, Project }

object SInfil {
  val project = Project.getInstance
  var HortInfil: THorton = null
  var GAInfil: TGrnAmpt = null
  var CNInfil: TCurveNum = null
  val hORTON = HORTON.ordinal()
  val mOD_HORTON = MOD_HORTON.ordinal()
  val gREEN_AMPT = GREEN_AMPT.ordinal()
  val URVE_NUMBER = CURVE_NUMBER.ordinal()

  def lid_validate() = {

  }

  //=============================================================================

  def infil_readParams(tok: List[String]) =
    //
    //  Input:   m = infiltration method code
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: sets infiltration parameters from a line of input data.
    //
    //  Format of data line is:
    //     subcatch  p1  p2 ... {
    {
      var n: Int = 0
      var x = new Array[Double](5)

      // --- check that subcatchment exists
      val maybeSubcatch = project.findSubcatch(tok(0)).filter(_ != null)
      //if ( j < 0 ) return error_setInpError(ERR_NAME, tok(0))
      val iType = project.InfilModel
      // --- number of input tokens depends on infiltration model m
      if (iType == HORTON) n = 5
      else if (iType == MOD_HORTON) n = 5
      else if (iType == GREEN_AMPT) n = 4
      else if (iType == CURVE_NUMBER) n = 4

      //  if ( ntoks < n ) return error_setInpError(ERR_ITEMS, "")

      // --- parse numerical values from tokens
      //    for (i = 0 i < 5 i++) x(i) = 0.0
      for (i <- 1 to n - 1) {
        x(i - 1) = tok(i).toDouble
        //        if ( ! getDouble(tok(i), &x(i-1)) )
        //            return error_setInpError(ERR_NUMBER, tok(i))
      }

      // --- special case for Horton infil. - last parameter is optional
      if ((iType == HORTON || iType == MOD_HORTON) && tok.length > n) {
        x(n - 1) = tok(n).toDouble
        //        if ( ! getDouble(tok(n), &x(n-1)) )
        //            return error_setInpError(ERR_NUMBER, tok(n))
      }

      // --- assign parameter values to infil. object

      var status = false
      if (maybeSubcatch != None) {
        val sub = maybeSubcatch.get.asInstanceOf[TBase]
        val pos = sub.pos
        val pSub = project.Subcatch(pos)
        iType match {
          case HORTON =>
            val newInstance = THorton.setParams(x)
            project.Subcatch(sub.pos).infil = newInstance
          case MOD_HORTON =>
            project.Subcatch(sub.pos).infil = THorton.setParams(x)

          case GREEN_AMPT =>
            project.Subcatch(sub.pos).infil = GAInfil.setParams(x)

          case CURVE_NUMBER =>
            project.Subcatch(sub.pos).infil = CNInfil.setParams(x)

        }

      }
    }
  //=============================================================================
  def createStorageExfil( x: Array[Double],tNode:TStorage): Int =
  //
  //  Input:   k = index of storage unit node
  //           x = array of Green-Ampt infiltration parameters
  //  Output:  returns an error code.
  //  Purpose: creates an exfiltration object for a storage node.
  //
  //  Note: the exfiltration object is freed in project.c.
  //
  {

    // --- create an exfiltration object for the storage node
    var exfil = tNode.exfil
    if (exfil == null) {
      exfil = new TExfil

      tNode.exfil = exfil

      // --- create Green-Ampt infiltration objects for the bottom & banks
      //        exfil->btmExfil = NULL
      //        exfil->bankExfil = NULL
      exfil.btmExfil = new TGrnAmpt
      exfil.bankExfil = new TGrnAmpt
    }

    // --- initialize the Green-Ampt parameters
    grnampt_setParams(exfil.btmExfil, x)
    grnampt_setParams(exfil.bankExfil, x)
    return 0
  }
  //=============================================================================
  def exfil_readStorageParams( tok: Array[String], n: Int,tNode:TStorage) =
  //
  //  Input:   k = storage unit index
  //           tok[] = array of string tokens
  //           ntoks = number of tokens
  //           n = last token processed
  //  Output:  returns an error code
  //  Purpose: reads a storage unit's exfiltration parameters from a
  //           tokenized line of input.
  //
  {

    //double    x[3]    //suction head, Ksat, IMDmax
    val x: Array[Double] = Array.fill(3)(0)
    // --- read Ksat if it's the only remaining token
    if (tok.length == n + 1) {
      x(1) = tok(n).toDouble
      x(0) = 0.0
      x(2) = 0.0
    } // --- otherwise read Green-Ampt infiltration parameters from input tokens
    //  else if ( tok.length < n + 3 ) return error_setInpError(ERR_ITEMS, "")
    else for (i <- 0 to 2) {
      x(i) = tok(n + i).toDouble
      //        if ( ! getDouble(tok[n+i], &x[i]) )
      //            return error_setInpError(ERR_NUMBER, tok[n+i])
    }

    // --- no exfiltration if Ksat is 0
    if (x(1) != 0.0)
    // --- create an exfiltration object
     createStorageExfil( x,tNode)
  }


  def grnampt_setParams(infil: TGrnAmpt, p: Array[Double]) =
    //
    //  Input:   infil = ptr. to Green-Ampt infiltration object
    //           p[] = array of parameter values
    //  Output:  returns TRUE if parameters are valid, FALSE otherwise
    //  Purpose: assigns Green-Ampt infiltration parameters to a subcatchment.
    //
    {
      var ksat: Double = .0 // sat. hyd. conductivity in in/hr

      if (p(0) < 0.0 || p(1) <= 0.0 || p(2) < 0.0)
      {
        //Do nothing
      }//(5.1.007)
      else
      {
        infil.S = p(0) / FunctionUtil.UCF(ConversionType.RAINDEPTH.ordinal) // Capillary suction head (ft)
        infil.Ks = p(1) / FunctionUtil.UCF(ConversionType.RAINFALL.ordinal) // Sat. hyd. conductivity (ft/sec)
        infil.IMDmax = p(2) // Max. init. moisture deficit

        // --- find depth of upper soil zone (ft) using Mein's eqn.
        ksat = infil.Ks * 12.0 * 3600.0
        infil.Lu = 4.0 * Math.sqrt(ksat) / 12.0

      }

    }

  def infil_create(subcatchCount: Int, model: Int): InfilT = model match //
  //  Purpose: creates an array of infiltration objects.
  //  Input:   n = number of subcatchments
  //           m = infiltration method code
  //  Output:  none
  {

    case `hORTON` =>
      HortInfil = new THorton()
      HortInfil
    case `mOD_HORTON` =>
      HortInfil = new THorton()
      HortInfil
    case `gREEN_AMPT` =>
      GAInfil = new TGrnAmpt()
      GAInfil
    case cURVE_NUMBER =>
      CNInfil = new TCurveNum()
      CNInfil

    //default: ErrorCode = ERR_MEMORY

  }

}

/**
 *
 * Created by Ning on 11/4/15.
 */
class SInfil {

}
