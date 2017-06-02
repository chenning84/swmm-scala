package swmm.configdata.types

import swmm.cmigrate.{SWMMText, Keywords}
import swmm.configdata.jnodes.SwmmEnum
import swmm.configdata.jnodes.types._
import swmm.configdata.jnodes.SwmmEnum.{EvapType, ConversionType}
import swmm.runoff.infil.SInfil
import swmm.util.{ErrorUtil, Project}
import swmm.util.FunctionUtil._
/**
 * Created by Ning on 12/28/15.
  *
 */
object TNodeUtil{
  val project = Project.getInstance
  def  readNode( sType:String,tok: List[String] ) :TBase =
  //
  //  Input:   type = type of node
  //  Output:  returns error code
  //  Purpose: reads data for a node from a line of input.
  //
  {
    val i = -1
    node_readParams( sType, tok.toArray)
  }
  //=============================================================================

  def node_readParams( sType:String, tok: Array[String]) :TBase =
  //
  //  Input:   j = node index
  //           type = node type code
  //           k = index of node type
  //           tok() = array of string tokens
  //           tok.length = number of tokens
  //  Output:  returns an error code
  //  Purpose: reads node properties from a tokenized line of input.
  //
  {
    sType match 
    {
      case "JUNCTION" =>
        junc_readParams( tok,sType)
      case "OUTFALL" =>
        outfall_readParams( tok,sType)
      case "STORAGE" => 
        storage_readParams( tok,sType)
      case "DIVIDER" => 
        divider_readParams( tok,sType)
      case _   =>      null
    }
  }

  //=============================================================================
  //                   J U N C T I O N   M E T H O D S
  //=============================================================================

  def junc_readParams( tok: Array[String], sType: String): TBase =
  //
  //  Input:   j = node index
  //           k = junction index
  //           tok() = array of string tokens
  //           tok.length = number of tokens
  //  Output:  returns an error message
  //  Purpose: reads a junction's properties from a tokenized line of input.
  //
  //  Format of input line is:
  //     nodeID  elev  maxDepth  initDepth  surDepth  aPond
  {


    val x: Array[Double] = Array.fill(6)(0)

    val id = tok(0)
    val oNode = project.project_findID("NODE", tok(0))
    var tNode: TNode = null

    if (oNode.isDefined) {
      tNode = oNode.get.asInstanceOf[TNode]

    }
    else {
      tNode = new TNode
    }
    // --- parse invert elev., max. depth, init. depth, surcharged depth,
    //     & ponded area values
    for (i <- 1 to 5) {
      x(i - 1) = 0.0
      if (i < tok.length) {
        x(i - 1) = tok(i).toDouble
      }
    }

    // --- check for non-negative values (except for invert elev.)
    //    for ( i <-1 to 4  )
    //    {
    //        if ( x(i) < 0.0 ) return error_setInpError(ERR_NUMBER, tok(i+1))
    //    }

    populateCommonParam(sType, x, tNode)

    tNode.fullDepth = x(1) / UCF(ConversionType.LENGTH.ordinal)
    tNode.initDepth = x(2) / UCF(ConversionType.LENGTH.ordinal)
    tNode.surDepth = x(3) / UCF(ConversionType.LENGTH.ordinal)
    tNode.pondedArea = x(4) / (UCF(ConversionType.LENGTH.ordinal) * UCF(ConversionType.LENGTH.ordinal))

    // --- add parameters to junction object
    tNode.id = id
   // tNode.pos = j
    project.project_addObject(sType, id, tNode)
    tNode

  }


  //=============================================================================
  //                   S T O R A G E   M E T H O D S
  //=============================================================================

  def populateCommonParam(sType: String, x: Array[Double], tNode: TNode): Unit = {
    tNode.sType = sType
 //   tNode.subIndex = k
    tNode.invertElev = x(0) / UCF(ConversionType.LENGTH.ordinal)
    tNode.crownElev = tNode.invertElev
    tNode.initDepth = 0.0
    tNode.newVolume = 0.0
    tNode.fullVolume = 0.0
    tNode.fullDepth = 0.0
    tNode.surDepth = 0.0
    tNode.pondedArea = 0.0
    tNode.degree = 0
  }

