package swmm.configdata.types

import swmm.cmigrate.SwmmConst
import swmm.configdata.jnodes.SwmmEnum._
import swmm.runoff.infil.TCurveNum

//import swmm.configdata.jnodes.SwmmEnum.OffsetType
//import swmm.configdata.jnodes.SwmmEnum.{OffsetType, ConversionType}
import swmm.configdata.jnodes.types._
import swmm.util.Project
import swmm.util.FunctionUtil._
/**
  * Created by Ning on 12/31/15.
  */
object TLinkUtil {
  val project = Project.getInstance
  def readLink( sType:String,tok: List[String] ) :TBase =
  //
  //  Input:   type = type of link
  //  Output:  returns error code
  //  Purpose: reads data for a link from a line of input.
  //
  {
    link_readParams( sType, tok.toArray)
  }

  def link_readParams( sType:String, tok: Array[String]) :TBase =
  //
  //  Input:   j     = link index
  //           type  = link type code
  //           k     = link type index
  //           tok[] = array of string tokens
  //           ntoks = number of tokens
  //  Output:  returns an error code
  //  Purpose: reads parameters for a specific type of link from a
  //           tokenized line of input data.
  //
  {
    sType match
    {
      case "CONDUIT" =>
        conduit_readParams( tok,sType)
      case "PUMP" =>
        pump_readParams( tok,sType)
      case "ORIFICE" =>
        orifice_readParams( tok,sType)
      case "WEIR" =>
        weir_readParams( tok,sType)
      case "OUTLET" =>
        outlet_readParams( tok,sType)
      case _   =>      null
    }

  }

  //=============================================================================
  //                    C O N D U I T   M E T H O D S
  //=============================================================================

  def  conduit_readParams(  tok: Array[String],sType:String) :TBase=
    //
    //  Input:   j = link index
    //           k = conduit index
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads conduit parameters from a tokenzed line of input.
    //
  {
    var    n1, n2:TBase =null
    var x:Array[Double] = Array.fill(6)(0)
    //char*  id

    // --- check for valid ID and end node IDs
    var oLink = project.project_findID("LINK", tok(0))                // link ID
    var tLink:TConduit = null
    if (oLink.isDefined)
      tLink = oLink.get.asInstanceOf[TConduit]
    else
      tLink = new TConduit
    var o1 = project.project_findObject("NODE", tok(1))            // upstrm. node
    if(o1.isDefined)
      n1 =o1.get
    var o2 = project.project_findObject("NODE", tok(2))            // dwnstrm. node
    if(o2.isDefined)
      n2 = o2.get

    // --- parse length & Mannings N
     x(0) =tok(3).toDouble
     x(1) =tok(4).toDouble

    // --- parse offsets
    if ( project.LinkOffsets == OffsetType.valueOf("ELEV_OFFSET") && tok(5).equals("*")) x(2) = SwmmConst.MISSING
    else
      {
        x(2) =tok(5).toDouble
      }
    if ( project.LinkOffsets == OffsetType.valueOf("ELEV_OFFSET") && tok(6).equals("*") ) x(3) = SwmmConst.MISSING
    else
      {
        x(3) =tok(6).toDouble
      }

    // --- parse optional parameters
    x(4) = tok(7).toDouble                                       // init. flow

//    if ( ntoks >= 8 )
//    {
//      if ( !getDouble(tok(7], &x(4]) )
//      return error_setInpError(ERR_NUMBER, tok[7])
//    }
    x(5) = tok(8).toDouble
//    if ( ntoks >= 9 )
//    {
//      if ( !getDouble(tok[8], &x[5]) )
//      return error_setInpError(ERR_NUMBER, tok[8])
//    }
    populateCommonParams(tLink, sType, n1, n2)
    tLink.length    = x(0) / UCF(ConversionType.LENGTH.ordinal)
    tLink.modLength = tLink.length
    tLink.roughness = x(1)
    tLink.offset1      = x(2) / UCF(ConversionType.LENGTH.ordinal)
    tLink.offset2      = x(3) / UCF(ConversionType.LENGTH.ordinal)
    tLink.q0           = x(4) / UCF(ConversionType.FLOW.ordinal())
    tLink.qLimit       = x(5) / UCF(ConversionType.FLOW.ordinal)
    // --- add parameters to data base
    project.project_addObject(sType,tok(0) ,tLink)
    tLink
  }
  //=============================================================================

