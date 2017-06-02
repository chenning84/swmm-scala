package swmm.util

import org.joda.time.{LocalTime, DateTime}
import org.joda.time.format.DateTimeFormat
import swmm.cmigrate.{SwmmConst, Keywords, SWMMText}
import swmm.configdata.jnodes.Errorh.ErrorType
import swmm.configdata.jnodes.InfilH.InfilType
import swmm.configdata.jnodes.types._
import swmm.configdata.jnodes.{SwmmEnum, GlobalContext}
import swmm.configdata.jnodes.SwmmEnum._
import swmm.cmigrate.Keywords._
import swmm.cmigrate.SWMMText._
import swmm.configdata.types.TGage
import swmm.engine.SInput
import swmm.runoff.infil.SInfil
import swmm.runoff.lid.SLid
import swmm.runoff.{SSnow, SGage, SClimate}
import scala.collection.mutable
import scala.collection.mutable.Map
/**
  * Created by Ning  on 11/12/15.
  *
  */
object Project {
  def getInstance: Project = {
    if (singletonInstance == null) {
      singletonInstance = new Project
    }
    singletonInstance
  }

  private var singletonInstance: Project = null

}

class Project extends GlobalContext {



  val NO_DATE = Keywords.NO_DATE
  val TRUE: Int = 1
  val FALSE: Int = 0
  val YES: Int = 1
  val NO: Int = 0
  val NO_FILE: Int = -1
  val eLENGTH = SwmmEnum.ConversionType.LENGTH.ordinal()
  val eLANDAREA = SwmmEnum.ConversionType.LANDAREA.ordinal()

  val ePOLLUT = SwmmEnum.ObjectType.POLLUT.ordinal()
  val eLINK = SwmmEnum.ObjectType.LINK.ordinal()
  val eSUBCATCH = SwmmEnum.ObjectType.SUBCATCH.ordinal()
  val eSNOWMELT = SwmmEnum.ObjectType.SNOWMELT.ordinal()
  val eAQUIFER = SwmmEnum.ObjectType.AQUIFER.ordinal()
  val eNODE = SwmmEnum.ObjectType.NODE.ordinal()
  val eCURVE = SwmmEnum.ObjectType.CURVE.ordinal()
  val eTSERIES = SwmmEnum.ObjectType.TSERIES.ordinal()
  val eGAGE = SwmmEnum.ObjectType.GAGE.ordinal()
  val eLid =SwmmEnum.ObjectType.LID.ordinal()
  val eRAINFALL = ConversionType.RAINFALL.ordinal()

  val eRAINDEPTH = ConversionType.RAINDEPTH.ordinal()
  // var ErrorCode = this.getErrorCode
  //=============================================================================
 // var sGage: SGage
  var sClimate: SClimate = null
  var hTable = mutable.Map[String,Set[String]]()
  var   Mobjects:List[TBase]=Nil   // Working number of objects of each type
  var   Mnodes:List[Int]=Nil   // Working number of node objects
  var   Mlinks:List[Int]=Nil    // Working number of link objects
  private def init ()={
    println("init function ")
  }

  init()

  def project_open(f1: String, f2: String, f3: String) =
  //
  //  Input:   f1 = pointer to name of input file
  //           f2 = pointer to name of report file
  //           f3 = pointer to name of binary output file
  //  Output:  none
  //  Purpose: opens a new SWMM project.
  //
  {
    initPointers()
    setDefaults()
    openFiles(f1, f2, f3)
  }

  //=============================================================================

  def project_readInput(): Int =
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: retrieves project data from input file.
  //
  {
    // --- create hash tables for fast retrieval of objects by ID names
    createHashTables()

    // --- count number of objects in input file and create them
    SInput.input_countObjects()
    createObjects()

    // --- read project data from input file
    SInput.input_readData()
    if (ErrorCode > 0) return ErrorCode

    // --- establish starting & ending date/time
    //FIXME
    //    StartDateTime = StartDate + StartTime
    //    EndDateTime   = EndDate + EndTime
    //    ReportStart   = ReportStartDate + ReportStartTime
    //    ReportStart   = Math.max(ReportStart, StartDateTime)

    // --- check for valid starting & ending date/times
    if (EndDateTime.compareTo(StartDateTime) <= 0) {
      Report.report_writeErrorMsg(ErrorType.ERR_START_DATE.ordinal(), "")
    }
    else if (EndDateTime.compareTo(ReportStart) <= 0) {
      Report.report_writeErrorMsg(ErrorType.ERR_REPORT_DATE.ordinal(), "")
    }
    else {
      // --- compute total duration of simulation in milliseconds
      TotalDuration = 0
      //FIXME
      //      Math.floor(datetime_timeDiff(EndDateTime, StartDateTime)
      //                              * 1000.0)

      // --- reporting step must be <= total duration
      if (ReportStep > TotalDuration / 1000.0) {
        ReportStep = (TotalDuration / 1000.0).toInt
      }
      if (ReportStep < RouteStep) {
        Report.report_writeErrorMsg(ErrorType.ERR_REPORT_STEP.ordinal(), "")
      }
    }
    1
  }

  //=============================================================================

