package swmm.configdata.types

import swmm.configdata.jnodes.SwmmEnum.XsectType
import swmm.configdata.jnodes.types.{TShape, TTransect, TXsect}
import swmm.cmigrate.SwmmConst
import swmm.configdata.jnodes.XSectDat._
import swmm.util.{FindRoot, Project}

/**
  * Created by ning on 1/2/16.
  */
object TXSectUtil {
  val project = Project.getInstance
  val  Amax:Array[Double] = Array (
    1.0,     //  DUMMY
    0.9756,  //  CIRCULAR
    0.9756,  //  FILLED_CIRCULAR
    0.97,    //  RECT_CLOSED
    1.0,     //  RECT_OPEN
    1.0,     //  TRAPEZOIDAL
    1.0,     //  TRIANGULAR
    1.0,     //  PARABOLIC
    1.0,     //  POWERFUNC
    0.98,    //  RECT_TRIANG
    0.98,    //  RECT_ROUND
    0.96,    //  MOD_BASKET
    0.96,    //  HORIZ_ELLIPSE
    0.96,    //  VERT_ELLIPSE
    0.92,    //  ARCH
    0.96,    //  EGGSHAPED
    0.96,    //  HORSESHOE
    0.96,    //  GOTHIC
    0.98,    //  CATENARY
    0.98,    //  SEMIELLIPTICAL
    0.96,    //  BASKETHANDLE
    0.96,    //  SEMICIRCULAR
    1.0,     //  IRREGULAR
    0.96,    //  CUSTOM
    0.9756) //  FORCE_MAIN
  //=============================================================================
  val  RECT_ALFMAX:Double =        0.97
  val  RECT_TRIANG_ALFMAX:Double = 0.98
  val  RECT_ROUND_ALFMAX:Double =  0.98
  def xsect_setParams(xsect:TXsect, sType:String, p:Array[Double], ucf:Double ):Boolean=
  //
  //  Input:   xsect = ptr. to a cross section data structure
  //           type = xsection shape type
  //           p[] = vector or xsection parameters
  //           ucf = units correction factor
  //  Output"=>s TRUE if successful, false if not
  //  Purpose: assigns parameters to a cross section's data structure.
  //
  {
    //int    index
    var aMax, theta:Double =.0

   // if ( type != DUMMY && p(0) <= 0.0 ) return false
    xsect.sType  = sType
     xsect.sType match
    {
      case "DUMMY"=>
      xsect.yFull = SwmmConst.TINY
      xsect.wMax  = SwmmConst.TINY
      xsect.aFull = SwmmConst.TINY
      xsect.rFull = SwmmConst.TINY
      xsect.sFull = SwmmConst.TINY
      xsect.sMax  = SwmmConst.TINY
     

      case "CIRCULAR" =>
      xsect.yFull = p(0)/ucf
      xsect.wMax  = xsect.yFull
      xsect.aFull = SwmmConst.PI / 4.0 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.2500 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.08 * xsect.sFull
      xsect.ywMax = 0.5 * xsect.yFull
      

      case "FORCE_MAIN"=>
      xsect.yFull = p(0)/ucf
      xsect.wMax  = xsect.yFull
      xsect.aFull = SwmmConst.PI / 4.0 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.2500 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 0.63)
      xsect.sMax  = 1.06949 * xsect.sFull
      xsect.ywMax = 0.5 * xsect.yFull

      // --- save C-factor or roughness in rBot position
      xsect.rBot  = p(1)
      

      case "FILLED_CIRCULAR"=>
      if ( p(1) >= p(0) ) return false

      // --- initially compute full values for unfilled pipe
      xsect.yFull = p(0)/ucf
      xsect.wMax  = xsect.yFull
      xsect.aFull = SwmmConst.PI / 4.0 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.2500 * xsect.yFull

      // --- find:
      //     yBot = depth of filled bottom
      //     aBot = area of filled bottom
      //     sBot = width of filled bottom
      //     rBot = wetted perimeter of filled bottom
      xsect.yBot  = p(1)/ucf
      xsect.aBot  = circ_getAofY(xsect, xsect.yBot)
      xsect.sBot  = xsect_getWofY(xsect, xsect.yBot)
      xsect.rBot  = xsect.aBot / (xsect.rFull *
        lookup(xsect.yBot/xsect.yFull, R_Circ, N_R_Circ))

      // --- revise full values for filled bottom
      xsect.aFull -= xsect.aBot
      xsect.rFull = xsect.aFull /
        (SwmmConst.PI*xsect.yFull - xsect.rBot + xsect.sBot)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.08 * xsect.sFull
      xsect.yFull -= xsect.yBot
      xsect.ywMax = 0.5 * xsect.yFull
      

      case "EGGSHAPED"=>
      xsect.yFull = p(0)/ucf
      xsect.aFull = 0.5105 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.1931 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.065 * xsect.sFull
      xsect.wMax  = 2.0/3.0 * xsect.yFull
      xsect.ywMax = 0.64 * xsect.yFull
      

      case "HORSESHOE" =>
      xsect.yFull = p(0)/ucf
      xsect.aFull = 0.8293 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.2538 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.077 * xsect.sFull
      xsect.wMax  = 1.0 * xsect.yFull
      xsect.ywMax = 0.5 * xsect.yFull
      

      case "GOTHIC" =>
      xsect.yFull = p(0)/ucf
      xsect.aFull = 0.6554 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.2269 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.065 * xsect.sFull
      xsect.wMax  = 0.84 * xsect.yFull
      xsect.ywMax = 0.45 * xsect.yFull
      

      case "CATENARY"=>
      xsect.yFull = p(0)/ucf
      xsect.aFull = 0.70277 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.23172 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.05 * xsect.sFull
      xsect.wMax  = 0.9 * xsect.yFull
      xsect.ywMax = 0.25 * xsect.yFull
      

      case "SEMIELLIPTICAL"=>
      xsect.yFull = p(0)/ucf
      xsect.aFull = 0.785 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.242 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.045 * xsect.sFull
      xsect.wMax  = 1.0 * xsect.yFull
      xsect.ywMax = 0.15 * xsect.yFull
      

      case "BASKETHANDLE"=>
      xsect.yFull = p(0)/ucf
      xsect.aFull = 0.7862 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.2464 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.06078 * xsect.sFull
      xsect.wMax  = 0.944 * xsect.yFull
      xsect.ywMax = 0.2 * xsect.yFull
      

      case "SEMICIRCULAR"=>
      xsect.yFull = p(0)/ucf
      xsect.aFull = 1.2697 * xsect.yFull * xsect.yFull
      xsect.rFull = 0.2946 * xsect.yFull
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = 1.06637 * xsect.sFull
      xsect.wMax  = 1.64 * xsect.yFull
      xsect.ywMax = 0.15 * xsect.yFull
      

      case "RECT_CLOSED" =>
      if ( p(1) <= 0.0 ) return false
      xsect.yFull = p(0)/ucf
      xsect.wMax  = p(1)/ucf
      xsect.aFull = xsect.yFull * xsect.wMax
      xsect.rFull = xsect.aFull / (2.0 * (xsect.yFull + xsect.wMax))
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      aMax = RECT_ALFMAX * xsect.aFull
      xsect.sMax = aMax * Math.pow(rect_closed_getRofA(xsect, aMax), 2.0/3.0)
      xsect.ywMax = xsect.yFull
      

      case "RECT_OPEN" =>
      if ( p(1) <= 0.0 ) return false
      xsect.yFull = p(0)/ucf
      xsect.wMax  = p(1)/ucf
      if (p(2) < 0.0 || p(2) > 2.0) return false   //# sides to ignore
      xsect.sBot = p(2)
      xsect.aFull = xsect.yFull * xsect.wMax
      xsect.rFull = xsect.aFull / ((2.0 - xsect.sBot) *
        xsect.yFull + xsect.wMax)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = xsect.sFull
      xsect.ywMax = xsect.yFull
      

      case "RECT_TRIANG" =>
      if ( p(1) <= 0.0 || p(2) <= 0.0 ) return false
      xsect.yFull = p(0)/ucf
      xsect.wMax  = p(1)/ucf
      xsect.yBot  = p(2)/ucf
      xsect.ywMax = xsect.yFull

      // --- area of bottom triangle
      xsect.aBot  = xsect.yBot * xsect.wMax / 2.0

      // --- slope of bottom side wall
      xsect.sBot  = xsect.wMax / xsect.yBot / 2.0

      // --- length of side wall per unit of depth
      xsect.rBot  = Math.sqrt( 1.0 + xsect.sBot * xsect.sBot )

      xsect.aFull = xsect.wMax * (xsect.yFull - xsect.yBot / 2.0)
      xsect.rFull = xsect.aFull / (2.0 * xsect.yBot * xsect.rBot + 2.0 *
        (xsect.yFull - xsect.yBot) + xsect.wMax)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      aMax = RECT_TRIANG_ALFMAX * xsect.aFull
      xsect.sMax  = aMax * Math.pow(rect_triang_getRofA(xsect, aMax), 2.0/3.0)
      

      case "RECT_ROUND" =>
      if ( p(1) <= 0.0 ) return false
      if ( p(2) < p(1)/2.0 ) p(2) = p(1)/2.0
      xsect.yFull = p(0)/ucf
      xsect.wMax  = p(1)/ucf
      xsect.rBot  = p(2)/ucf

      // --- angle of circular arc
      theta = 2.0 * Math.asin(xsect.wMax / 2.0 / xsect.rBot)

      // --- area of circular bottom
      xsect.aBot  = xsect.rBot * xsect.rBot /
        2.0 * (theta - Math.sin(theta))

      // --- section factor for circular bottom
      xsect.sBot  = SwmmConst.PI * xsect.rBot * xsect.rBot *
        Math.pow(xsect.rBot/2.0, 2.0/3.0)

      // --- depth of circular bottom
      xsect.yBot  = xsect.rBot * (1.0 - Math.cos(theta/2.0))
      xsect.ywMax = xsect.yFull

      xsect.aFull = xsect.wMax * (xsect.yFull - xsect.yBot) + xsect.aBot
      xsect.rFull = xsect.aFull / (xsect.rBot * theta + 2.0 *
        (xsect.yFull - xsect.yBot) + xsect.wMax)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      aMax = RECT_ROUND_ALFMAX * xsect.aFull
      xsect.sMax = aMax * Math.pow(rect_round_getRofA(xsect, aMax), 2.0/3.0)
      

      case "MOD_BASKET" =>
      if ( p(1) <= 0.0 ) return false
      if ( p(2) < p(1)/2.0 ) p(2) = p(1)/2.0
      xsect.yFull = p(0)/ucf
      xsect.wMax  = p(1)/ucf

      // --- radius of circular arc
      xsect.rBot = p(2)/ucf

      // --- angle of circular arc
      theta = 2.0 * Math.asin(xsect.wMax / 2.0 / xsect.rBot)
      xsect.sBot = theta

      // --- height of circular arc
      xsect.yBot = xsect.rBot * (1.0 - Math.cos(theta/2.0))
      xsect.ywMax = xsect.yBot

      // --- area of circular arc
      xsect.aBot = xsect.rBot * xsect.rBot /
        2.0 * (theta - Math.sin(theta))

      // --- full area
      xsect.aFull = (xsect.yFull - xsect.yBot) * xsect.wMax +
        xsect.aBot

      // --- full hydraulic radius & section factor
      xsect.rFull = xsect.aFull / (xsect.rBot * theta + 2.0 *
        (xsect.yFull - xsect.yBot) + xsect.wMax)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)