 def  link_readXsectParams(tok:Array[String]) =
    //
    //  Input:   tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads a link's cross section parameters from a tokenized
    //           line of input data.
    //
  {
    //int    i, j, k
    val x: Array[Double] = Array.fill(4)(0)
    var tLink:TLink=null
    // --- get index of link
    val oLink = project.project_findObject("LINK", tok(0))
    if(oLink.isDefined)
      tLink = oLink.get.asInstanceOf[TLink]
    // --- get code of xsection shape
//    k = findmatch(tok[1], XsectTypeWords)
//    if ( k < 0 ) return error_setInpError(ERR_KEYWORD, tok[1])

    // --- assign default number of barrels to conduit
    if ( tLink.sType.equals("CONDUIT" ) )
      project.Conduit(tLink.subIndex).barrels = 1

    // --- assume link is not a culvert
    tLink.xsect.culvertCode = 0

    // --- for irregular shape, find index of transect object
    if ( tok(1).equals("IRREGULAR") )
    {
      val oTransact  = project.project_findObject("TRANSECT", tok(2))
//      if ( i < 0 ) return error_setInpError(ERR_NAME, tok[2])
      tLink.xsect.sType = tok(1)
      if(oTransact.isDefined)
        tLink.xsect.transect = oTransact.get.asInstanceOf[TTransect]
    }
    else
    {
      // --- parse max. depth & shape curve for a custom shape
      if (tok(1).equals("CUSTOM") )
      {
        x(0) =tok(2).toDouble
        val oCurve = project.project_findObject("CURVE", tok(3))
        if(oCurve.isDefined)
          tLink.xsect.transect = oCurve.get.asInstanceOf[TTransect]
       tLink.xsect.sType = tok(1)
       // tLink.xsect.transect = oTr
        tLink.xsect.yFull = x(0) / UCF(ConversionType.LENGTH.ordinal)
      }

      // --- parse and save geometric parameters
      else for (i <- 2 to 5)
      {
        x(i-2) =tok(i).toDouble
      }
      TXSectUtil.xsect_setParams(tLink.xsect, tok(1), x, UCF(ConversionType.LENGTH.ordinal()))

      // --- parse number of barrels if present
      if (tLink.sType.equals("CONDUIT"))
      {

         project.Conduit(tLink.subIndex).barrels = tok(6).toInt
      }

      // --- parse culvert code if present
      if ( tLink.sType.equals("CONDUIT") )
      {
//        i = atoi(tok[7])
         tLink.xsect.culvertCode = tok(7).toInt
      }

    }
  //  return 0
  }

  //=============================================================================
  //                        P U M P   M E T H O D S
  //=============================================================================

