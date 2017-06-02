package swmm.runoff

import swmm.cmigrate.{SWMMText, Keywords}
import swmm.configdata.jnodes.SwmmEnum
import swmm.configdata.jnodes.SwmmEnum._
import swmm.configdata.jnodes.SwmmEnum.{SubAreaType, ConversionType}
import swmm.configdata.jnodes.types._
import swmm.configdata.types.TGage
import swmm.util.Project
import swmm.util.FunctionUtil.UCF
/**
  * Created by ning on 12/6/15.
  */
object SSubcatch {
  val gInstance = Project.getInstance

  val tSeries = gInstance.Tseries
  val tSnow = gInstance.Snow
  val tFrain = gInstance.Frain
  val tTemp = gInstance.Temp
  val tAdjust = gInstance.Adjust
  val NO_DATE = Keywords.NO_DATE
  val eRAINFALL = ConversionType.RAINFALL.ordinal()
  val eRAINDEPTH = ConversionType.RAINDEPTH.ordinal()
  val gIgnoreSnowmelt = gInstance.IgnoreSnowmelt
  val gIgnoreRainfall = gInstance.IgnoreRainfall
  val eLENGTH = SwmmEnum.ConversionType.LENGTH.ordinal()
  val eLANDAREA = SwmmEnum.ConversionType.LANDAREA.ordinal()

  val TRUE: Int = 1
  val FALSE: Int = 0
  val OneSecond = 1.1574074e-5 // TODO: Need to further dicide the value
  val project = Project.getInstance
  val RunoffRoutingWords =Array[String] (SWMMText.w_OUTLET,  SWMMText.w_IMPERV, SWMMText.w_PERV)
  //=============================================================================

 def subcatch_readParams(tok: List[String]):TSubcatch =
    //
    //  Input:   j = subcatchment index
    //           tok(] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads subcatchment parameters from a tokenized  line of input data.
    //
    //  Data has format:
    //    Name  RainGage  Outlet  Area  %Imperv  Width  Slope CurbLength  Snowpack  
    //
  {
  //  var    i, k, m:Int =0

    
    var x = new Array[Option[TBase]](3)
    var y= new Array[Double](6)

    // --- check that named subcatch exists
    val id = project.findSubcatch(tok(0))
    if(id.isDefined)
      return id.filter(_!=null).asInstanceOf[TSubcatch]

    // --- check that rain gage exists
    var k = project.findGage(tok(1))
 //   if ( k < 0 ) return error_setInpError(ERR_NAME, tok(1))
    x(0) = k

    // --- check that outlet node or subcatch exists
    val m = project.findNode(tok(2))
    x(1) = m
    val m1 =project.findSubcatch(tok(2))
    x(2) = m1
//    if ( x(1) < 0.0 && x(2) < 0.0 )
//      return error_setInpError(ERR_NAME, tok(2))

    // --- read area, %imperv, width, slope, & curb length

    for ( i <- 3 to 7)//i = 3 i < 8 i++)
    {

         // printf("x 3 to  7 "+tok(3)+ " "+tok(4)+ " "+tok(5)+ " "+tok(6)+ " "+tok(7)+"\n")

      y(i-3) =tok(i).toDouble
//      if ( ! getDouble(tok(i), &x[i)) || x[i] < 0.0 )
//      return error_setInpError(ERR_NUMBER, tok(i))
    }

    // --- if snowmelt object named, check that it exists
    y(2) = -1
    if ( tok.size > 8 )
    {
      val snk =project.findTSnowmelt(tok(8)).filter(_!=null)
     // if ( k < 0 ) return error_setInpError(ERR_NAME, tok(8))
      //FIXME , not sur how to populate this value
     // y(2) = snk.wsnow
    }

    // --- assign input values to subcatch's properties
    val subCatch = new TSubcatch
    subCatch.id = tok(0)
    if(x(0).isDefined)
      subCatch.gage        = x(0).get.asInstanceOf[TGage]
    if(x(1).isDefined)
      subCatch.outNode     = x(1).get.asInstanceOf[TNode]
    if(x(2).isDefined)
      subCatch.outSubcatch = x(2).get.asInstanceOf[TSubcatch]
    subCatch.area        = y(0) / UCF(eLANDAREA)
    subCatch.fracImperv  = y(1) / 100.0
    subCatch.width       = y(2) / UCF(eLENGTH)
    subCatch.slope       = y(3) / 100.0
    subCatch.curbLength  = y(4)

    // --- create the snow pack object if it hasn't already been created
    if ( y(5) >= 0 )
    {
      //FIXME: Add later on
//      if ( !snow_createSnowpack(j, (int)x(8)) )
//      return error_setInpError(ERR_MEMORY, "")
    }
    project.project_addObject("SUBCATCH",tok(0),subCatch)

    subCatch
  }
  //=============================================================================