      // --- area corresponding to max. section factor
      xsect.sMax = xsect_getSofA(xsect, Amax(XsectType.MOD_BASKET.ordinal())*xsect.aFull)
      

      case "TRAPEZOIDAL" =>
      if ( p(1) < 0.0 || p(2) < 0.0 || p(3) < 0.0 ) return false
      xsect.yFull = p(0)/ucf
      xsect.ywMax = xsect.yFull

      // --- bottom width
      xsect.yBot = p(1)/ucf

      // --- avg. slope of side walls
      xsect.sBot  = ( p(2) + p(3) )/2.0
      if ( xsect.yBot == 0.0 && xsect.sBot == 0.0 ) return false

      // --- length of side walls per unit of depth
      xsect.rBot  = Math.sqrt( 1.0 + p(2)*p(2) ) + Math.sqrt( 1.0 + p(3)*p(3) )

      // --- top width
      xsect.wMax = xsect.yBot + xsect.yFull * (p(2) + p(3))

      xsect.aFull = ( xsect.yBot + xsect.sBot * xsect.yFull ) * xsect.yFull
      xsect.rFull = xsect.aFull / (xsect.yBot + xsect.yFull * xsect.rBot)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = xsect.sFull
      

      case "TRIANGULAR" =>
      if ( p(1) <= 0.0 ) return false
      xsect.yFull = p(0)/ucf
      xsect.wMax  = p(1)/ucf
      xsect.ywMax = xsect.yFull

      // --- slope of side walls
      xsect.sBot  = xsect.wMax / xsect.yFull / 2.0

      // --- length of side wall per unit of depth
      xsect.rBot  = Math.sqrt( 1.0 + xsect.sBot * xsect.sBot )

      xsect.aFull = xsect.yFull * xsect.yFull * xsect.sBot
      xsect.rFull = xsect.aFull / (2.0 * xsect.yFull * xsect.rBot)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = xsect.sFull
      

      case "PARABOLIC" =>
      if ( p(1) <= 0.0 ) return false
      xsect.yFull = p(0)/ucf
      xsect.wMax  = p(1)/ucf
      xsect.ywMax = xsect.yFull

      // --- rBot " =>" => 1/c^.5, where y = c*x^2 is eqn. of parabolic shape
      xsect.rBot  = xsect.wMax / 2.0 / Math.sqrt(xsect.yFull)

      xsect.aFull = (2.0/3.0) * xsect.yFull * xsect.wMax
      xsect.rFull = xsect_getRofY(xsect, xsect.yFull)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = xsect.sFull
      

      case "POWERFUNC" =>
      if ( p(1) <= 0.0 || p(2) <= 0.0 ) return false
      xsect.yFull = p(0)/ucf
      xsect.wMax  = p(1)/ucf
      xsect.ywMax = xsect.yFull
      xsect.sBot  = 1.0 / p(2)
      xsect.rBot  = xsect.wMax / (xsect.sBot + 1) /
        Math.pow(xsect.yFull, xsect.sBot)
      xsect.aFull = xsect.yFull * xsect.wMax / (xsect.sBot+1)
      xsect.rFull = xsect_getRofY(xsect, xsect.yFull)
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = xsect.sFull
      

      case "HORIZ_ELLIPSE" =>
      if ( p(1) == 0.0 ) p(2) = p(0)                                        //(5.1.008)
      if ( p(2) > 0.0 )                        // std. ellipse pipe          //(5.1.008)
      {
        val index = Math.floor(p(2)).toInt - 1        // size code                  //(5.1.008)
        if ( index < 0 ||
          index >= NumCodesEllipse ) return false
        xsect.yFull = MinorAxis_Ellipse(index)/12.0
        xsect.wMax  = MajorAxis_Ellipse(index)/12.0
        xsect.aFull = Afull_Ellipse(index)
        xsect.rFull = Rfull_Ellipse(index)
      }
      else
      {
        // --- length of minor axis
        xsect.yFull = p(0)/ucf

        // --- length of major axis
        if ( p(1) < 0.0 ) return false
        xsect.wMax = p(1)/ucf
        xsect.aFull = 1.2692 * xsect.yFull * xsect.yFull
        xsect.rFull = 0.3061 * xsect.yFull
      }
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = xsect.sFull
      xsect.ywMax = 0.48 * xsect.yFull
      

      case "VERT_ELLIPSE" =>
      if ( p(1) == 0.0 ) p(2) = p(0)                                        //(5.1.008)
      if ( p(2) > 0.0 )                        // std. ellipse pipe          //(5.1.008)
      {
        val index = Math.floor(p(2)).toInt - 1        // size code                  //(5.1.008)
        if ( index < 0 ||
          index >= NumCodesEllipse ) return false
        xsect.yFull = MajorAxis_Ellipse(index)/12.0
        xsect.wMax  = MinorAxis_Ellipse(index)/12.0
        xsect.aFull = Afull_Ellipse(index)
        xsect.rFull = Rfull_Ellipse(index)
      }
      else
      {
        // --- length of major axis
        if ( p(1) < 0.0 ) return false

        // --- length of minor axis
        xsect.yFull = p(0)/ucf
        xsect.wMax = p(1)/ucf
        xsect.aFull = 1.2692 * xsect.wMax * xsect.wMax
        xsect.rFull = 0.3061 * xsect.wMax
      }
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = xsect.sFull
      xsect.ywMax = 0.48 * xsect.yFull
      