  def project_validate()
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: checks validity of project data.
  //
  {
    var i, j, err = 0

    // --- validate Curves and TimeSeries
    for (i <- 0 to Nobjects(eCURVE)) {
      err = STable.table_validate(Curve(i))
      if (err != 0) Report.report_writeErrorMsg(ErrorType.ERR_CURVE_SEQUENCE.ordinal(), Curve(i).id)
    }
    for (i <- 0 to Nobjects(eTSERIES) - 1) {
      err = STable.table_validate(Tseries(i))

    }

    // --- validate hydrology objects
    //     (NOTE: order is important !!!!)
    SClimate.climate_validate()
    SInfil.lid_validate()
    if (Nobjects(eSNOWMELT) == 0) IgnoreSnowmelt = true
    if (Nobjects(eAQUIFER) == 0) IgnoreGwater = true
    for (i <- 0 to Nobjects(eGAGE) - 1) SGage.gage_validate(i)
    //FIXME
    //    for ( i <- 0 to  Nobjects(eAQUIFER) -1 )  gwater_validateAquifer(i)
    //    for ( i <- 0 to  Nobjects(eSUBCATCH) -1 )  subcatch_validate(i)
    for (i <- 0 to Nobjects(eSNOWMELT) - 1) SSnow.snow_validateSnowmelt(i)

    // --- compute geometry tables for each shape curve
    //FIXME: what's the real j value
    j = 0
    for (i <- 0 to Nobjects(eCURVE) - 1) {
      if (Curve(i).curveType == CurveType.SHAPE_CURVE.ordinal) {
        Curve(i).refersTo = EvapType.values()(j)
        Shape(j).curve = i
        if (!SShape.shape_validate(Shape(j), Curve(i)))
          Report.report_writeErrorMsg(ErrorType.ERR_CURVE_SEQUENCE.ordinal(), Curve(i).id)
          j = j +1
      }
    }

    // --- validate links before nodenames, since the latter can
    //     result in adjustment of node depths
    for (i <- 0 to Nobjects(eNODE) - 1) Node(i).oldDepth = Node(i).fullDepth
    //FIXME
    //    for ( i <- 0 to  Nobjects(eLINK) -1 ) link_validate(i)
    //    for ( i <- 0 to  Nobjects(eNODE) -1 ) node_validate(i)

    // --- adjust time steps if necessary
    if (DryStep < WetStep) {
      Report.report_writeWarningMsg(WARN06, "")
      DryStep = WetStep
    }
    if (RouteStep > WetStep) {
      Report.report_writeWarningMsg(WARN07, "")
      RouteStep = WetStep
    }

    // --- adjust individual reporting flags to match global reporting flag
    if (RptFlags.subcatchments == NoneAllType.ALL.ordinal())
      for (i <- 0 to Nobjects(eSUBCATCH) - 1) Subcatch(i).rptFlag = TRUE
    if (RptFlags.nodes == NoneAllType.ALL.ordinal())
      for (i <- 0 to Nobjects(eNODE) - 1) Node(i).rptFlag = TRUE
    if (RptFlags.links == NoneAllType.ALL.ordinal())
      for (i <- 0 to Nobjects(eLINK) - 1) Link(i).rptFlag = TRUE

    // --- validate dynamic wave options
    if (RouteModel == RouteModelType.DW) //FIXME dynwave_validate()                                //(5.1.008)


      if (Nobjects(eLINK) < 4 * NumThreads) NumThreads = 1 //(5.1.008)

  }

  //=============================================================================

  def project_close() =
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: closes a SWMM project.
  //
  {
    deleteObjects()
    deleteHashTables()
  }

  //=============================================================================

  def project_init() =
  //
  //  Input:   none
  //  Output:  returns an error code
  //  Purpose: initializes the internal state of all objects.
  //
  {
    val j = 0
    SClimate.climate_initState()
    SLid.lid_initState()
    for (j <- 0 to Nobjects(eTSERIES) - 1) STable.table_tseriesInit(Tseries(j))
    for (j <- 0 to Nobjects(eGAGE) - 1) SGage.gage_initState(j)
    //FIXME
    //    for (j <- 0 to  Nobjects(eSUBCATCH)-1) subcatch_initState(j)
    //    for (j <- 0 to  Nobjects(eNODE)-1)     node_initState(j)
    //    for (j <- 0 to  Nobjects(eLINK)-1)     link_initState(j)
    ErrorCode
  }

  //=============================================================================
  def project_addObject(sType: String, id: String, n: TBase) =
  //
  //  Input:   type = object type
  //           id   = object ID string
  //           n    = object index
  //  Output:  returns 0 if object already added, 1 if not, -1 if hashing fails
  //  Purpose: adds an object ID to a hash table
  //
  {

     DataCache.getInstance.insert(sType,id,n)

  }

  //=============================================================================