  def storage_readParams(tok: Array[String],sType:String):TBase =
//
//  Input:   j = node index
//           k = storage unit index
//           tok() = array of string tokens
//           tok.length = number of tokens
//  Output:  returns an error message
//  Purpose: reads a storage unit's properties from a tokenized line of input.
//
//  Format of input line is:
//     nodeID  elev  maxDepth  initDepth  FUNCTIONAL a1 a2 a0 aPond fEvap (infil)
//     nodeID  elev  maxDepth  initDepth  TABULAR    curveID  aPond fEvap (infil)
//
{
  var n:Int =0
  var    m:Option[TBase] =None
  val x:Array[Double]= Array.fill(9)(0)

    // --- get ID name
  //  var id = project.project_findID("NODE", tok(0))
  val id = tok(0)
  val oNode = project.project_findID("NODE", tok(0))
  var tNode: TStorage = null

  if (oNode.isDefined ) {
    tNode = oNode.get.asInstanceOf[TStorage]

  }
  else {
    tNode = new TStorage
  }

    // --- get invert elev, max. depth, & init. depth
    for ( i <- 1 to 3)
    {
      x(i-1) =tok(i).toDouble
    }

    // --- get surf. area relation type
  //  m = findmatch(tok(4), RelationWords)
  //  if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok(4))
    x(3) = 0.0                        // a1 
    x(4) = 0.0                        // a2
    x(5) = 0.0                        // a0
    x(6) = -1.0                       // curveID
    x(7) = 0.0                        // aPond
    x(8) = 0.0                        // fEvap

    // --- get surf. area function coeffs.
    if ( tok(4).equals(SWMMText.w_FUNCTIONAL))
    {
        for (i <- 5 to 7)
        {
            if ( i < tok.length )
            {
              x(i-2) = tok(i).toDouble
            }
        }
        n = 8
    }

    // --- get surf. area curve name
    else
    {
        m = project.project_findObject("CURVE", tok(5))
        x(6) = m.get.pos
        n = 6
    }

    // --- ignore next token if present (deprecated ponded area property)      //(5.1.007) 
    if ( tok.length > n)
    {
      x(7) =tok(n).toDouble
        n = n+1
    }

    // --- get evaporation fraction if present
    if ( tok.length > n )
    {
      x(8) =tok(n).toDouble
        n= n+1
    }
  // --- read exfiltration parameters if present
  if ( tok.length > n )
    SInfil.exfil_readStorageParams( tok, n,tNode)         //(5.1.007)
    // --- add parameters to storage unit object
  populateCommonParam( sType, x, tNode)

  tNode.fullDepth  = x(1) / UCF(ConversionType.LENGTH.ordinal)
  tNode.initDepth  = x(2) / UCF(ConversionType.LENGTH.ordinal)
  //For storage
  tNode.aCoeff  = x(3)
  tNode.aExpon  = x(4)
  tNode.aConst  = x(5)
  tNode.aCurve  = tok(5)
  // x(7) (ponded depth) is deprecated.                                  //(5.1.007)
  tNode.fEvap   = x(8)
  project.project_addObject(sType, id, tNode)
    tNode

}

  //=============================================================================
  //                    O U T F A L L   M E T H O D S
  //=============================================================================