      case "ARCH" =>
      if ( p(1) == 0.0 ) p(2) = p(0)                                        //(5.1.008)
      if ( p(2) > 0.0 )                        // std. arch pipe             //(5.1.008)
      {
        val index = Math.floor(p(2)).toInt - 1        // size code                  //(5.1.008)
        if ( index < 0 ||
          index >= NumCodesArch ) return false
        xsect.yFull = Yfull_Arch(index)/12.0     // Yfull units are inches
        xsect.wMax  = Wmax_Arch(index)/12.0      // Wmax units are inches
        xsect.aFull = Afull_Arch(index)
        xsect.rFull = Rfull_Arch(index)
      }
      else                                     // non-std. arch pipe
      {
        if ( p(1) < 0.0 ) return false
        xsect.yFull = p(0)/ucf
        xsect.wMax  = p(1)/ucf
        xsect.aFull = 0.7879 * xsect.yFull * xsect.wMax
        xsect.rFull = 0.2991 * xsect.yFull
      }
      xsect.sFull = xsect.aFull * Math.pow(xsect.rFull, 2.0/3.0)
      xsect.sMax  = xsect.sFull
      xsect.ywMax = 0.28 * xsect.yFull
      
    }
    true
  }

  def circ_getAofY(xsect:TXsect , y:Double):Double =
  {

    val yNorm = y / xsect.yFull
    xsect.aFull * lookup(yNorm, A_Circ, N_A_Circ)
  }
  //=============================================================================
  //  FILLED_CIRCULAR functions
  //=============================================================================

  def filled_circ_getYofA(xsect:TXsect, a:Double) :Double=
  {
    var y:Double=0

    // --- temporarily remove filled portion of circle
    xsect.yFull += xsect.yBot
    xsect.aFull += xsect.aBot
    val b = a + xsect.aBot

    // --- find depth in unfilled circle
    y = circ_getYofA(xsect, b)

    // --- restore original values
    y -= xsect.yBot
    xsect.yFull -= xsect.yBot
    xsect.aFull -= xsect.aBot
     y
  }

  def filled_circ_getAofY(xsect:TXsect, y:Double):Double =
  {
    var a:Double=.0

    // --- temporarily remove filled portion of circle
    xsect.yFull += xsect.yBot
    xsect.aFull += xsect.aBot
    val yy =y+ xsect.yBot

    // --- find area of unfilled circle
    a = circ_getAofY(xsect, yy)

    // --- restore original values
    a -= xsect.aBot
    xsect.yFull -= xsect.yBot
    xsect.aFull -= xsect.aBot
     a
  }

  def filled_circ_getRofY(xsect:TXsect, y:Double):Double=
  {
    var a, r, p:Double =.0

    // --- temporarily remove filled portion of circle
    xsect.yFull += xsect.yBot
    xsect.aFull += xsect.aBot
    val yy =y+ xsect.yBot

    // --- get area,  hyd. radius & wetted perimeter of unfilled circle
    a = circ_getAofY(xsect, yy)
    r = 0.25 * xsect.yFull * lookup(y/xsect.yFull, R_Circ, N_R_Circ)
    p = a/r

    // --- reduce area and wetted perimeter by amount of filled circle
    //     (rBot = filled perimeter, sBot = filled width)
    a = a - xsect.aBot
    p = p - xsect.rBot + xsect.sBot

    // --- compute actual hyd. radius & restore xsect parameters
    r = a / p
    xsect.yFull -= xsect.yBot
    xsect.aFull -= xsect.aBot
     r
  }


  //=============================================================================
  //  Special functions for circular cross sections
  //=============================================================================

  def getYcircular(alpha:Double):Double=
  {
    var theta:Double =.0
    if ( alpha >= 1.0 ) return 1.0
    if ( alpha <= 0.0 ) return 0.0
    if ( alpha <= 1.0e-5 )
    {
      theta = Math.pow(37.6911*alpha, 1.0/3.0)
      return theta * theta / 16.0
    }
    theta = getThetaOfAlpha(alpha)
    (1.0 - Math.cos(theta/2.0)) / 2.0
  }

  def getScircular(alpha:Double):Double =
  {
    var theta:Double=0
    if ( alpha >= 1.0 ) return 1.0
    if ( alpha <= 0.0 ) return 0.0
    if ( alpha <= 1.0e-5 )
    {
      theta = Math.pow(37.6911*alpha, 1.0/3.0)
      return Math.pow(theta, 13.0/3.0) / 124.4797
    }
    theta = getThetaOfAlpha(alpha)
    Math.pow(theta - Math.sin(theta), 5.0/3.0) /((2.0 * SwmmConst.PI) / Math.pow(theta, 2.0/3.0))
  }

  def getAcircular(psi:Double):Double=
  {
    var theta:Double=0
    if ( psi >= 1.0 ) return 1.0
    if ( psi <= 0.0 ) return 0.0
    if ( psi <= 1.0e-6 )
    {
      theta = Math.pow(124.4797*psi, 3.0/13.0)
      return theta*theta*theta / 37.6911
    }
    theta = getThetaOfPsi(psi)
    (theta - Math.sin(theta)) / (2.0 * SwmmConst.PI)
  }

  def getThetaOfAlpha(alpha:Double):Double=
  {
//    int    k
    var theta, theta1, ap, d:Double=.0

    if ( alpha > 0.04 ) theta = 1.2 + 5.08 * (alpha - 0.04) / 0.96
    else theta = 0.031715 - 12.79384 * alpha + 8.28479 * Math.sqrt(alpha)
    theta1 = theta
    ap  = (2.0*SwmmConst.PI) * alpha
    for (k <- 1 to 40)
    {
      d = - (ap - theta + Math.sin(theta)) / (1.0 - Math.cos(theta))
      // --- modification to improve convergence for large theta
      if ( d > 1.0 ) d = FindRoot.SIGN( 1.0, d )
      theta = theta - d
      if ( Math.abs(d) <= 0.0001 )  theta
    }
     theta1
  }

  def  getThetaOfPsi(psi:Double):Double=
  {
    //int    k
    var theta, theta1, ap, tt, tt23, t3, d:Double =.0

    if      (psi > 0.90)  theta = 4.17 + 1.12 * (psi - 0.90) / 0.176
    else if (psi > 0.5)   theta = 3.14 + 1.03 * (psi - 0.5) / 0.4
    else if (psi > 0.015) theta = 1.2 + 1.94 * (psi - 0.015) / 0.485
    else                  theta = 0.12103 - 55.5075 * psi +
      15.62254 * Math.sqrt(psi)
    theta1 = theta
    ap     = (2.0*SwmmConst.PI) * psi

    for (k <- 1 to 40)
    {
      theta    = Math.abs(theta)
      tt       = theta - Math.sin(theta)
      tt23     = Math.pow(tt, 2.0/3.0)
      t3       = Math.pow(theta, 1.0/3.0)
      d        = ap * theta / t3 - tt * tt23
      d        = d / ( ap*(2.0/3.0)/t3 - (5.0/3.0)*tt23*(1.0-Math.cos(theta)) )
      theta    = theta - d
      if ( Math.abs(d) <= 0.0001 ) return theta
    }
    theta1
  }
//  def rect_closed_getdSdA(xsect:TXsect,  a:Double):Double =
//  {
//    var alpha, alfMax, r:Double =.0
//
//    // --- if above level corresponding to sMax, then
//    //     use slope between sFull & sMax
//    alfMax = RECT_ALFMAX
//    alpha = a / xsect.aFull
//    if ( alpha > alfMax )
//    {
//      return (xsect.sFull - xsect.sMax) /
//        ((1.0 - alfMax) * xsect.aFull)
//    }
//
//    // --- for small a/aFull use generic central difference formula
//    if ( alpha <= 1.0e-30 ) return generic_getdSdA(xsect, a)
//
//    // --- otherwise evaluate dSdA = [5/3 - (2/3)(dP/dA)R]R^(2/3)
//    //     (where P = wetted perimeter & dPdA = 2/width)
//    r = xsect_getRofA(xsect, a)
//    (5.0/3.0 - (2.0/3.0) * (2.0/xsect.wMax) * r) * Math.pow(r, 2.0/3.0)
//  }