  def  pump_readParams(tok: Array[String],sType:String):TLink =
    //
    //  Input:   j = link index
    //           k = pump index
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads pump parameters from a tokenized line of input.
    //
  {
    var    n1, n2:TBase =null
    var x:Array[Double] = Array.fill(4)(0)
    //char*  id

    // --- check for valid ID and end node IDs
    var oLink = project.project_findID("LINK", tok(0))                // link ID
  var tLink:TPump = null
    if (oLink.isDefined)
      tLink = oLink.get.asInstanceOf[TPump]
    else
      tLink = new TPump
    var o1 = project.project_findObject("NODE", tok(1))            // upstrm. node
    if(o1.isDefined)
      n1 =o1.get
    var o2 = project.project_findObject("NODE", tok(2))            // dwnstrm. node
    if(o2.isDefined)
      n2 = o2.get
    // --- parse curve name
    x(0) = -1.0
//    if ( ntoks >= 4 )
//    {
//      if ( !strcomp(tok[3],"*") )
//      {
//        m = project_findObject(CURVE, tok[3])
//        if ( m < 0 ) return error_setInpError(ERR_NAME, tok[3])
//        x[0] = m
//      }
//    }
    //FIXME now
    //var tCurve:TCurveNum =null
//    if(!"*".equals(tok(3)))
//      {
//        val oCurve= project.project_findObject("CURVE",tok(3))
//        //FIXME:NOW
////        if(oCurve.isDefined)
////          tCurve = oCurve.get.asInstanceOf[TCurveNum]
//      }
    // --- parse init. status if present
    //x(1) = 1.0
//    if ( ntoks >= 5 )
//    {
//      m = findmatch(tok[4], OffOnWords)
//      if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok[4])
//      x[1] = m
//    }
    var m =false
    if(tok(4).equals("ON"))
      {
        m = true
      }
    // --- parse startup/shutoff depths if present
   // x[2] = 0.0
//    if ( ntoks >= 6 )
//    {
//      if ( !getDouble(tok[5], &x[2]) || x[2] < 0.0)
//      return error_setInpError(ERR_NUMBER, tok[5])
//    }
    x(2) = tok(5).toDouble
  //  x[3] = 0.0
//    if ( ntoks >= 7 )
//    {
//      if ( !getDouble(tok[6], &x[3]) || x[3] < 0.0 )
//      return error_setInpError(ERR_NUMBER, tok[6])
//    }
    x(3) = tok(6).toDouble

    // --- add parameters to pump object
    populateCommonParams(tLink, sType, n1, n2)
    //FIXME NOW
  //  tLink.pumpCurve    =x.head.toInt
    tLink.hasFlapGate  = false
    tLink.initSetting  = tok(4)
    tLink.yOn          = x(2) / UCF(ConversionType.LENGTH.ordinal)
    tLink.yOff         = x(3) / UCF(ConversionType.LENGTH.ordinal)
    tLink.xMin         = 0.0
    tLink.xMax         = 0.0
    // --- add parameters to data base
    project.project_addObject(sType,tok(0) ,tLink)

    tLink
  }

  //=============================================================================
  //                    O R I F I C E   M E T H O D S
  //=============================================================================

  def  orifice_readParams(tok: Array[String],sType:String):TLink =
    //
    //  Input:   j = link index
    //           k = orifice index
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads orifice parameters from a tokenized line of input.
    //
  {
    var    n1, n2:TBase =null
    var x:Array[Double] = Array.fill(5)(0)
    //char*  id

    // --- check for valid ID and end node IDs
    var oLink = project.project_findID("LINK", tok(0))                // link ID
  var tLink:TOrifice = null
    if (oLink.isDefined)
      tLink = oLink.get.asInstanceOf[TOrifice]
    else
      tLink = new TOrifice
    var o1 = project.project_findObject("NODE", tok(1))            // upstrm. node
    if(o1.isDefined)
      n1 =o1.get
    var o2 = project.project_findObject("NODE", tok(2))            // dwnstrm. node
    if(o2.isDefined)
      n2 = o2.get

    // --- parse orifice parameters
  //  m = findmatch(tok[3], OrificeTypeWords)
  // if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok[3])
  //  x[0] = m                                              // type
    if ( project.LinkOffsets.name().equals( "ELEV_OFFSET") && tok(4).equals("*"))
      x(1) = SwmmConst.MISSING
    else
      x(1) =tok(4).toDouble                  // crest height
    x(2) =tok(5).toDouble
//    if ( ! getDouble(tok[5], &x[2]) || x[2] < 0.0 )        // cDisch
//    return error_setInpError(ERR_NUMBER, tok[5])
//    x[3] = 0.0
//    if ( ntoks >= 7 )
//    {
//      m = findmatch(tok[6], NoYesWords)
//      if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok[6])
//      x[3] = m                                          // flap gate
//    }
 //     val hasFlapGate ="YES".equals(tok(6))
//    x[4] = 0.0
//    if ( ntoks >= 8 )
//    {
//      if ( ! getDouble(tok[7], &x[4]) || x[4] < 0.0 )    // orate
//      return error_setInpError(ERR_NUMBER, tok[7])
//    }
    x(4) =tok(7).toDouble


    // --- add parameters to pump object
    populateCommonParams(tLink, sType, n1, n2)
    // --- add parameters to orifice object
    tLink.sType      = tok(3)
    tLink.offset1      = x(1) / UCF(ConversionType.LENGTH.ordinal)
    tLink.offset2      = tLink.offset1
    tLink.cDisch    = x(2)
    tLink.hasFlapGate  = "YES".equals(tok(6))
    tLink.orate     = x(4) * 3600.0   // --- add parameters to data base
    project.project_addObject(sType,tok(0) ,tLink)

    tLink
  }
  //=============================================================================
  //                           W E I R   M E T H O D S
  //=============================================================================