  def project_findObject(sType: String, id: String): Option[TBase] =
  //
  //  Input:   type = object type
  //           id   = object ID
  //  Output:  returns index of object with given ID, or -1 if ID not found
  //  Purpose: uses hash table to find index of an object with a given ID.
  //
  {
     DataCache.getInstance.findObject(sType,id)
  }
  def findTPattern(id:String) :Option[TPattern]=
  {
    project_findObject("TIMEPATTERN",id).map(_.asInstanceOf[TPattern])
  }
  def findTSeries(id:String) :Option[TTable]=
  {
    project_findObject("TSERIES",id).map(_.asInstanceOf[TTable])
  }
  def findTSnowmelt(id:String) :Option[TSnowmelt]=
  {
    val obj =    project_findObject("SNOWMELT",id)
      if(obj.exists(_!=null))
        obj.map(_.asInstanceOf[TSnowmelt])
       else
        None
  }

  def findSubcatch(id:String) :Option[TSubcatch]=
  {
     project_findObject("SUBCATCH",id).map(_.asInstanceOf[TSubcatch])
  }
  def findGage(id:String) :Option[TGage]=
  {
    project_findObject("GAGE",id).map(_.asInstanceOf[TGage])
  }
  def findNode(id:String) :Option[TTable]=
  {
    project_findObject("NODE",id).map(_.asInstanceOf[TTable])
  }


  //=============================================================================

  def project_findID(stype: String, id: String): Option[String ]=
  //
  //  Input:   type = object type
  //           id   = ID name being sought
  //  Output:  returns pointer to location where object's ID string is stored
  //  Purpose: uses hash table to find address of given string entry.
  //
  {
      val dInstance = DataCache.getInstance

    dInstance.findId(stype,id)


  }

  //=============================================================================

  def project_createMatrix(nrow: Int, incols: Int): Array[Array[Double]] =
  //
  //  Input:   nrows = number of rows (0-based)
  //           ncols = number of columns (0-based)
  //  Output:  returns a pointer to a matrix
  //  Purpose: allocates memory for a matrix of doubles.
  //
  {
    var i, j = 0
    var a: Array[Array[Double]] = null //new Array[Array[Double]]()


    // FIXME:
    //    for ( i  <- 0 to nrows )
    //        a(i) = a(i-1) + ncols
    //
    //    for ( i <- 0 to nrows-1)
    //    {
    //        for ( j <- 0 to ncols01) a(i)(j) = 0.0
    //    }

    // --- return pointer to array of pointers to rows
    a
  }

  //=============================================================================

  def project_freeMatrix(a: Array[Array[Double]]) =
  //
  //  Input:   a = matrix of floats
  //  Output:  none
  //  Purpose: frees memory allocated for a matrix of doubles.
  //
  {
    //    if ( a != null )
    //    {
    //        if ( a(0) != null ) free( a[0] )
    //        free( a )
    //    }
  }

  //=============================================================================
  def readOptionSection(dat: List[List[String]]): Boolean = {
    dat.foreach(readOption(_))
    true

  }