//  def rect_closed_getRofA(xsect:TXsect, a:Double):Double =
//  {
//    var p:Double =.0
//    if ( a <= 0.0 )   return 0.0
//    p = xsect.wMax + 2.0*a/xsect.wMax // Wetted Perim = width + 2*area/width
//    if ( a/xsect.aFull > RECT_ALFMAX )
//    {
//      p += (a/xsect.aFull - RECT_ALFMAX) / (1.0 - RECT_ALFMAX) * xsect.wMax
//    }
//    a / p
//  }

  def generic_getdSdA(xsect:TXsect,a:Double):Double=
    //
    //  Input:   xsect = ptr. to cross section data structure
    //           a = area (ft2)
    //  Output"=>s derivative of section factor w.r.t. area (ft^2/3)
    //  Purpose: computes derivative of section factor w.r.t area
    //           using central difference approximation.
    //
  {
    var a1, a2:Double=.0
    val alpha = a / xsect.aFull
    var alpha1 = alpha - 0.001
    val alpha2 = alpha + 0.001
    if ( alpha1 < 0.0 ) 
      alpha1 = 0.0
    a1 = alpha1 * xsect.aFull
    a2 = alpha2 * xsect.aFull
     (xsect_getSofA(xsect, a2) - xsect_getSofA(xsect, a1)) / (a2 - a1)
  }

  //=============================================================================

  def lookup(x:Double, table:Array[Double], nItems:Int):Double =
    //
    //  Input:   x = value of independent variable in a geometry table
    //           table = ptr. to geometry table
    //           nItems = number of equally spaced items in table
    //  Output"=>s value of dependent table variable
    //  Purpose: looks up a value in a geometry table (i.e., finds y given x).
    //
  {
    var  delta, x0, x1, y, y2:Double =.0
   // int     i

    // --- find which segment of table contains x
    delta = 1.0 / (nItems-1)
     val i = (x / delta).toInt
    if ( i >= nItems - 1 ) return table(nItems-1)

    // --- compute x at start and end of segment
    x0 = i * delta
    x1 = (i+1) * delta

    // --- linearly interpolate a y-value
    y = table(i) + (x - x0) * (table(i+1) - table(i)) / delta

    // --- use quadratic interpolation for low x value
    if ( i < 2 )
    {
      y2 = y + (x - x0) * (x - x1) / (delta*delta) *
        (table(i)/2.0 - table(i+1) + table(i+2)/2.0) 
      if ( y2 > 0.0 ) y = y2
    }
    if ( y < 0.0 ) y = 0.0
    y
  }
  //=============================================================================

  def xsect_getYofA(xsect:TXsect ,  a:Double):Double =
    //
    //  Input:   xsect = ptr. to a cross section data structure
    //           a = area (ft2)
    //  Output:  returns depth (ft)
    //  Purpose: computes xsection's depth at a given area.
    //
  {
    val alpha = a / xsect.aFull
     xsect.sType match
    {
      case "FORCE_MAIN" =>
        circ_getYofA(xsect, a)
      case "CIRCULAR" =>
        circ_getYofA(xsect, a)

      case "FILLED_CIRCULAR" =>
       filled_circ_getYofA(xsect, a)

      case "EGGSHAPED" =>
       xsect.yFull * lookup(alpha, Y_Egg, N_Y_Egg)

      case "HORSESHOE" =>
       xsect.yFull * lookup(alpha, Y_Horseshoe, N_Y_Horseshoe)

      case "GOTHIC" =>
       xsect.yFull * lookup(alpha, Y_Gothic, N_Y_Gothic)

      case "CATENARY" =>
       xsect.yFull * lookup(alpha, Y_Catenary, N_Y_Catenary)

      case "SEMIELLIPTICAL" =>
       xsect.yFull * lookup(alpha, Y_SemiEllip, N_Y_SemiEllip)

      case "BASKETHANDLE" =>
       xsect.yFull * lookup(alpha, Y_BasketHandle, N_Y_BasketHandle)

      case "SEMICIRCULAR" =>
       xsect.yFull * lookup(alpha, Y_SemiCirc, N_Y_SemiCirc)

      case "HORIZ_ELLIPSE" =>
       xsect.yFull * invLookup(alpha, A_HorizEllipse, N_A_HorizEllipse)

      case "VERT_ELLIPSE" =>
       xsect.yFull * invLookup(alpha, A_VertEllipse, N_A_VertEllipse)

      case "IRREGULAR" =>
       xsect.yFull * invLookup(alpha,
        project.Transect(xsect.transect.pos).areaTbl, TTransect.N_TRANSECT_TBL)

      case "CUSTOM" =>
       xsect.yFull * invLookup(alpha,
        project.Shape(project.Curve(xsect.transect.pos).refersTo.ordinal()).areaTbl, TShape.N_SHAPE_TBL)

      case "ARCH" =>
       xsect.yFull * invLookup(alpha, A_Arch, N_A_Arch)

      case "RECT_CLOSED" =>  a / xsect.wMax

      case "RECT_TRIANG" =>  rect_triang_getYofA(xsect, a)

      case "RECT_ROUND" =>   rect_round_getYofA(xsect, a)

      case "RECT_OPEN" =>    a / xsect.wMax

      case "MOD_BASKET" =>   mod_basket_getYofA(xsect, a)

      case "TRAPEZOIDAL" =>  trapez_getYofA(xsect, a)

      case "TRIANGULAR" =>   triang_getYofA(xsect, a)

      case "PARABOLIC" =>    parab_getYofA(xsect, a)

      case "POWERFUNC" =>    powerfunc_getYofA(xsect, a)

      case _ =>           0.0
    }
  }

  //=============================================================================

  def xsect_getRofA(xsect:TXsect ,  a:Double):Double =
    //
    //  Input:   xsect = ptr. to a cross section data structure
    //           a = area (ft2)
    //  Output"=>s hydraulic radius (ft)
    //  Purpose: computes xsection's hydraulic radius at a given area.
    //
  {
    var cathy:Double =0
    if ( a <= 0.0 ) return 0.0
     xsect.sType match
    {
      case "HORIZ_ELLIPSE"=>
        xsect_getRofY( xsect, xsect_getYofA(xsect, a) )
      case "VERT_ELLIPSE"=>
        xsect_getRofY( xsect, xsect_getYofA(xsect, a) )
      case "ARCH"=>
        xsect_getRofY( xsect, xsect_getYofA(xsect, a) )
      case "IRREGULAR" =>
        xsect_getRofY( xsect, xsect_getYofA(xsect, a) )
      case "FILLED_CIRCULAR" =>
        xsect_getRofY( xsect, xsect_getYofA(xsect, a) )
      case "CUSTOM" =>
       xsect_getRofY( xsect, xsect_getYofA(xsect, a) )

      case "RECT_CLOSED"=> rect_closed_getRofA(xsect, a)

      case "RECT_OPEN"=>
      
      a / (xsect.wMax +  (2.0 - xsect.sBot) * a / xsect.wMax)

      case "RECT_TRIANG"=> rect_triang_getRofA(xsect, a)

      case "RECT_ROUND"=> rect_round_getRofA(xsect, a)

      case "MOD_BASKET"=> mod_basket_getRofA(xsect, a)

      case "TRAPEZOIDAL"=> trapez_getRofA(xsect, a)

      case "TRIANGULAR"=> triang_getRofA(xsect, a)

      case "PARABOLIC"=> parab_getRofA(xsect, a)

      case "POWERFUNC"=> powerfunc_getRofA(xsect, a)

      case _ =>
        cathy = xsect_getSofA(xsect, a)
      if ( cathy < SwmmConst.TINY || a < SwmmConst.TINY ) return 0.0
      Math.pow(cathy/a, 3.0/2.0)
    }
  }

  def xsect_getWofY(xsect:TXsect ,  y:Double):Double =
    //
    //  Input:   xsect = ptr. to a cross section data structure
    //           y = depth ft)
    //  Output"=>s top width (ft)
    //  Purpose: computes xsection's top width at a given depth.
    //
  {
    var yNorm = y / xsect.yFull
     xsect.sType match
    {
      case "FORCE_MAIN"=>
        xsect.wMax * lookup(yNorm, W_Circ, N_W_Circ)
      case "CIRCULAR"=>
       xsect.wMax * lookup(yNorm, W_Circ, N_W_Circ)

      case "FILLED_CIRCULAR"=>
      yNorm = (y + xsect.yBot) / (xsect.yFull + xsect.yBot)
       xsect.wMax * lookup(yNorm, W_Circ, N_W_Circ)

      case "EGGSHAPED"=>
       xsect.wMax * lookup(yNorm, W_Egg, N_W_Egg)

      case "HORSESHOE"=>
       xsect.wMax * lookup(yNorm, W_Horseshoe, N_W_Horseshoe)

      case "GOTHIC"=>
       xsect.wMax * lookup(yNorm, W_Gothic, N_W_Gothic)

      case "CATENARY"=>
       xsect.wMax * lookup(yNorm, W_Catenary, N_W_Catenary)

      case "SEMIELLIPTICAL"=>
       xsect.wMax * lookup(yNorm, W_SemiEllip, N_W_SemiEllip)

      case "BASKETHANDLE"=>
       xsect.wMax * lookup(yNorm, W_BasketHandle, N_W_BasketHandle)

      case "SEMICIRCULAR"=>
       xsect.wMax * lookup(yNorm, W_SemiCirc, N_W_SemiCirc)

      case "HORIZ_ELLIPSE"=>
       xsect.wMax * lookup(yNorm, W_HorizEllipse, N_W_HorizEllipse)

      case "VERT_ELLIPSE"=>
       xsect.wMax * lookup(yNorm, W_VertEllipse, N_W_VertEllipse)

      case "ARCH"=>
       xsect.wMax * lookup(yNorm, W_Arch, N_W_Arch)

      case "IRREGULAR"=>
       xsect.wMax * lookup(yNorm,
        project.Transect(xsect.transect.pos).widthTbl, TTransect.N_TRANSECT_TBL)

      case "CUSTOM"=>
       xsect.wMax * lookup(yNorm,
        project.Shape(project.Curve(xsect.transect.pos).refersTo.ordinal()).widthTbl, TShape.N_SHAPE_TBL)

      case "RECT_CLOSED"=>
        xsect.wMax

      case "RECT_TRIANG"=>
        rect_triang_getWofY(xsect, y)

      case "RECT_ROUND"=>
        rect_round_getWofY(xsect, y)

      case "RECT_OPEN"=>
        xsect.wMax

      case "MOD_BASKET"=>
        mod_basket_getWofY(xsect, y)

      case "TRAPEZOIDAL"=>
        trapez_getWofY(xsect, y)

      case "TRIANGULAR"=>
        triang_getWofY(xsect, y)

      case "PARABOLIC"=>
        parab_getWofY(xsect, y)

      case "POWERFUNC"=>
        powerfunc_getWofY(xsect, y)

      case _ =>
        0.0
    }
  }