  def outfall_readParams( tok: Array[String],sType:String):TBase =
//
//  Input:   j = node index
//           k = outfall index
//           tok() = array of string tokens
//           tok.length = number of tokens
//  Output:  returns an error message
//  Purpose: reads an outfall's properties from a tokenized line of input.
//
//  Format of input line is:
//    nodeID  elev  FIXED  fixedStage (flapGate) (routeTo)
//    nodeID  elev  TIDAL  curveID (flapGate) (routeTo)
//    nodeID  elev  TIMESERIES  tseriesID (flapGate) (routTo)
//    nodeID  elev  FREE (flapGate) (routeTo)
//    nodeID  elev  NORMAL (flapGate) (routeTo)
//
{
    var      n:Int =0
  var tCurve,tSeries,tSubcatch:String =""
    var m:Option[TBase] =None
    val x:Array[Double]= Array.fill(7)(0)
  val id = tok(0)
  val oNode = project.project_findID("NODE", tok(0))
  var tNode: TOutfall = null

  if (oNode.isDefined ) {
    tNode = oNode.get.asInstanceOf[TOutfall]

  }
  else {
    tNode = new TOutfall
  }
  x(0) =tok(1).toDouble
 //   val i = findmatch(tok(2), OutfallTypeWords)               // outfall type
 //   if ( i < 0 ) return error_setInpError(ERR_KEYWORD, tok(2))
   val outfllType = tok(2)
 //   x(1) = i                                              // outfall type
    x(2) = 0.0                                            // fixed stage
    x(3) = -1.0                                            // tidal curve
    x(4) = -1.0                                            // tide series
    x(5) = .0                                             // flap gate
    x(6) = -1.0                                            // route to subcatch//(5.1.008)

    n = 4
//    if ( i >= FIXED_OUTFALL )
 //   {
  //      if ( tok.length < 4 ) return error_setInpError(ERR_ITEMS, "")
        n = 5
      tok(2) match
        {
        case "FIXED_OUTFALL" =>                                // fixed stage
          x(2) =tok(3).toDouble
//          if ( ! getDouble(tok(3), x(2)) )
//              return error_setInpError(ERR_NUMBER, tok(3))

        case "TIDAL_OUTFALL" =>                                // tidal curve
          m = project.project_findObject("CURVE", tok(3))
          if(m.isDefined)
            tCurve =m.get.id
         // x(3) = m

        case "TIMESERIES_OUTFALL"=>                           // stage time series
          m = project project_findObject("TSERIES", tok(3))
          if(m.isDefined)
            tSeries =m.get.id
          //x(4) = m
          project.Tseries(m.get.pos).refersTo = SwmmEnum.EvapType.valueOf("TIMESERIES_OUTFALL")
        }
 //   }
    if ( tok.length == n )
    {
//        m = findmatch(tok(n-1), NoYesWords)               // flap gate
//        if ( m < 0 ) return error_setInpError(ERR_KEYWORD, tok(n-1))
        x(5) =tok(n-1).toDouble
    }

////  Added for release 5.1.008.  ////                                         //(5.1.008)
    if ( tok.length == n+1)
    {
        m = project.findSubcatch(tok(n))
       // if ( m < 0 ) return error_setInpError(ERR_NAME, tok(n))
      if(m.isDefined)
       // x(6) = m
       tSubcatch =m.get.id
    }
////

  tNode.sType        = sType
  tNode.fixedStage  = x(2) / UCF(ConversionType.LENGTH.ordinal)
  tNode.tideCurve   = tCurve
  tNode.stageSeries = tSeries
  tNode.hasFlapGate = tok(n-1)

  ////  Following code segment added to release 5.1.008.  ////                   //(5.1.008)

  tNode.routeTo     = tSubcatch
  tNode.wRouted     = -1.0
  if (( tNode.routeTo!=null ) &&(!"".equals(tSubcatch)))
  {
    //FIXME: check what's going on
//            tNode.wRouted =
//              (double *) calloc(Nobjects(POLLUT), sizeof(double))
  }

  project.project_addObject(sType, id, tNode)
  tNode

}

  //=============================================================================
  //                   D I V I D E R   M E T H O D S
  //=============================================================================