  def subcatch_readSubareaParams(tok: List[String]):TSubcatch =
    //
    //  Input:   tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads subcatchment's subarea parameters from a tokenized
    //           line of input data.
    //
    //  Data has format:
    //    Subcatch  Imperv_N  Perv_N  Imperv_S  Perv_S  PctZero  RouteTo (PctRouted)
    //
  {
  //  var   i, j, k, m:Int=0
    var x = new Array[Double](7)

    // --- check for enough tokens
    //if ( ntoks < 7 ) return error_setInpError(ERR_ITEMS, "")

    // --- check that named subcatch exists
    var j = project.findSubcatch(tok(0))
    //if ( j < 0 ) return error_setInpError(ERR_NAME, tok(0))

    // --- read in Mannings n, depression storage, & PctZero values
    //for (i = 0 i < 5 i++)
    for (i <- 0 to 4 )
    {
      x(i) = tok(i+1).toDouble
    }
    var m:Int =0
    // --- check for valid runoff routing keyword
    //m = findmatch(tok(6), RunoffRoutingWords)
    for( i <- RunoffRoutingWords.indices)
    {
      if( tok(6).equals(RunoffRoutingWords(i)))
        m =i
    }


    // --- get percent routed parameter if present (default is 100)
    x(5) = m
    x(6) = 1.0
    if ( tok.size >= 8 )
    {
//      if ( ! getDouble(tok(7), &x(6)) || x(6) < 0.0 || x(6) > 100.0 )
//      return error_setInpError(ERR_NUMBER, tok(7))
      x(6) /= 100.0
    }
    val subCatch = new TSubcatch
    val IMPERV0 =SubAreaType.IMPERV0.ordinal()
    val IMPERV1 =SubAreaType.IMPERV1.ordinal()
    val PERV =SubAreaType.PERV.ordinal()
    // --- assign input values to each type of subarea
    subCatch.subArea(IMPERV0) = new TSubarea()
    subCatch.subArea(IMPERV1) = new TSubarea()
    subCatch.subArea(PERV) = new TSubarea()
    subCatch.subArea(IMPERV0).N = x(0)
    subCatch.subArea(IMPERV1).N = x(0)
    subCatch.subArea(PERV).N    = x(1)

    subCatch.subArea(IMPERV0).dStore = 0.0
    subCatch.subArea(IMPERV1).dStore = x(2) / UCF(ConversionType.RAINDEPTH.ordinal())
    subCatch.subArea(PERV).dStore    = x(3) / UCF(ConversionType.RAINDEPTH.ordinal())

    subCatch.subArea(IMPERV0).fArea  = subCatch.fracImperv * x(4) / 100.0
    subCatch.subArea(IMPERV1).fArea  = subCatch.fracImperv * (1.0 - x(4) / 100.0)
    subCatch.subArea(PERV).fArea     = 1.0 - subCatch.fracImperv

    // --- assume that all runoff from each subarea goes to subcatch outlet
    //for (i = IMPERV0 i <= PERV i++)
    for (i <- IMPERV0 to PERV)
    {
      subCatch.subArea(i).routeTo = RunoffRoutingType.TO_OUTLET
      subCatch.subArea(i).fOutlet = 1.0
    }

    // --- modify routing if pervious runoff routed to impervious area
    //     (fOutlet is the fraction of runoff not routed)

    var  k = x(5).toInt
    if ( subCatch.fracImperv == 0.0      ||   subCatch.fracImperv == 1.0 )
      k = RunoffRoutingType.TO_OUTLET.ordinal()
    if ( k == RunoffRoutingType.TO_IMPERV.ordinal)// && subCatch.fracImperv )
    {
      subCatch.subArea(PERV).routeTo = RunoffRoutingType.values()(k)
      subCatch.subArea(PERV).fOutlet = 1.0 - x(6)
    }

    // --- modify routing if impervious runoff routed to pervious area
    if ( k == RunoffRoutingType.TO_PERV.ordinal() )
    {
      subCatch.subArea(IMPERV0).routeTo =RunoffRoutingType.values()(k)
      subCatch.subArea(IMPERV1).routeTo = RunoffRoutingType.values()(k)
      subCatch.subArea(IMPERV0).fOutlet = 1.0 - x(6)
      subCatch.subArea(IMPERV1).fOutlet = 1.0 - x(6)
    }
    subCatch
  }


}