  def readOption(kayValue: List[String]): Int =
  //
  //  Input:   s1 = option keyword
  //           s2 = string representation of option's value
  //  Output:  returns error code
  //  Purpose: reads a project option from a pair of string tokens.
  //
  //  NOTE:    all project options have default values assigned in setDefaults().
  {
    val s1 = kayValue.head
    val s2 = kayValue.tail.head
    val formatter1 = DateTimeFormat.forPattern("dd/MM/yyyy")
    val formatter2 = DateTimeFormat.forPattern("HH:mm:ss")
    val formatter3 = DateTimeFormat.forPattern("MM/dd/yyyy")
    var h, s = 0
    var m = false

    //   var     strDate:String =""
    var aDate: DateTime = null

    // --- determine which option is being read
    val k = OptionWords.contains(s1)
    if (!k) return ErrorUtil.error_setInpError(ErrorType.ERR_KEYWORD, s1)
    s1 match {
      // --- choice of flow units
      case SWMMText.w_FLOW_UNITS =>
        val uType = FlowUnitsType.valueOf(s2)

        FlowUnits = uType
        if (FlowUnits.ordinal() <= FlowUnitsType.MGD.ordinal())
          UnitSystem = UnitsType.US
        else
          UnitSystem = UnitsType.SI

      // --- choice of infiltration modeling method
      case SWMMText.w_INFIL_MODEL =>
        InfilModel = InfilType.valueOf(s2)


      // --- choice of flow routing method
      case SWMMText.w_ROUTE_MODEL =>

        s2 match {
          case `w_NONE` =>
            RouteModel = RouteModelType.NO_ROUTING
            IgnoreRouting = true
          case `w_STEADY` =>
          case `w_NF` =>
            RouteModel = RouteModelType.SF
          case `w_KINWAVE` =>
          case `w_KW` =>
          //TODO : Need to futher check
          case `w_XKINWAVE` =>
          case `w_EKW` =>
            RouteModel = RouteModelType.KW
          case `w_DYNWAVE` =>
          case `w_DW` =>
            RouteModel = RouteModelType.DW
        }
      // --- simulation start date
      case SWMMText.w_START_DATE =>
        StartDate = formatter1.parseDateTime(s2)

      // --- simulation start time of day
      case SWMMText.w_START_TIME =>
        StartTime = formatter2.parseLocalTime(s2)
      // --- simulation ending date
      case SWMMText.w_END_DATE =>
        EndDate = formatter1.parseDateTime(s2)
      // --- simulation ending time of day
      case SWMMText.w_END_TIME =>
        EndTime = formatter2.parseLocalTime(s2)
      // --- reporting start date
      case SWMMText.w_REPORT_START_DATE =>
        ReportStartDate = formatter1.parseDateTime(s2)

      // --- reporting start time of day
      case SWMMText.w_REPORT_START_TIME =>
        ReportStartTime = formatter2.parseLocalTime(s2)


      // --- day of year when street sweeping begins or when it ends
      //     (year is arbitrarily set to 1947 so that the dayOfYear
      //      function can be applied)
      case SWMMText.w_SWEEP_START =>
      case SWMMText.w_SWEEP_END =>

        aDate = formatter3.parseDateTime(s2 + "/1947")
        val y = aDate.getYear
        if (s1.equals(w_SWEEP_START))
          SweepStart = y
        else
          SweepEnd = y
      // --- number of antecedent dry days
      case SWMMText.w_START_DRY_DAYS =>
        StartDryDays = s2.toDouble
      // --- runoff or reporting time steps
      //     (input is in hrs:min=>sec format, time step saved as seconds)
      case SWMMText.w_WET_STEP =>
      case SWMMText.w_DRY_STEP =>
      case SWMMText.w_REPORT_STEP =>
        val aTime = formatter2.parseLocalTime(s2)
        //        datetime_decodeTime(aTime, h, m, s)
        //        h += 24* aTime.
        val s = aTime.getSecondOfMinute + 60 * aTime.getMinuteOfHour + 3600 * aTime.getHourOfDay
        s1 match {
          case SWMMText.w_WET_STEP =>
            WetStep = s
          case SWMMText.w_DRY_STEP =>
            DryStep = s
          case SWMMText.w_REPORT_STEP =>
            ReportStep = s
        }


      // --- type of damping applied to inertial terms of dynamic wave routing
      case SWMMText.w_INERT_DAMPING =>

        if (InertDampingWords(s2))
          InertDamping = InerDampingType.valueOf(s2)


      // --- Yes/No options (NO = 0, YES = 1)
      case SWMMText.w_ALLOW_PONDING =>
      case SWMMText.w_SLOPE_WEIGHTING =>
      case SWMMText.w_SKIP_STEADY_STATE =>
      case SWMMText.w_IGNORE_RAINFALL =>
      case SWMMText.w_IGNORE_SNOWMELT =>
      case SWMMText.w_IGNORE_GWATER =>
      case SWMMText.w_IGNORE_ROUTING =>
      case SWMMText.w_IGNORE_QUALITY =>
      case SWMMText.w_IGNORE_RDII => //(5.1.004)
        m = NoYesWords(s2)
        if (!m) return ErrorUtil.error_setInpError(ErrorType.ERR_KEYWORD, s2)
        s1 match {
          case SWMMText.w_ALLOW_PONDING => AllowPonding = m
          case SWMMText.w_SLOPE_WEIGHTING => SlopeWeighting = if (m) 1 else 0
          case SWMMText.w_SKIP_STEADY_STATE => SkipSteadyState = if (m) 1 else 0
          case SWMMText.w_IGNORE_RAINFALL => IgnoreRainfall = m
          case SWMMText.w_IGNORE_SNOWMELT => IgnoreSnowmelt = m
          case SWMMText.w_IGNORE_GWATER => IgnoreGwater = m
          case SWMMText.w_IGNORE_ROUTING => IgnoreRouting = m
          case SWMMText.w_IGNORE_QUALITY => IgnoreQuality = m
          case SWMMText.w_IGNORE_RDII => IgnoreRDII = m //(5.1.004)
        }


      case SWMMText.w_NORMAL_FLOW_LTD =>

        if (NormalFlowWords(s2))
          NormalFlowLtd = NormalFlowType.valueOf(s2)


      case SWMMText.w_FORCE_MAIN_EQN =>

        if (ForceMainEqnWords(s2))
          ForceMainEqn = ForceMainType.valueOf(s2)

      case SWMMText.w_LINK_OFFSETS =>

        if (LinkOffsetWords(s2))

          LinkOffsets = OffsetType.valueOf(s2.replace("-", "_"))


      // --- compatibility option for selecting solution method for
      //     dynamic wave flow routing (NOT CURRENTLY USED)
      case SWMMText.w_COMPATIBILITY =>
        if ("3".equals(s2)) Compatibility = CompatibilityType.SWMM3.ordinal()
        else if ("4".equals(s2)) Compatibility = CompatibilityType.SWMM4.ordinal()
        else if ("5".equals(s2)) Compatibility = CompatibilityType.SWMM5.ordinal()
        else return ErrorUtil.error_setInpError(ErrorType.ERR_KEYWORD, s2)


      // --- routing or lengthening time step (in decimal seconds)
      //     (lengthening time step is used in Courant stability formula
      //     to artificially lengthen conduits for dynamic wave flow routing
      //     (a value of 0 means that no lengthening is used))
      case SWMMText.w_ROUTE_STEP =>
      case SWMMText.w_LENGTHENING_STEP =>
        // Two types of format 0.5 or 00:12:25
        var tStep: Double = .0
        if(s2.indexOf(":")>=0)
          {
            val aTime =formatter2.parseLocalTime(s2)
            tStep = aTime.getSecondOfMinute + 60 * aTime.getMinuteOfHour + 3600 * aTime.getHourOfDay
          }
        else
        {
          tStep = s2.toDouble
        }

        if (s1.equals(SWMMText.w_ROUTE_STEP) ) {
          if (tStep <= 0.0) return ErrorUtil.error_setInpError(ErrorType.ERR_NUMBER, s2)
          RouteStep = tStep
        }
        else LengtheningStep = Math.max(0.0, tStep)


      ////  Following code section added to release 5.1.008.  ////                   //(5.1.008)

      // --- minimum variable time step for dynamic wave routing
      case SWMMText.w_MIN_ROUTE_STEP =>
        MinRouteStep = s2.toDouble
      //        if ( !getDouble(s2, MinRouteStep) || MinRouteStep < 0.0 )
      //            return ErrorUtil.error_setInpError(ErrorType.ERR_NUMBER.ordinal(), s2)


      case SWMMText.w_NUM_THREADS =>
        val im = s2.toInt
        if (!m)
          return ErrorUtil.error_setInpError(ErrorType.ERR_NUMBER, s2)
        NumThreads = im

      ////

      // --- safety factor applied to variable time step estimates under
      //     dynamic wave flow routing (value of 0 indicates that variable
      //     time step option not used)
      case SWMMText.w_VARIABLE_STEP =>
        CourantFactor = s2.toDouble

        if (CourantFactor < 0.0 || CourantFactor > 2.0)
          return ErrorUtil.error_setInpError(ErrorType.ERR_NUMBER, s2)


      // --- minimum surface area (ft2 or sq. meters) associated with nodes
      //     under dynamic wave flow routing 
      case SWMMText.w_MIN_SURFAREA =>
        MinSurfArea = s2.toDouble


      // --- minimum conduit slope (%)
      case SWMMText.w_MIN_SLOPE =>
        MinSlope = s2.toDouble

        if (MinSlope < 0.0 || MinSlope >= 100)
          return ErrorUtil.error_setInpError(ErrorType.ERR_NUMBER, s2)
        MinSlope /= 100.0


      // --- maximum trials / time step for dynamic wave routing
      case SWMMText.w_MAX_TRIALS =>

        MaxTrials = s2.toInt


      // --- head convergence tolerance for dynamic wave routing
      case SWMMText.w_HEAD_TOL =>

        HeadTol = s2.toDouble
      // --- steady state tolerance on system inflow - outflow
      case SWMMText.w_SYS_FLOW_TOL =>

        SysFlowTol = s2.toDouble / 100.0
      // --- steady state tolerance on nodal lateral inflow
      case SWMMText.w_LAT_FLOW_TOL =>

        LatFlowTol = s2.toDouble / 100.0
      case SWMMText.w_TEMPDIR => // Temporary Directory
        TempDir = s2


    }
    0
  }