  def  weir_readParams(tok: Array[String],sType:String):TLink =
    //
    //  Input:   j = link index
    //           k = weir index
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads weir parameters from a tokenized line of input.
    //
  {
    var    n1, n2:TBase =null
    var x:Array[Double] = Array.fill(7)(0)
    //char*  id

    // --- check for valid ID and end node IDs
    var oLink = project.project_findID("LINK", tok(0))                // link ID
  var tLink:TWeir = null
    if (oLink.isDefined)
      tLink = oLink.get.asInstanceOf[TWeir]
    else
      tLink = new TWeir
    var o1 = project.project_findObject("NODE", tok(1))            // upstrm. node
    if(o1.isDefined)
      n1 =o1.get
    var o2 = project.project_findObject("NODE", tok(2))            // dwnstrm. node
    if(o2.isDefined)
      n2 = o2.get

    // --- parse weir parameters
//    m = findmatch(tok[3], WeirTypeWords)
//    if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok[3])
//    x[0] = m                                              // type
    if (( project.LinkOffsets.name.equals("ELEV_OFFSET")) && tok(4).equals("*" ))
      x(1) = SwmmConst.MISSING
    else
      x(1) = tok(4).toDouble    // height
//    if ( ! getDouble(tok[5], &x[2]) || x[2] < 0.0 )        // cDisch1
//    return error_setInpError(ERR_NUMBER, tok[5])
    x(2) =tok(5).toDouble
//    x[3] = 0.0
//    x[4] = 0.0
//    x[5] = 0.0
//    x[6] = 1.0                                                                //(5.1.007)
//    if ( ntoks >= 7 && *tok[6] != '*' )                                        //(5.1.007)
//    {
//      m = findmatch(tok[6], NoYesWords)
//      if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok[6])
//      x[3] = m                                          // flap gate
//    }
//    if ( ntoks >= 8 && *tok[7] != '*' )                                        //(5.1.007)
//    {
//      if ( ! getDouble(tok[7], &x[4]) || x[4] < 0.0 )     // endCon
//      return error_setInpError(ERR_NUMBER, tok[7])
//    }
    x(4) =tok(7).toDouble
//    if ( ntoks >= 9 && *tok[8] != '*' )                                        //(5.1.007)
//    {
//      if ( ! getDouble(tok[8], &x[5]) || x[5] < 0.0 )     // cDisch2
//      return error_setInpError(ERR_NUMBER, tok[8])
//    }
    if(!"*".equals(tok(8)))
      x(5) = tok(8).toDouble
    else
      x(5) = -1
    ////  Following segment added for release 5.1.007.  ////                       //(5.1.007)
//    if ( ntoks >= 10 && *tok[9] != '*' )
//    {
//      m = findmatch(tok[9], NoYesWords)
//      if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok[9])
//      x[6] = m                                          // canSurcharge
//    }

    // --- add parameters to pump object
    populateCommonParams(tLink, sType, n1, n2)
    // --- add parameters to orifice object
    //FIXME NOW
   // tLink.sType         = (int)x[0]
    tLink.offset1      = x(1) / UCF(ConversionType.LENGTH.ordinal)
    tLink.offset2      = tLink.offset1
    tLink.cDisch1      = x(2)
    tLink.hasFlapGate  = "YES".equals(tok(6))
    tLink.endCon       = x(4)
    tLink.cDisch2      = x(5)
    tLink.canSurcharge = "YES".equals(tok(9))
    project.project_addObject(sType,tok(0) ,tLink)

    tLink
  }