//  def rect_triang_getWofY(xsect:TXsect, y:Double):Double=
//  {
//    if ( y <= xsect.yBot )  2.0 * xsect.sBot * y  // below upper section
//    else  xsect.wMax                               // above bottom section
//  }
  //=============================================================================
  //  RECT_ROUND fuctions
  //=============================================================================

  def rect_round_getYofA(xsect:TXsect, a:Double):Double=
  {

    // --- if above circular bottom:
    if ( a > xsect.aBot )
      return xsect.yBot + (a - xsect.aBot) / xsect.wMax

    // --- otherwise use circular xsection method to find height
    val alpha = a / (SwmmConst.PI * xsect.rBot * xsect.rBot)
    if ( alpha < 0.04 )
      (2.0 * xsect.rBot) * getYcircular(alpha)
    else
     (2.0 * xsect.rBot) * lookup(alpha, Y_Circ, N_Y_Circ)
  }

  def rect_round_getRofA(xsect:TXsect, a:Double):Double=
  {
    var y1, theta1, p, arg:Double=0

    // --- if above circular invert ...
    if ( a <= 0.0 ) return 0.0
    if ( a > xsect.aBot )
    {
      // wetted perimeter without contribution of top surface
      y1 = (a - xsect.aBot) / xsect.wMax
      theta1 = 2.0 * Math.asin(xsect.wMax/2.0/xsect.rBot)
      p = xsect.rBot*theta1 + 2.0*y1

      // top-surface contribution
      arg = (a / xsect.aFull) - RECT_ROUND_ALFMAX
      if ( arg > 0.0 ) p += arg / (1.0 - RECT_ROUND_ALFMAX) * xsect.wMax
      return a / p
    }

    // --- if within circular invert ...
    y1 = rect_round_getYofA(xsect, a)
    theta1 = 2.0*Math.acos(1.0 - y1/xsect.rBot)
    p = xsect.rBot * theta1
    a / p
  }

  def rect_round_getSofA(xsect:TXsect, a:Double):Double=
  {
    var alpha, aFull, sFull:Double=0

    // --- if a > area corresponding to sMax,
    //     interpolate between sMax and sFull
    val alfMax = RECT_ROUND_ALFMAX
    if ( a / xsect.aFull > alfMax )
    {
       xsect.sMax + (xsect.sFull - xsect.sMax) *
        (a / xsect.aFull - alfMax) / (1.0 - alfMax)
    }

    // --- if above circular invert, use generic function
    else if ( a > xsect.aBot )
    {
       a * Math.pow(xsect_getRofA(xsect, a), 2.0/3.0)
    }

    // --- otherwise use circular xsection function applied
    //     to full circular shape of bottom section
    else
    {
      aFull = SwmmConst.PI * xsect.rBot * xsect.rBot
      alpha = a / aFull
      sFull = xsect.sBot

      // --- use special function for small a/aFull
      if ( alpha < 0.04 )  sFull * getScircular(alpha)

      // --- otherwise use table
      else  sFull * lookup(alpha, S_Circ, N_S_Circ)
    }
  }

  def rect_round_getdSdA(xsect:TXsect, a:Double):Double=
  {
    var alfMax, r, dPdA:Double=0

    // --- if a > area corresponding to sMax, then
    //     use slope between sFull & sMax
    alfMax = RECT_ROUND_ALFMAX
    if ( a / xsect.aFull > alfMax )
    {
       (xsect.sFull - xsect.sMax) /
        ((1.0 - alfMax) * xsect.aFull)
    }

    // --- if above circular invert, use analytical function for dS/dA
    else if ( a > xsect.aBot )
    {
      r = rect_round_getRofA(xsect, a)
      dPdA = 2.0 / xsect.wMax       // d(wet perim)/dA for rect.
      (5.0/3.0 - (2.0/3.0) * dPdA * r) * Math.pow(r, 2.0/3.0)
    }

    // --- otherwise use generic finite difference function
    else  generic_getdSdA(xsect, a)
  }

  def rect_round_getAofY(xsect:TXsect, y:Double):Double=
  {
    var theta1:Double=0

    // --- if above circular invert...
    if ( y > xsect.yBot )
      return xsect.aBot + (y - xsect.yBot) * xsect.wMax

    // --- find area of circular section
    theta1 = 2.0*Math.acos(1.0 - y/xsect.rBot)
     0.5 * xsect.rBot * xsect.rBot * (theta1 - Math.sin(theta1))
  }

  def rect_round_getRofY(xsect:TXsect, y:Double):Double=
  {


    // --- if above top of circular bottom, use RofA formula
    if ( y <= 0.0 ) return 0.0
    if ( y > xsect.yBot )
      return rect_round_getRofA( xsect, rect_round_getAofY(xsect, y) )

    // --- find hyd. radius of circular section
    var theta1 = 2.0*Math.acos(1.0 - y/xsect.rBot)
    0.5 * xsect.rBot * (1.0 - Math.sin(theta1)) / theta1
  }

  def rect_round_getWofY(xsect:TXsect, y:Double):Double=
  {
    // --- return width if depth above circular bottom section
    if ( y > xsect.yBot ) return xsect.wMax

    // --- find width of circular section
     2.0 *Math.sqrt( y * (2.0*xsect.rBot - y) )
  }

  //=============================================================================
  //  MOD_BASKETHANDLE fuctions
  //=============================================================================

  // Note: the variables rBot, yBot, and aBot refer to properties of the
  //       circular top portion of the cross-section (not the bottom)

  def mod_basket_getYofA(xsect:TXsect, a:Double):Double=
  {
    var alpha, y1:Double =0

    // --- water level below top of rectangular bottom
    if ( a <= xsect.aFull - xsect.aBot ) return a / xsect.wMax

    // --- find unfilled top area / area of full circular top
    alpha = (xsect.aFull - a) / (SwmmConst.PI * xsect.rBot * xsect.rBot)

    // --- find unfilled height
    if ( alpha < 0.04 ) y1 = getYcircular(alpha)
    else                y1 = lookup(alpha, Y_Circ, N_Y_Circ)
    y1 = 2.0 * xsect.rBot * y1

    // --- return difference between full height & unfilled height
     xsect.yFull - y1
  }

  def mod_basket_getRofA(xsect:TXsect, a:Double):Double=
  {
    var y1, p, theta1:Double =0

    // --- water level is below top of rectangular bottom
    //     return hyd. radius of rectangle
    if ( a <= xsect.aFull - xsect.aBot )
      return a / (xsect.wMax + 2.0 * a / xsect.wMax)

    // --- find height of empty area
    y1 = xsect.yFull - mod_basket_getYofA(xsect, a)

    // --- find angle of circular arc corresponding to this height
    theta1 = 2.0 * Math.acos(1.0 - y1 / xsect.rBot)

    // --- find perimeter of wetted portion of circular arc
    //     (angle of full circular opening was stored in sBot)
    p = (xsect.sBot - theta1) * xsect.rBot

    // --- add on wetted perimeter of bottom rectangular area
    y1 = xsect.yFull - xsect.yBot
    p =  p + 2.0*y1 + xsect.wMax

    // --- return area / wetted perimeter
     a / p
  }

  def mod_basket_getdSdA(xsect:TXsect, a:Double):Double=
  {
    var r, dPdA:Double =0

    // --- if water level below top of rectangular bottom but not
    //     empty then use same code as for rectangular xsection
    if ( a <= xsect.aFull - xsect.aBot && a/xsect.aFull > 1.0e-30 )
    {
      r = a / (xsect.wMax + 2.0 * a / xsect.wMax)
      dPdA = 2.0 / xsect.wMax
        (5.0/3.0 - (2.0/3.0) * dPdA * r) * Math.pow(r, 2.0/3.0)
    }

    // --- otherwise use generic function
    else
       generic_getdSdA(xsect, a)
  }

  def mod_basket_getAofY(xsect:TXsect, y:Double):Double=
  {
    var a1, theta1, y1:Double =0

    // --- if water level is below top of rectangular bottom
    //     return depth * width
    if ( y <= xsect.yFull - xsect.yBot ) return y * xsect.wMax

    // --- find empty top circular area
    y1 = xsect.yFull - y
    theta1 = 2.0*Math.acos(1.0 - y1/xsect.rBot)
    a1 = 0.5 * xsect.rBot * xsect.rBot * (theta1 - Math.sin(theta1))

    // --- return difference between full and empty areas
     xsect.aFull - a1
  }

  def mod_basket_getWofY(xsect:TXsect, y:Double):Double=
  {

    // --- if water level below top of rectangular bottom then return width
    if ( y <= 0.0 ) return 0.0
    if ( y <= xsect.yFull - xsect.yBot ) return xsect.wMax

    // --- find width of empty top circular section
    val y1 = xsect.yFull - y
     2.0 *Math.sqrt( y1 * (2.0*xsect.rBot - y1) )
  }

  //=============================================================================
  //  POWERFUNC fuctions
  //=============================================================================

  def powerfunc_getYofA(xsect:TXsect, a:Double):Double=
  {
     Math.pow(a / xsect.rBot, 1.0 / (xsect.sBot + 1.0))
  }

  def powerfunc_getRofA(xsect:TXsect, a:Double):Double=
  {
    if ( a <= 0.0 ) return 0.0
     a / powerfunc_getPofY(xsect, powerfunc_getYofA(xsect, a))
  }

  def powerfunc_getPofY(xsect:TXsect, y:Double):Double=
  {
    val dy1 = 0.02 * xsect.yFull
    var h = (xsect.sBot + 1.0) * xsect.rBot / 2.0
    var m = xsect.sBot
    var p = 0.0
    var y1 = 0.0
    var x1 = 0.0
    var x2, y2, dx, dy:Double=0
    do
    {
      y2 = y1 + dy1
      if ( y2 > y ) y2 = y
      x2 = h * Math.pow(y2, m)
      dx = x2 - x1
      dy = y2 - y1
      p += Math.sqrt(dx*dx + dy*dy)
      x1 = x2
      y1 = y2
    } while ( y2 < y )
     2.0 * p
  }

  def powerfunc_getAofY(xsect:TXsect, y:Double):Double=
  {
     xsect.rBot * Math.pow(y, xsect.sBot + 1.0)
  }

  def powerfunc_getRofY(xsect:TXsect, y:Double):Double=
  {
    if ( y <= 0.0 ) return 0.0
     powerfunc_getAofY(xsect, y) / powerfunc_getPofY(xsect, y)
  }

  def powerfunc_getWofY(xsect:TXsect, y:Double):Double=
  {
     (xsect.sBot + 1.0) * xsect.rBot * Math.pow(y, xsect.sBot)
  }
  //=============================================================================
  //  PARABOLIC fuctions
  //=============================================================================

  def parab_getYofA(xsect:TXsect, a:Double):Double=
  {
     Math.pow( (3.0/4.0) * a / xsect.rBot, 2.0/3.0 )
  }

  def parab_getRofA(xsect:TXsect, a:Double):Double=
  {
    if ( a <= 0.0 )  0.0
    else 
     a / parab_getPofY( xsect, parab_getYofA(xsect, a) )
  }

  def parab_getPofY(xsect:TXsect, y:Double):Double=
  {
    val x = 2.0 *Math.sqrt(y) / xsect.rBot
    val t =Math.sqrt(1.0 + x * x)
     0.5 * xsect.rBot * xsect.rBot * ( x * t + Math.log(x + t) )
  }

  def parab_getAofY(xsect:TXsect, y:Double):Double=
  {
     4.0/3.0 * xsect.rBot * y *Math.sqrt(y)
  }

  def parab_getRofY(xsect:TXsect, y:Double):Double=
  {
    if ( y <= 0.0 ) return 0.0
     parab_getAofY(xsect, y) / parab_getPofY(xsect, y)
  }

  def parab_getWofY(xsect:TXsect, y:Double):Double=
  {
     2.0 * xsect.rBot *Math.sqrt(y)
  }
  //=============================================================================
  //  TRAPEZOIDAL fuctions
  //
  //  Note: yBot = width of bottom
  //        sBot = avg. of side slopes
  //        rBot = length of sides per unit of depth
  //=============================================================================

  def trapez_getYofA(xsect:TXsect, a:Double):Double=
  {
    if ( xsect.sBot == 0.0 ) return a / xsect.yBot
     (Math.sqrt( xsect.yBot*xsect.yBot + 4.0*xsect.sBot*a )
      - xsect.yBot )/(2.0 * xsect.sBot)
  }

  def trapez_getRofA(xsect:TXsect, a:Double):Double=
  {
     a / (xsect.yBot + trapez_getYofA(xsect, a) * xsect.rBot)
  }

  def trapez_getdSdA(xsect:TXsect, a:Double):Double=
  {
    var r, dPdA:Double =0
    // --- use generic central difference method for very small a
    if ( a/xsect.aFull <= 1.0e-30 ) return generic_getdSdA(xsect, a)

    // --- otherwise use analytical formula:
    //     dSdA = [5/3 - (2/3)(dP/dA)R]R^(2/3)
    r = trapez_getRofA(xsect, a)
    dPdA = xsect.rBot /
     Math.sqrt( xsect.yBot * xsect.yBot + 4.0 * xsect.sBot * a )

     (5.0/3.0 - (2.0/3.0) * dPdA * r) * Math.pow(r, 2.0/3.0)
  }

  def trapez_getAofY(xsect:TXsect, y:Double):Double=
  {
     ( xsect.yBot + xsect.sBot * y ) * y
  }

  def trapez_getRofY(xsect:TXsect, y:Double):Double=
  {
    if ( y == 0.0 ) return 0.0
     trapez_getAofY(xsect, y) / (xsect.yBot + y * xsect.rBot)
  }

  def trapez_getWofY(xsect:TXsect, y:Double):Double=
  {
     xsect.yBot + 2.0 * y * xsect.sBot
  }


  //=============================================================================
  //  TRIANGULAR fuctions
  //=============================================================================

  def triang_getYofA(xsect:TXsect, a:Double):Double=
  {
    Math.sqrt(a / xsect.sBot)
  }

  def triang_getRofA(xsect:TXsect, a:Double):Double=
  {
     a / (2.0 * triang_getYofA(xsect, a) * xsect.rBot)
  }

  def triang_getdSdA(xsect:TXsect, a:Double):Double=
  {
    var r, dPdA:Double =0
    // --- use generic finite difference method for very small 'a'
    if ( a/xsect.aFull <= 1.0e-30 ) return generic_getdSdA(xsect, a)

    // --- evaluate dSdA = [5/3 - (2/3)(dP/dA)R]R^(2/3)
    r = triang_getRofA(xsect, a)
    dPdA = xsect.rBot /Math.sqrt(a * xsect.sBot)
    (5.0/3.0 - (2.0/3.0) * dPdA * r) * Math.pow(r, 2.0/3.0)
  }

  def triang_getAofY(xsect:TXsect, y:Double):Double=
  {
     y * y * xsect.sBot
  }

  def triang_getRofY(xsect:TXsect, y:Double):Double=
  {
     (y * xsect.sBot) / (2.0 * xsect.rBot)
  }

  def triang_getWofY(xsect:TXsect, y:Double):Double=
  {
     2.0 * xsect.sBot * y
  }


  //=============================================================================
  //  RECT_TRIANG fuctions
  //=============================================================================

  def rect_triang_getYofA(xsect:TXsect, a:Double):Double=
  {
    // below upper section
    if ( a <= xsect.aBot ) 
      Math.sqrt(a / xsect.sBot)

    // above bottom section
    else 
      xsect.yBot + (a - xsect.aBot) / xsect.wMax
  }

  def rect_triang_getRofA(xsect:TXsect, a:Double):Double=
  {
    var y, p, alf:Double =0.0

    if ( a <= 0.0 )   return 0.0
    y = rect_triang_getYofA(xsect, a)

    // below upper section
    if ( y <= xsect.yBot ) return a / (2.0* y * xsect.rBot)

    // wetted perimeter without contribution of top surface
    p = 2.0* xsect.yBot * xsect.rBot + 2.0* (y - xsect.yBot)

    // top-surface contribution
    alf = (a / xsect.aFull) - RECT_TRIANG_ALFMAX
    if ( alf > 0.0 ) p += alf / (1.0 - RECT_TRIANG_ALFMAX) * xsect.wMax
     a / p
  }

  def rect_triang_getSofA(xsect:TXsect, a:Double):Double=
  {
    // --- if a > area corresponding to sMax, then
    //     interpolate between sMax and Sfull
    val alfMax = RECT_TRIANG_ALFMAX
    if ( a / xsect.aFull > alfMax )
       xsect.sMax + (xsect.sFull - xsect.sMax) *
        (a/xsect.aFull - alfMax) / (1.0 - alfMax)

    // --- otherwise use regular formula
    else
       a * Math.pow(rect_triang_getRofA(xsect, a), 2.0/3.0)
  }

  def rect_triang_getdSdA(xsect:TXsect, a:Double):Double=
  {
    var alpha, alfMax, dPdA, r:Double =0

    // --- if a > area corresponding to sMax, then
    //     use slope between sFull & sMax
    alfMax = RECT_TRIANG_ALFMAX
    alpha = a / xsect.aFull
    if ( alpha > alfMax )
      return (xsect.sFull - xsect.sMax) / ((1.0 - alfMax) * xsect.aFull)

    // --- use generic central difference method for very small a
    if ( alpha <= 1.0e-30 ) return generic_getdSdA(xsect, a)

    // --- find deriv. of wetted perimeter
    if ( a > xsect.aBot ) dPdA = 2.0 / xsect.wMax  // for upper rectangle
    else dPdA = xsect.rBot / Math.sqrt(a * xsect.sBot)  // for triang. bottom

    // --- get hyd. radius & evaluate section factor derivative formula
    r = rect_triang_getRofA(xsect, a)
    ( 5.0/3.0 - (2.0/3.0) * dPdA * r) * Math.pow(r, 2.0/3.0)
  }

  def rect_triang_getAofY(xsect:TXsect, y:Double):Double=
  {
    if ( y <= xsect.yBot )  y * y * xsect.sBot         // below upper section
    else  xsect.aBot + (y - xsect.yBot) * xsect.wMax  // above bottom section
  }

  def rect_triang_getRofY(xsect:TXsect, y:Double):Double=
  {
    var p, a, alf:Double =0

    // y is below upper rectangular section
    if ( y <= xsect.yBot ) return y * xsect.sBot / (2.0* xsect.rBot)

    // area
    a = xsect.aBot + (y - xsect.yBot) * xsect.wMax

    // wetted perimeter without contribution of top surface
    p = 2.0* xsect.yBot * xsect.rBot + 2.0* (y - xsect.yBot)

    // top-surface contribution
    alf = (a / xsect.aFull) - RECT_TRIANG_ALFMAX
    if ( alf > 0.0 ) p += alf / (1.0 - RECT_TRIANG_ALFMAX) * xsect.wMax
    a / p
  }

  def rect_triang_getWofY(xsect:TXsect, y:Double):Double=
  {
    if ( y <= xsect.yBot )  2.0 * xsect.sBot * y  // below upper section
    else  xsect.wMax                               // above bottom section
  }


  //=============================================================================
  //  CIRCULAR functions
  //=============================================================================

  def circ_getYofA(xsect:TXsect, a:Double):Double=
  {
    val alpha = a / xsect.aFull

    // --- use special function for small a/aFull
    if ( alpha < 0.04 )
      xsect.yFull * getYcircular(alpha)

    // --- otherwise use table
    else
      xsect.yFull * lookup(alpha, Y_Circ, N_Y_Circ)
  }

  def circ_getAofS(xsect:TXsect, s:Double):Double=
  {
    val psi = s / xsect.sFull
    if (psi == 0.0) return 0.0
    if (psi >= 1.0) return xsect.aFull

    // --- use special function for small s/sFull
    if (psi <= 0.015)  xsect.aFull * getAcircular(psi)

    // --- otherwise use table
    else  xsect.aFull * invLookup(psi, S_Circ, N_S_Circ)
  }

  def circ_getSofA(xsect:TXsect, a:Double):Double=
  {
    val alpha = a / xsect.aFull

    // --- use special function for small a/aFull
    if ( alpha < 0.04 )  xsect.sFull * getScircular(alpha)

    // --- otherwise use table
    else
       xsect.sFull * lookup(alpha, S_Circ, N_S_Circ)
  }

  def circ_getdSdA(xsect:TXsect, a:Double):Double=
  {
    var alpha, theta, p, r, dPdA:Double =0

    // --- for near-zero area, use generic central difference formula
    alpha = a / xsect.aFull
    if ( alpha <= 1.0e-30 )  1.0e-30  //generic_getdSdA(xsect, a)

    // --- for small a/aFull use analytical derivative
    else if ( alpha < 0.04 )
    {
      theta = getThetaOfAlpha(alpha)
      p = theta * xsect.yFull / 2.0
      r = a / p
      dPdA = 4.0 / xsect.yFull / (1.0 - Math.cos(theta))
        (5.0/3.0 - (2.0/3.0) * dPdA * r) * Math.pow(r, 2.0/3.0)
    }

    // --- otherwise use generic tabular getdSdA
    else  tabular_getdSdA(xsect, a, S_Circ, N_S_Circ)
  }
  //=============================================================================

  def tabular_getdSdA(xsect:TXsect, a:Double, table:Array[Double], nItems:Int):Double =
    //
    //  Input:   xsect = ptr. to cross section data structure
    //           a = area (ft2)
    //           table = ptr. to table of section factor v. normalized area
    //           nItems = number of equally spaced items in table
    //  Output:  returns derivative of section factor w.r.t. area (ft^2/3)
    //  Purpose: computes derivative of section factor w.r.t area
    //           using geometry tables.
    //
  {
    var    i:Int=0
    val alpha = a / xsect.aFull
    val delta = 1.0 / (nItems - 1)
    var dSdA:Double=0

    // --- find which segment of table contains alpha
    i = (alpha / delta).toInt
    if ( i >= nItems - 1 ) i = nItems - 2

    // --- compute slope from this interval of table
    dSdA = (table(i+1) - table(i)) / delta

    // --- convert slope to un-normalized value
     dSdA * xsect.sFull / xsect.aFull
  }

  def xsect_getRofY(xsect:TXsect, y:Double):Double=
  //
  //  Input:   xsect = ptr. to a cross section data structure
  //           y = depth (ft)
  //  Output"=>s hydraulic radius (ft)
  //  Purpose: computes xsection's hydraulic radius at a given depth.
  //
  {
    val yNorm = y / xsect.yFull
    xsect.sType match
    {
      case "FORCE_MAIN" =>
        xsect.rFull * lookup(yNorm, R_Circ, N_R_Circ)
      case "CIRCULAR" =>
         xsect.rFull * lookup(yNorm, R_Circ, N_R_Circ)

      case "FILLED_CIRCULAR" =>
        if ( xsect.yBot == 0.0 )
           xsect.rFull * lookup(yNorm, R_Circ, N_R_Circ)
        else
         filled_circ_getRofY(xsect, y)

      case "EGGSHAPED" =>
         xsect.rFull * lookup(yNorm, R_Egg, N_R_Egg)

      case "HORSESHOE" =>
         xsect.rFull * lookup(yNorm, R_Horseshoe, N_R_Horseshoe)

      case "BASKETHANDLE" =>
         xsect.rFull * lookup(yNorm, R_Baskethandle, N_R_Baskethandle)

      case "HORIZ_ELLIPSE" =>
         xsect.rFull * lookup(yNorm, R_HorizEllipse, N_R_HorizEllipse)

      case "VERT_ELLIPSE" =>
         xsect.rFull * lookup(yNorm, R_VertEllipse, N_R_VertEllipse)

      case "ARCH" =>
         xsect.rFull * lookup(yNorm, R_Arch, N_R_Arch)

      case "IRREGULAR" =>
         xsect.rFull * lookup(yNorm,
          project.Transect(xsect.transect.pos).hradTbl, TTransect.N_TRANSECT_TBL)

      case "CUSTOM" =>
         xsect.rFull * lookup(yNorm,
          project.Shape(project.Curve(xsect.transect.pos).refersTo.ordinal()).hradTbl, TShape.N_SHAPE_TBL)

      case "RECT_TRIANG" =>   rect_triang_getRofY(xsect, y)

      case "RECT_ROUND" =>    rect_round_getRofY(xsect, y)

      case "TRAPEZOIDAL" =>   trapez_getRofY(xsect, y)

      case "TRIANGULAR" =>    triang_getRofY(xsect, y)

      case "PARABOLIC" =>     parab_getRofY(xsect, y)

      case "POWERFUNC" =>     powerfunc_getRofY(xsect, y)

      case  _ =>            xsect_getRofA( xsect, xsect_getAofY(xsect, y) )
    }
  }
  def xsect_getAofY(xsect:TXsect, y:Double):Double =
    //
    //  Input:   xsect = ptr. to a cross section data structure
    //           y = depth (ft)
    //  Output:  returns area (ft2)
    //  Purpose: computes xsection's area at a given depth.
    //
  {
    var yNorm = y / xsect.yFull
    if ( y <= 0.0 ) return 0.0
     xsect.sType match
    {
      case "FORCE_MAIN" =>
        xsect.aFull * lookup(yNorm, A_Circ, N_A_Circ)
      case "CIRCULAR" =>
       xsect.aFull * lookup(yNorm, A_Circ, N_A_Circ)

      case "FILLED_CIRCULAR" =>
       filled_circ_getAofY(xsect, y)

      case "EGGSHAPED" =>
       xsect.aFull * lookup(yNorm, A_Egg, N_A_Egg)

      case "HORSESHOE" =>
       xsect.aFull * lookup(yNorm, A_Horseshoe, N_A_Horseshoe)

      case "GOTHIC" =>
       xsect.aFull * invLookup(yNorm, Y_Gothic, N_Y_Gothic)

      case "CATENARY" =>
       xsect.aFull * invLookup(yNorm, Y_Catenary, N_Y_Catenary)

      case "SEMIELLIPTICAL" =>
       xsect.aFull * invLookup(yNorm, Y_SemiEllip, N_Y_SemiEllip)

      case "BASKETHANDLE" =>
       xsect.aFull * lookup(yNorm, A_Baskethandle, N_A_Baskethandle)

      case "SEMICIRCULAR" =>
       xsect.aFull * invLookup(yNorm, Y_SemiCirc, N_Y_SemiCirc)

      case "HORIZ_ELLIPSE" =>
       xsect.aFull * lookup(yNorm, A_HorizEllipse, N_A_HorizEllipse)

      case "VERT_ELLIPSE" =>
       xsect.aFull * lookup(yNorm, A_VertEllipse, N_A_VertEllipse)

      case "ARCH" =>
       xsect.aFull * lookup(yNorm, A_Arch, N_A_Arch)

      case "IRREGULAR" =>
       xsect.aFull * lookup(yNorm,
        project.Transect(xsect.transect.pos).areaTbl, TTransect.N_TRANSECT_TBL)

      case "CUSTOM" =>
       xsect.aFull * lookup(yNorm,
        project.Shape(project.Curve(xsect.transect.pos).refersTo.ordinal()).areaTbl, TShape.N_SHAPE_TBL)

      case "RECT_CLOSED" =>   y * xsect.wMax

      case "RECT_TRIANG" =>  rect_triang_getAofY(xsect, y)

      case "RECT_ROUND" =>   rect_round_getAofY(xsect, y)

      case "RECT_OPEN" =>    y * xsect.wMax

      case "MOD_BASKET" =>   mod_basket_getAofY(xsect, y)

      case "TRAPEZOIDAL" =>  trapez_getAofY(xsect, y)

      case "TRIANGULAR" =>   triang_getAofY(xsect, y)

      case "PARABOLIC" =>    parab_getAofY(xsect, y)

      case "POWERFUNC" =>    powerfunc_getAofY(xsect, y)

      case  _ =>            0.0
    }
  }

  def xsect_getSofA(xsect:TXsect, a:Double):Double =
    //
    //  Input:   xsect = ptr. to a cross section data structure
    //           a = area (ft2)
    //  Output"=>s section factor (ft^(8/3))
    //  Purpose: computes xsection's section factor at a given area.
    //
  {
    val alpha = a / xsect.aFull
    var r:Double=0
     xsect.sType match
    {
      case "FORCE_MAIN" =>
        circ_getSofA(xsect, a)
      case "CIRCULAR" =>
       circ_getSofA(xsect, a)

      case "EGGSHAPED" =>
       xsect.sFull * lookup(alpha, S_Egg, N_S_Egg)

      case "HORSESHOE" =>
       xsect.sFull * lookup(alpha, S_Horseshoe, N_S_Horseshoe)

      case "GOTHIC" =>
       xsect.sFull * lookup(alpha, S_Gothic, N_S_Gothic)

      case "CATENARY" =>
       xsect.sFull * lookup(alpha, S_Catenary, N_S_Catenary)

      case "SEMIELLIPTICAL" =>
       xsect.sFull * lookup(alpha, S_SemiEllip, N_S_SemiEllip)

      case "BASKETHANDLE" =>
       xsect.sFull * lookup(alpha, S_BasketHandle, N_S_BasketHandle)

      case "SEMICIRCULAR" =>
       xsect.sFull * lookup(alpha, S_SemiCirc, N_S_SemiCirc)

      case "RECT_CLOSED" =>
       rect_closed_getSofA(xsect, a)

      case "RECT_OPEN" =>
       rect_open_getSofA(xsect, a)

      case "RECT_TRIANG" =>
       rect_triang_getSofA(xsect, a)

      case "RECT_ROUND" =>
       rect_round_getSofA(xsect, a)

      case _ =>
      if (a == 0.0) return 0.0
      r = xsect_getRofA(xsect, a)
      if ( r < SwmmConst.TINY )
        0.0
      else
         a * Math.pow(r, 2.0/3.0)
    }

  }
  //=============================================================================
  //  RECT_CLOSED fuctions
  //=============================================================================

  def rect_closed_getSofA(xsect:TXsect, a:Double):Double =
  {
    // --- if a > area corresponding to Smax then
    //     interpolate between sMax and Sfull
    var alfMax = RECT_ALFMAX
    if ( a / xsect.aFull > alfMax )
    {
      xsect.sMax + (xsect.sFull - xsect.sMax) *
        (a/xsect.aFull - alfMax) / (1.0 - alfMax)
    }

    // --- otherwise use regular formula
    a * Math.pow(xsect_getRofA(xsect, a), 2.0/3.0)
  }

  def rect_closed_getdSdA(xsect:TXsect, a:Double):Double =
  {
    var alpha, alfMax, r:Double =0

    // --- if above level corresponding to sMax, then
    //     use slope between sFull & sMax
    alfMax = RECT_ALFMAX
    alpha = a / xsect.aFull
    if ( alpha > alfMax )
    {
      return (xsect.sFull - xsect.sMax) /
        ((1.0 - alfMax) * xsect.aFull)
    }

    // --- for small a/aFull use generic central difference formula
    if ( alpha <= 1.0e-30 ) return generic_getdSdA(xsect, a)

    // --- otherwise evaluate dSdA = [5/3 - (2/3)(dP/dA)R]R^(2/3)
    //     (where P = wetted perimeter & dPdA = 2/width)
    r = xsect_getRofA(xsect, a)
      (5.0/3.0 - (2.0/3.0) * (2.0/xsect.wMax) * r) * Math.pow(r, 2.0/3.0)
  }

  def rect_closed_getRofA(xsect:TXsect, a:Double):Double =
  {
  
    if ( a <= 0.0 )   return 0.0
    var p = xsect.wMax + 2.0*a/xsect.wMax // Wetted Perim = width + 2*area/width
    if ( a/xsect.aFull > RECT_ALFMAX )
    {
      p =p + (a/xsect.aFull - RECT_ALFMAX) / (1.0 - RECT_ALFMAX) * xsect.wMax
    }
     a / p
  }


  //=============================================================================
  //  RECT_OPEN fuctions
  //=============================================================================

  def rect_open_getSofA(xsect:TXsect, a:Double):Double =
  {
    val y = a / xsect.wMax
    val r = a / ((2.0 - xsect.sBot) * y + xsect.wMax)
     a * Math.pow(r, 2.0/3.0)
  }


  def rect_open_getdSdA(xsect:TXsect, a:Double):Double =
  {
    var r, dPdA:Double =0

    // --- for small a/aFull use generic central difference formula
    if ( a / xsect.aFull <= 1.0e-30 ) return generic_getdSdA(xsect, a)

    // --- otherwise evaluate dSdA = [5/3 - (2/3)(dP/dA)R]R^(2/3)
    //     (where P = wetted perimeter)
    r = xsect_getRofA(xsect, a)
    dPdA = (2.0 - xsect.sBot) / xsect.wMax // since P = geom2 + 2a/geom2
     (5.0/3.0 - (2.0/3.0) * dPdA * r) * Math.pow(r, 2.0/3.0)
  }
  //=============================================================================

  def invLookup( y:Double, table:Array[Double], nItems:Int):Double =
    //
    //  Input:   y = value of dependent variable in a geometry table
    //           table = ptr. to geometry table
    //           nItems = number of equally spaced items in table
    //  Output:  returns value of independent table variable
    //  Purpose: performs inverse lookup in a geometry table (i.e., finds
    //           x given y).
    //
    //  Notes:   This function assumes that the geometry table has either strictly
    //           increasing entries or that the maximum entry is always third
    //           from the last (which is true for all section factor tables). In
    //           the latter case, the location of a large y can be ambiguous
    //           -- it can be both below and above the location of the maximum.
    //           In such cases this routine searches only the interval above
    //           the maximum (i.e., the last 2 segments of the table).
    //
    //           nItems-1 is the highest subscript for the table's data.
    //
    //           The x value's in a geometry table lie between 0 and 1.
    //
  {
    var dx ,              // x-increment of table
     x, x0, dy:Double =0        // interpolation variables
    var    n,               // # items in increasing portion of table
        i:Int =0                // lower table index that brackets y

    // --- compute table's uniform x-increment
    dx = 1.0 / (nItems-1)

    // --- truncate item count if last 2 table entries are decreasing
    n = nItems
    if ( table(n-3) > table(n-1) ) n = n - 2

    // --- check if y falls in decreasing portion of table
    if ( n < nItems && y > table(nItems-1))
    {
      if ( y >= table(nItems-3) ) 
        return (n-1) * dx
      if ( y <= table(nItems-2) ) 
        i = nItems - 2
      else i = nItems - 3
    }

    // --- otherwise locate the interval where y falls in the table
    else i = locate(y, table, n-1)
    if ( i >= n - 1 ) return (n-1) * dx

    // --- compute x at start and end of segment
    x0 = i * dx

    // --- linearly interpolate an x value
    dy = table(i+1) - table(i)
    if ( dy == 0.0 ) x = x0
    else x = x0 + (y - table(i)) * dx / dy
    if ( x < 0.0 ) x = 0.0
    if ( x > 1.0 ) x = 1.0
     x
  }

  //=============================================================================

  def locate(y:Double, table:Array[Double], jLast:Int):Int =
    //
    //  Input:   y      = value being located in table
    //           table  = ptr. to table with monotonically increasing entries
    //           jLast  = highest table entry index to search over
    //  Output:  returns index j of table such that table[j] <= y <= table[j+1]
    //  Purpose: uses bisection method to locate the highest table index whose
    //           table entry does not exceed a given value.
    //
    //  Notes:   This function is only used in conjunction with invLookup().
    //
  {
     var j,
     j1:Int  = 0
    var j2 = jLast

    // Check if value <= first table entry
    if ( y <= table(0) ) return 0

    // Check if value >= the last entry
    if ( y >= table(jLast) )
      return jLast

    // While a portion of the table still remains
    while ( j2 - j1 > 1)
    {
      // Find midpoint of remaining portion of table
      j = (j1 + j2) >> 1

      // Value is greater or equal to midpoint: search from midpoint to j2
      if ( y >= table(j) ) j1 = j

      // Value is less than midpoint: search from j1 to midpoint
      else j2 = j
    }

    // Return the lower index of the remaining interval,
     j1
  }


}