  //=============================================================================

  def initPointers()
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: assigns null to all dynamic arrays for a new project.
  //
  {
    Gage = null
    Subcatch = null
    Node = null
    Outfall = null
    Divider = null
    Storage = null
    Link = null
    Conduit = null
    Pump = null
    Orifice = null
    Weir = null
    Outlet = null
    Pollut = null
    Landuse = null
    Pattern = null
    Curve = null
    Tseries = null
    Transect = null
    Shape = null
    Aquifer = null
    UnitHyd = null
    Snowmelt = null
    //MemPoolAllocated = FALSE
  }

  //=============================================================================

  def setDefaults()
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: assigns default values to project variables.
  //
  {
    var i, j = 0

    // Project title & temp. file path
    for (i <- 0 to SwmmConst.MAXTITLE - 1) Title(i) = ""
    TempDir = ""

    // Interface files
    Frain.mode = FileUsageType.SCRATCH_FILE.ordinal() // Use scratch rainfall file
    Fclimate.mode = NO_FILE
    Frunoff.mode = NO_FILE
    Frdii.mode = NO_FILE
    Fhotstart1.mode = NO_FILE
    Fhotstart2.mode = NO_FILE
    Finflows.mode = NO_FILE
    Foutflows.mode = NO_FILE
    Frain.file = null
    Fclimate.file = null
    Frunoff.file = null
    Frdii.file = null
    Fhotstart1.file = null
    Fhotstart2.file = null
    Finflows.file = null
    Foutflows.file = null
    Fout.file = null
    Fout.mode = NO_FILE

    // Analysis options
    UnitSystem = UnitsType.US // US unit system
    FlowUnits = FlowUnitsType.CFS // CFS flow units
    InfilModel = InfilType.HORTON // Horton infiltration method
    RouteModel = RouteModelType.KW // Kin. wave flow routing method
    AllowPonding = false // No ponding at nodes
    InertDamping = InerDampingType.PARTIAL // Partial inertial damping
    NormalFlowLtd = NormalFlowType.BOTH // Default normal flow limitation
    ForceMainEqn = ForceMainType.H_W // Hazen-Williams eqn. for force mains
    LinkOffsets = OffsetType.DEPTH // Use depth for link offsets
    LengtheningStep = 0 // No lengthening of conduits
    CourantFactor = 0.0 // No variable time step
    MinSurfArea = 0.0 // Force use of default min. surface area
    SkipSteadyState = FALSE // Do flow routing in steady state periods
    IgnoreRainfall = false // Analyze rainfall/runoff
    IgnoreRDII = false // Analyze RDII                         //(5.1.004)
    IgnoreSnowmelt = false // Analyze snowmelt
    IgnoreGwater = false // Analyze groundwater
    IgnoreRouting = false // Analyze flow routing
    IgnoreQuality = false // Analyze water quality
    WetStep = 300 // Runoff wet time step (secs)
    DryStep = 3600 // Runoff dry time step (secs)
    RouteStep = 300.0 // Routing time step (secs)
    MinRouteStep = 0.5 // Minimum variable time step (sec)     //(5.1.008)
    ReportStep = 900 // Reporting time step (secs)
    StartDryDays = 0.0 // Antecedent dry days
    MaxTrials = 0 // Force use of default max. trials
    HeadTol = 0.0 // Force use of default head tolerance
    SysFlowTol = 0.05 // System flow tolerance for steady state
    LatFlowTol = 0.05 // Lateral flow tolerance for steady state
    NumThreads = 0 // Number of parallel threads to use

    // Deprecated options
    SlopeWeighting = TRUE // Use slope weighting
    Compatibility = CompatibilityType.SWMM4.ordinal() // Use SWMM 4 up/dn weighting method

    // Starting & ending date/time

    StartDate = new DateTime(2004, 1, 1, 0, 0, 0, 0)
    StartTime = StartDate.toLocalTime
    StartDateTime = StartDate
    EndDate = StartDate
    EndTime = StartDate.toLocalTime
    ReportStartDate = StartDate
    ReportStartTime = StartDate.toLocalTime
    SweepStart = 1
    SweepEnd = 365

    // Reporting options
    RptFlags.input = FALSE
    RptFlags.continuity = TRUE
    RptFlags.flowStats = TRUE
    RptFlags.controls = FALSE
    RptFlags.subcatchments = FALSE
    RptFlags.nodes = FALSE
    RptFlags.links = FALSE
    RptFlags.nodeStats = FALSE

    // Temperature data
    Temp.dataSource = TempType.NO_TEMP.ordinal()
    Temp.tSeries = -1
    Temp.ta = 70.0
    Temp.elev = 0.0
    Temp.anglat = 40.0
    Temp.dtlong = 0.0
    Temp.tmax = SwmmConst.MISSING

    // Wind speed data
    Wind.sType = WindType.MONTHLY_WIND.ordinal()
    for (i <- 0 to 11) Wind.aws(i) = 0.0

    // Snowmelt parameters
    Snow.snotmp = 34.0
    Snow.tipm = 0.5
    Snow.rnm = 0.6

    // Snow areal depletion curves for pervious and impervious surfaces
    for (i <- 0 to 1) {
      for (j <- 0 to 9) Snow.adc(i)(j) = 1.0
    }

    // Evaporation rates
    Evap.sType = EvapType.CONSTANT_EVAP
    for (i <- 1 to 11) {
      Evap.monthlyEvap(i) = 0.0
      Evap.panCoeff(i) = 1.0
    }
   // Evap.recoveryPattern = -1
    Evap.recoveryFactor = 1.0
    Evap.tSeries = -1
    Evap.dryOnly = false

    ////  Following code segment added to release 5.1.007.  ////                   //(5.1.007)
    ////
    // Climate adjustments
    for (i <- 0 to 11) {
      Adjust.temp(i) = 0.0 // additive adjustments
      Adjust.evap(i) = 0.0 // additive adjustments
      Adjust.rain(i) = 1.0 // multiplicative adjustments
      Adjust.hydcon(i) = 1.0 // hyd. conductivity adjustments                //(5.1.008)
    }
    Adjust.rainFactor = 1.0
    Adjust.hydconFactor = 1.0 //(5.1.008)
    ////
  }