  //=============================================================================

  def link_readLossParams(tok:Array[String]) =
    //
    //  Input:   tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads local loss parameters for a link from a tokenized
    //           line of input data.
    //
    //  Format:  LinkID  cInlet  cOutlet  cAvg  FlapGate(YES/NO)  SeepRate         //(5.1.007)
    //
  {
    var x:Array[Double]=Array.fill(3)(0)


    //if ( ntoks < 4 ) return error_setInpError(ERR_ITEMS, "")
    val oLink  = project.project_findObject("LINK", tok(0))
    if(oLink.isDefined)
    {
      val tLink = oLink.get.asInstanceOf[TLink]
      tLink.cLossInlet   = tok(1).toDouble
      tLink.cLossOutlet  = tok(2).toDouble
      tLink.cLossAvg     = tok(3).toDouble
      if("YES".equals(tok(4)))
        tLink.hasFlapGate =true
      else
        tLink.hasFlapGate =false
      val seepRate = tok(5).toDouble
      tLink.seepRate =seepRate/UCF(ConversionType.RAINFALL.ordinal())
    }
//
//
//    k = 0
//    if ( ntoks >= 5 )
//    {
//      k = findmatch(tok[4], NoYesWords)
//      if ( k < 0 ) return error_setInpError(ERR_KEYWORD, tok[4])
//    }
//    if ( ntoks >= 6 )
//    {
//      if ( ! getDouble(tok[5], &seepRate) )
//      return error_setInpError(ERR_NUMBER, tok[5])
//    }
//    Link[j].cLossInlet   = x[0]
//    Link[j].cLossOutlet  = x[1]
//    Link[j].cLossAvg     = x[2]
//    Link[j].hasFlapGate  = k
//    Link[j].seepRate     = seepRate / UCF(RAINFALL)
//    return 0
  }

  //=============================================================================
  //               O U T L E T    D E V I C E    M E T H O D S
  //=============================================================================