  def  divider_readParams( tok: Array[String],sType:String):TBase =
//
//  Input:   j = node index
//           k = divider index
//           tok() = array of string tokens
//           tok.length = number of tokens
//  Output:  returns an error message
//  Purpose: reads a flow divider's properties from a tokenized line of input.
//
//  Format of input line is:
//    nodeID  elev  divLink  TABULAR  curveID (optional params)
//    nodeID  elev  divLink  OVERFLOW (optional params)
//    nodeID  elev  divLink  CUTOFF  qCutoff (optional params)
//    nodeID  elev  divLink  WEIR    qMin  dhMax  cWeir (optional params)
//  where optional params are:
//    maxDepth  initDepth  surDepth  aPond    
//
{
   var  n,mm:Int =0


  var m,mm2:String =""

  val x:Array[Double]= Array.fill(11)(0)
    // --- get ID name

  //  var id = project.project_findID("NODE", tok(0))
  val id = tok(0)
  val oNode = project.project_findID("NODE", tok(0))
  var tNode: TDivider = null

  if (oNode.isDefined ) {
    tNode = oNode.get.asInstanceOf[TDivider]

  }
  else {
    tNode = new TDivider
  }

    // --- get invert elev.
    x(0) =tok(1).toDouble
//    if ( ! getDouble(tok(1), x(0)) ) return error_setInpError(ERR_NUMBER, tok(1))

    // --- initialize parameter values
    for ( i<- 1 to 10 ) x(i) = 0.0

    // --- check if no diverted link supplied
    if ( tok(2).length == 0 ||  "*".equals(tok(2))) x(1) = -1.0

    // --- otherwise get index of diverted link
    else
    {
        val ml = project.project_findObject("LINK", tok(2))
        if(ml.isDefined)
          m =ml.get.id
    }
    
    // --- get divider type
	n = 4
//    var  m1 =tok(3)
//    if ( m1 < 0 ) return error_setInpError(ERR_KEYWORD, tok(3))


    // --- get index of flow diversion curve for Tabular divider
    x(3) = -1
    if ( "TABULAR_DIVIDER".equals(tok(3) ))
    {
       // if ( tok.length < 5 ) return error_setInpError(ERR_ITEMS, "")
        val m2 = project.project_findObject("CURVE", tok(4))
        //if ( m2 < 0 ) return error_setInpError(ERR_NAME, tok(4))
      //  x(3) = m2
        if(m2.isDefined)
          mm2 = m2.get.id
        n = 5
    }

    // --- get cutoff flow for Cutoff divider
    if ( "CUTOFF_DIVIDER".equals(tok(3) ))
    {
      //  if ( tok.length < 5 ) return error_setInpError(ERR_ITEMS, "")
      x(4) =tok(4).toDouble
//        if ( ! getDouble(tok(4), x(4)) )
//            return error_setInpError(ERR_NUMBER, tok(4))
        n = 5
    }

    // --- get qmin, dhMax, & cWeir for Weir divider
    if ( "WEIR_DIVIDER".equals(tok(3) ))
    {
    //    if ( tok.length < 7 ) return error_setInpError(ERR_ITEMS, "")
        for (i <- 4 to 6)
              x(i) =tok(i).toDouble
//             if ( ! getDouble(tok(i), x(i)) )
//                 return error_setInpError(ERR_NUMBER, tok(i))
        n = 7
    }

    // --- no parameters needed for Overflow divider
    if ( "OVERFLOW_DIVIDER".equals(tok(3)) ) n = 4

    // --- retrieve optional full depth, init. depth, surcharged depth
    //      & ponded area
    mm = 7
    for (i <- n to tok.length)
    {
      if(mm<11)
        {
          x(mm) = tok(i).toDouble
          mm = mm+1

        }
    }
 
    // --- add parameters to data base
  populateCommonParam( sType, x, tNode)
//    node_setParams(j, "DIVIDER", k, x)
  tNode.link      = m
  tNode.sType      = sType
  tNode.flowCurve = mm2
  tNode.qMin      = x(4) / UCF(ConversionType.FLOW.ordinal)
  tNode.dhMax     = x(5)
  tNode.cWeir     = x(6)
  tNode.fullDepth    = x(7) / UCF(ConversionType.LENGTH.ordinal)
  tNode.initDepth    = x(8) / UCF(ConversionType.LENGTH.ordinal)
  tNode.surDepth     = x(9) / UCF(ConversionType.LENGTH.ordinal)
  tNode.pondedArea   = x(10) / (UCF(ConversionType.LENGTH.ordinal)*UCF(ConversionType.LENGTH.ordinal))
  project.project_addObject(sType, id, tNode)
  tNode

}
//
//  def  node_setParams(j:Int, sType:String, k:Int, x:Array[Double]) =
//  //
//  //  Input:   j = node index
//  //           type = node type code
//  //           k = index of node type
//  //           x() = array of property values
//  //  Output:  none
//  //  Purpose: assigns property values to a node.
//  //
//  {
//    populateCommonParam(k, sType, x, project.Node(j))
//
//    sType match
//    {
//
//      case "OUTFALL"=>
//
//
//
//
//
//
//    }
//  }

}