  //=============================================================================

  def openFiles(f1: String, f2: String, f3: String) =
  //
  //  Input:   f1 = name of input file
  //           f2 = name of report file
  //           f3 = name of binary output file
  //  Output:  none
  //  Purpose: opens a project's input and report files.
  //
  {
    // --- initialize file pointers to null
    Finp.file = f1
    Frpt.file = f2
    Fout.file = f3



    // --- check that file names are not identical
    //FIXME:
    //    if (f1.equals(f2) || f1.equals( f3) || f2.equals( f3))
    //    {
    //        writecon(FMT11)
    //        ErrorCode = ERR_FILE_NAME
    //        return
    //    }
    //
    //    // --- open input and report files
    //    if ((Finp.file = fopen(f1,"rt")) == null)
    //    {
    //        writecon(FMT12)
    //        writecon(f1)
    //        ErrorCode = ERR_INP_FILE
    //        return
    //    }
    //    if ((Frpt.file = fopen(f2,"wt")) == null)
    //    {
    //       writecon(FMT13)
    //       ErrorCode = ERR_RPT_FILE
    //       return
    //    }
  }

  //=============================================================================

  def createObjects()
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: allocates memory for project's objects.
  //
  //  NOTE: number of each type of object has already been determined in
   //       project_readInput().
  //
  {
    var j, k = 0

        // --- allocate memory for each category of object
//
//        Gage     = new TGage *)     calloc(Nobjects(eGAGE],     sizeof(TGage))
//        Subcatch = (TSubcatch *) calloc(Nobjects(eSUBCATCH], sizeof(TSubcatch))
//        Node     = (TNode *)     calloc(Nobjects(eNODE],     sizeof(TNode))
//        Outfall  = (TOutfall *)  calloc(Nnodes[OUTFALL],    sizeof(TOutfall))
//        Divider  = (TDivider *)  calloc(Nnodes[DIVIDER],    sizeof(TDivider))
//        Storage  = (TStorage *)  calloc(Nnodes[STORAGE],    sizeof(TStorage))
//        Link     = (TLink *)     calloc(Nobjects(eLINK],     sizeof(TLink))
//        Conduit  = (TConduit *)  calloc(Nlinks[CONDUIT],    sizeof(TConduit))
//        Pump     = (TPump *)     calloc(Nlinks[PUMP],       sizeof(TPump))
//        Orifice  = (TOrifice *)  calloc(Nlinks[ORIFICE],    sizeof(TOrifice))
//        Weir     = (TWeir *)     calloc(Nlinks[WEIR],       sizeof(TWeir))
//        Outlet   = (TOutlet *)   calloc(Nlinks[OUTLET],     sizeof(TOutlet))
//        Pollut   = (TPollut *)   calloc(Nobjects(ePOLLUT],   sizeof(TPollut))
//        Landuse  = (TLanduse *)  calloc(Nobjects(eLANDUSE],  sizeof(TLanduse))
//        Pattern  = (TPattern *)  calloc(Nobjects(eTIMEPATTERN],  sizeof(TPattern))
//        Curve    = (TTable *)    calloc(Nobjects(eCURVE],    sizeof(TTable))
//        Tseries  = (TTable *)    calloc(Nobjects(eTSERIES],  sizeof(TTable))
//        Aquifer  = (TAquifer *)  calloc(Nobjects(eAQUIFER],  sizeof(TAquifer))
//        UnitHyd  = (TUnitHyd *)  calloc(Nobjects(eUNITHYD],  sizeof(TUnitHyd))
//        Snowmelt = (TSnowmelt *) calloc(Nobjects(eSNOWMELT], sizeof(TSnowmelt))
//        Shape    = (TShape *)    calloc(Nobjects(eSHAPE],    sizeof(TShape))
//
//     --- create LID objects
        SLid.lid_create(Nobjects(eLid), Nobjects(eSUBCATCH))

//        // --- create control rules
//        ErrorCode = controls_create(Nobjects(eCONTROL))
//        if ( ErrorCode ) return
//
//        // --- create cross section transects
//        ErrorCode = transect_create(Nobjects(eTRANSECT])
//        if ( ErrorCode ) return
//
//        // --- allocate memory for infiltration data
//        SInfil.infil_create(Nobjects(eSUBCATCH], InfilModel)
//
//        // --- allocate memory for water quality state variables
//        for (j = 0 j < Nobjects(eSUBCATCH] j++)
//        {
//            Subcatch(j).initBuildup =
//                                  (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//            Subcatch(j).oldQual = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//            Subcatch(j).newQual = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//            Subcatch(j).pondedQual = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//            Subcatch(j).totalLoad  = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//        }
//        for (j = 0 j < Nobjects(eNODE] j++)
//        {
//            Node(j).oldQual = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//            Node(j).newQual = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//            Node(j).extInflow = null
//            Node(j).dwfInflow = null
//            Node(j).rdiiInflow = null
//            Node(j).treatment = null
//        }
//        for (j = 0 j < Nobjects(eLINK] j++)
//        {
//            Link(j).oldQual = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//            Link(j).newQual = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//    	    Link(j).totalLoad = (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//        }
//
//        // --- allocate memory for land use buildup/washoff functions
//        for (j = 0 j < Nobjects(eLANDUSE] j++)
//        {
//            Landuse(j).buildupFunc =
//                (TBuildup *) calloc(Nobjects(ePOLLUT], sizeof(TBuildup))
//            Landuse(j).washoffFunc =
//                (TWashoff *) calloc(Nobjects(ePOLLUT], sizeof(TWashoff))
//        }
//
//        // --- allocate memory for subcatchment landuse factors
//        for (j = 0 j < Nobjects(eSUBCATCH] j++)
//        {
//            Subcatch(j).landFactor =
//                (TLandFactor *) calloc(Nobjects(eLANDUSE], sizeof(TLandFactor))
//            for (k = 0 k < Nobjects(eLANDUSE] k++)
//            {
//                Subcatch(j).landFactor[k].buildup =
//                    (double *) calloc(Nobjects(ePOLLUT], sizeof(double))
//            }
//        }
//
//        // --- initialize buildup & washoff functions
//        for (j = 0 j < Nobjects(eLANDUSE] j++)
//        {
//            for (k = 0 k < Nobjects(ePOLLUT] k++)
//            {
//                Landuse(j).buildupFunc[k].funcType = NO_BUILDUP
//                Landuse(j).buildupFunc[k].normalizer = PER_AREA
//                Landuse(j).washoffFunc[k].funcType = NO_WASHOFF
//            }
//        }
//
//        // --- initialize rain gage properties
//        for (j = 0 j < Nobjects(eGAGE] j++)
//        {
//            Gage(j).tSeries = -1
//            strcpy(Gage(j).fname, "")
//        }
//
//        // --- initialize subcatchment properties
//        for (j = 0 j < Nobjects(eSUBCATCH] j++)
//        {
//            Subcatch(j).outSubcatch = -1
//            Subcatch(j).outNode     = -1
//            Subcatch(j).infil       = -1
//            Subcatch(j).groundwater = null
//    	    Subcatch(j).gwLatFlowExpr = null                                      //(5.1.007)
//            Subcatch(j).gwDeepFlowExpr = null                                     //(5.1.007)
//            Subcatch(j).snowpack    = null
//            Subcatch(j).lidArea     = 0.0
//            for (k = 0 k < Nobjects(ePOLLUT] k++)
//            {
//                Subcatch(j).initBuildup[k] = 0.0
//            }
//        }
//
//        // --- initialize RDII unit hydrograph properties
//        for ( j = 0 j < Nobjects(eUNITHYD] j++ ) rdii_initUnitHyd(j)
//
//        // --- initialize snowmelt properties
//        for ( j = 0 j < Nobjects(eSNOWMELT] j++ ) snow_initSnowmelt(j)
//
//        // --- initialize storage node exfiltration                                //(5.1.007)
//        for (j = 0 j < Nnodes[STORAGE] j++) Storage(j).exfil = null             //(5.1.007)
//
//        // --- initialize link properties
//        for (j = 0 j < Nobjects(eLINK] j++)
//        {
//            Link(j).xsect.type   = -1
//            Link(j).cLossInlet   = 0.0
//            Link(j).cLossOutlet  = 0.0
//            Link(j).cLossAvg     = 0.0
//            Link(j).hasFlapGate  = FALSE
//        }
//        for (j = 0 j < Nlinks[PUMP] j++) Pump(j).pumpCurve  = -1
//
//        // --- initialize reporting flags
//        for (j = 0 j < Nobjects(eSUBCATCH] j++) Subcatch(j).rptFlag = FALSE
//        for (j = 0 j < Nobjects(eNODE] j++) Node(j).rptFlag = FALSE
//        for (j = 0 j < Nobjects(eLINK] j++) Link(j).rptFlag = FALSE
//
//        //  --- initialize curves, time series, and time patterns
//        for (j = 0 j < Nobjects(eCURVE] j++)   table_init(Curve(j))
//        for (j = 0 j < Nobjects(eTSERIES] j++) table_init(Tseries(j))
//        for (j = 0 j < Nobjects(eTIMEPATTERN] j++) inflow_initDwfPattern(j)
  }