  def outlet_readParams(tok: Array[String],sType:String):TLink =
    //
    //  Input:   j = link index
    //           k = outlet index
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads outlet parameters from a tokenized  line of input.
    //
  {
    var n:Int =0
    var    n1, n2:TBase =null
    var x:Array[Double] = Array.fill(6)(0)
    //char*  id

    // --- check for valid ID and end node IDs
    var oLink = project.project_findID("LINK", tok(0))                // link ID
  var tLink:TOutlet = null
    if (oLink.isDefined)
      tLink = oLink.get.asInstanceOf[TOutlet]
    else
      tLink = new TOutlet
    var o1 = project.project_findObject("NODE", tok(1))            // upstrm. node
    if(o1.isDefined)
      n1 =o1.get
    var o2 = project.project_findObject("NODE", tok(2))            // dwnstrm. node
    if(o2.isDefined)
      n2 = o2.get


    // --- get height above invert
    if ( project.LinkOffsets.name().equals("ELEV_OFFSET") && (tok(3).equals("*")))
      x(0) = SwmmConst.MISSING
    else
    {
//      if ( ! getDouble(tok[3], &x[0]) )
//      return error_setInpError(ERR_NUMBER, tok[3])
//      if ( LinkOffsets == DEPTH_OFFSET && x[0] < 0.0 ) x[0] = 0.0
      x(0) = tok(3).toDouble
    }

    // --- see if outlet flow relation is tabular or functional
  //  m = findmatch(tok[4], RelationWords)
 //   if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok[4])
//    x[1] = 0.0
//    x[2] = 0.0
//    x[3] = -1.0
//    x[4] = 0.0

    // --- see if rating curve is head or depth based
    x(5)  = NodeResultType.valueOf("NODE_DEPTH").ordinal()                                //default is depth-based
//    s = strtok(tok[4], "/")                          //parse token for
//    s = strtok(NULL, "/")                            //  qualifier term
//    if ( strcomp(s, w_HEAD) ) x[5] = NODE_HEAD       //check if its "HEAD"

    // --- get params. for functional outlet device
    if ( tok(4).equals("FUNCTIONAL") )
    {
      //if ( ntoks < 7 ) return error_setInpError(ERR_ITEMS, "")
      //if ( ! getDouble(tok[5], &x[1]) )
      //return error_setInpError(ERR_NUMBER, tok[5])
      x(1) =tok(5).toDouble
      //if ( ! getDouble(tok[6], &x[2]) )
      //return error_setInpError(ERR_NUMBER, tok[6])
      x(2) =tok(6).toDouble
      n = 7
    }

    // --- get name of outlet rating curve
    else
    {
      val oCurve  = project.project_findObject("CURVE", tok(5))
      if(oCurve.isDefined)
        tLink.qCurve     = oCurve.get.asInstanceOf[TCurveNum]
      n = 6
    }

//    // --- check if flap gate specified
//    if ( ntoks > n)
//    {
//      i = findmatch(tok[n], NoYesWords)
//      if ( i < 0 ) return error_setInpError(ERR_KEYWORD, tok[n])
//      x[4] = i
//    }
    var hasFlap =false
    if(tok(n).equals("YES"))
      hasFlap =true
    populateCommonParams(tLink, sType, n1, n2)
    // --- add parameters to orifice object
    tLink.offset1      = x(0) / UCF(ConversionType.LENGTH.ordinal)
    tLink.offset2      = tLink.offset1
    tLink.qCoeff     = x(1)
    tLink.qExpon     = x(2)
//    tLink.qCurve     = x(3)
    tLink.hasFlapGate  = hasFlap
    //FIXME:NOW
//    tLink.curveType  = x(5)
//
//    TXSectUtil.xsect_setParams(tLink.xsect, DUMMY, NULL, 0.0)
    project.project_addObject(sType,tok(0) ,tLink)
    tLink
  }
  //=============================================================================

  def  link_setParams(tLink:TConduit, sType:String, n1:TBase, n2:TBase,   x:Array[Double])
  //
  //  Input:   j   = link index
  //           type = link type code
  //           n1   = index of upstream node
  //           n2   = index of downstream node
  //           k    = index of link's sub-type
  //           x    = array of parameter values
  //  Output:  none
  //  Purpose: sets parameters for a link.
  //
  {
    populateCommonParams(tLink, sType, n1, n2)

//    switch (sType)
//    {
//
//
//      case OUTLET:
//      Link[j].offset1      = x[0] / UCF(ConversionType.LENGTH.ordinal)
//      Link[j].offset2      = Link[j].offset1
//      Outlet[k].qCoeff     = x[1]
//      Outlet[k].qExpon     = x[2]
//      Outlet[k].qCurve     = (int)x[3]
//      Link[j].hasFlapGate  = (x[4] > 0.0) ? 1 : 0
//      Outlet[k].curveType  = (int)x[5]
//
//      xsect_setParams(&Link[j].xsect, DUMMY, NULL, 0.0)
//      break
//
//    }
  }

  def populateCommonParams(tLink: TLink, sType: String, n1: TBase, n2: TBase): Unit = {
    tLink.node1 = n1
    tLink.node2 = n2
    tLink.sType = sType
    //    tLink.subIndex    = k
    tLink.offset1 = 0.0
    tLink.offset2 = 0.0
    tLink.q0 = 0.0
    tLink.qFull = 0.0
    tLink.setting = 1.0
    tLink.targetSetting = 1.0
    tLink.hasFlapGate = false
    tLink.qLimit = 0.0 // 0 means that no limit is defined
    tLink.direction = 1
  }


}