  //=============================================================================

  def deleteObjects() =
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: frees memory allocated for a project's objects.
  //
  //  NOTE: care is taken to first free objects that are properties of another
  //        object before the latter is freed (e.g., we must free a
  //        subcatchment's land use factors before freeing the subcatchment).
  //
  {

    //    // --- now free each major category of object
    //    FREE(Gage)
    //    FREE(Subcatch)
    //    FREE(Node)
    //    FREE(Outfall)
    //    FREE(Divider)
    //    FREE(Storage)
    //    FREE(Link)
    //    FREE(Conduit)
    //    FREE(Pump)
    //    FREE(Orifice)
    //    FREE(Weir)
    //    FREE(Outlet)
    //    FREE(Pollut)
    //    FREE(Landuse)
    //    FREE(Pattern)
    //    FREE(Curve)
    //    FREE(Tseries)
    //    FREE(Aquifer)
    //    FREE(UnitHyd)
    //    FREE(Snowmelt)
    //    FREE(Shape)
  }

  //=============================================================================

  def createHashTables()
  //
  //  Input:   none
  //  Output:  returns error code
  //  Purpose: allocates memory for object ID hash tables
  //
  {

    for (j <- 0 to ObjectType.MAX_OBJ_TYPES.ordinal() - 1) {
      //FIXME
      //         Htable(j) = HTcreate()
      //         if ( Htable(j) == null ) Report.report_writeErrorMsg(ErrorType.ERR_MEMORY.ordinal(), "")
    }

    // --- initialize memory pool used to store object ID's

  }

  //=============================================================================

  def deleteHashTables()
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: frees memory allocated for object ID hash tables
  //
  {
    //    int j
    //    for (j = 0 j < MAX_OBJ_TYPES j++)
    //    {
    //        if ( Htable(j) != null ) HTfree(Htable(j))
    //    }
    //
    //    // --- free object ID memory pool
    //    if ( MemPoolAllocated ) AllocFreePool()
  }

  //=============================================================================


}
