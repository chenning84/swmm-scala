package swmm.util

import org.joda.time.DateTime
import swmm.cmigrate.SWMMText._
import swmm.cmigrate.SwmmConst
import swmm.configdata.jnodes.Errorh.ErrorType

import swmm.configdata.jnodes.SwmmEnum._
import swmm.configdata.jnodes.{SwmmEnum, GlobalContext}
import swmm.configdata.jnodes.types._
import swmm.configdata.jnodes.Errorh.ErrorType._

import swmm.util.FunctionUtil.UCF
import swmm.cmigrate.Keywords._
import swmm.util.FPrintFUtil._

/**
  * Created by ning on 11/11/15.
  * 
  */
object Report {
  val TRUE:Int =1
  val FALSE:Int =0
  val YES:Int =1
  val NO:Int =0
  //=============================================================================
  var gInstance =Project.getInstance
  var project = new Project
  var Frpt = gInstance.Frpt
  var Gage = gInstance.Gage
  var Node = gInstance.Node
  var Link = gInstance.Link
  var Subcatch = gInstance.Subcatch
  val gIgnoreSnowmelt =gInstance.IgnoreSnowmelt
  val gIgnoreRainfall =gInstance.IgnoreRainfall
  val gIgnoreRouting =gInstance.IgnoreRouting
  val gIgnoreGwater =gInstance.IgnoreGwater
  val gIgnoreQuality =gInstance.IgnoreQuality

  var RptFlags =gInstance.RptFlags
  var Frunoff =gInstance.Frunoff
  var Pollut =gInstance.Pollut
  val Snowmelt =gInstance.Snowmelt
  val FlowUnits =gInstance.FlowUnits
  val UnitSystem=gInstance.UnitSystem
  var Nobjects = gInstance.Nobjects
  val eLENGTH = SwmmEnum.ConversionType.LENGTH.ordinal()
  val eLANDAREA = SwmmEnum.ConversionType.LANDAREA.ordinal()
  val ePOLLUT = SwmmEnum.ObjectType.POLLUT.ordinal()
  val eLINK = SwmmEnum.ObjectType.LINK.ordinal()
  val eSUBCATCH = SwmmEnum.ObjectType.SUBCATCH.ordinal()
  val eSNOWMELT = SwmmEnum.ObjectType.SNOWMELT.ordinal()
  val eAQUIFER = SwmmEnum.ObjectType.AQUIFER.ordinal()
  val eNode = SwmmEnum.ObjectType.NODE.ordinal()
  val eRAINFALL = ConversionType.RAINFALL.ordinal()

  val eRAINDEPTH = ConversionType.RAINDEPTH.ordinal()
//  val gIgnoreSnowmelt =gInstance.IgnoreSnowmelt
//  val gIgnoreRainfall =gInstance.IgnoreRainfall
//  val gIgnoreGwater =gInstance.IgnoreGwater
  val gIgnoreRDII =gInstance.IgnoreRDII

  val US = UnitsType.US

  val LINE_10= "----------"
  val LINE_12= "------------"
  val LINE_51 =  "---------------------------------------------------"
  val LINE_64 =  "----------------------------------------------------------------"


  def report_readOptions(tok:Array[String]):Int =
    //
    //  Input:   tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads reporting options from a line of input

    //
  {
    var  k = -1
    var   j, m, t=0
//    val  w_INPUT          ="INPUT"
//    val  w_CONTINUITY     ="CONTINUITY"
//    val  w_FLOWSTATS      ="FLOWSTATS"
//    val  w_CONTROLS       ="CONTROL"
//    val  w_NODESTATS      ="NODESTATS

    tok(0) match
    {
      case `w_INPUT`=> // Input
  //    m = findmatch(tok(1), NoYesWords)
      if      ( tok(1).equals("YES" )) RptFlags.input = TRUE
      else if ( tok(1).equals("NO" ))  RptFlags.input = FALSE
      else
        return ErrorUtil.error_setInpError(ERR_KEYWORD, tok(1))
      return 0

      case `w_CONTINUITY` => // Continuity
      var m = NoYesWords(tok(1))
      if      ( m  ) RptFlags.continuity = TRUE
      else   RptFlags.continuity = FALSE

      return 0

      case `w_FLOWSTATS` => // Flow Statistics
    //  m = findmatch(tok(1), NoYesWords)
      if      ( tok(1).equals("YES" )) RptFlags.flowStats = TRUE
      else if ( tok(1).equals("NO" ))  RptFlags.flowStats = FALSE
      else                 return ErrorUtil.error_setInpError(ERR_KEYWORD , tok(1))
      return 0

      case `w_CONTROLS` => // Controls
   //   m = findmatch(tok(1), NoYesWords)
      if      ( tok(1).equals("YES" )) RptFlags.controls = TRUE
      else if ( tok(1).equals("NO" ))  RptFlags.controls = FALSE
      else                 return ErrorUtil.error_setInpError(ERR_KEYWORD , tok(1))
      return 0
//FIXME
//      case 4 =>  m = eSUBCATCH    // Subcatchments
//      case 5 =>   m = eNode        // Nodes
//      case 6 => m = eLINK        // Links

      case `w_NODESTATS` => // Node Statistics
 //     m = findmatch(tok(1), NoYesWords)
      if      ( tok(1).equals("YES" )) RptFlags.nodeStats = TRUE
      else if ( tok(1).equals("NO" ))  RptFlags.nodeStats = FALSE
      else                 return ErrorUtil.error_setInpError(ERR_KEYWORD, tok(1))
      return 0

      case _ =>return ErrorUtil.error_setInpError(ERR_KEYWORD, tok(1))
    }
    //FIXME FIXME No idea what is doing
//    k = findmatch(tok(1), NoneAllWords)
//    if ( k < 0 )
//    {
//      k = NoneAllType.SOME
//      for (t <- 1 to  tok.length-1)
//      {
//        j = project.project_findObject(m, tok(t))
//        if ( j < 0 ) return ErrorUtil.error_setInpError(ERR_NAME, tok(t))
//        m match
//        {
//          case eSUBCATCH =>  Subcatch(j).rptFlag = TRUE
//          case eNODE =>      Node(j).rptFlag = TRUE
//          case eLINK =>      Link(j).rptFlag = TRUE
//        }
//      }
//    }
    m match
    {
      case `eSUBCATCH` =>  RptFlags.subcatchments = k
      case `eNode` =>    RptFlags.nodes = k
      case `eLINK` =>     RptFlags.links = k
    }
     0
  }

  //=============================================================================

  def report_writeLine(line:String) =
    //
    //  Input:   line = line of text
    //  Output:  none
    //  Purpose: writes line of text to report file.
    //
  {
    if ( Frpt.file!=null ) fprintf(Frpt.file, "\n  %s", line)
  }

  //=============================================================================

  def report_writeSysTime() =
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes starting/ending processing times to report file.
    //
  {
//    var   theTime:String=""
//    val  elapsedTime=.0
//    DateTime  endTime
//    if ( Frpt.file  !=null)
//    {
////      fprintf(Frpt.file, FMT20, ctime(&SysTime))
////      time(&endTime)
////      fprintf(Frpt.file, FMT20a, ctime(&endTime))
////      elapsedTime = difftime(endTime, SysTime)
////      fprintf(Frpt.file, FMT21)
////      if ( elapsedTime < 1.0 ) fprintf(Frpt.file, "< 1 sec")
////      else
////      {
////        elapsedTime /= gInstance.SECperDAY
////        if (elapsedTime >= 1.0)
////        {
////          fprintf(Frpt.file, "%d.", (int)floor(elapsedTime))
////          elapsedTime -= floor(elapsedTime)
////        }
////        datetime_timeToStr(elapsedTime, theTime)
////        fprintf(Frpt.file, "%s", theTime)
//      }
//    }
  }


  //=============================================================================
  //      SIMULATION OPTIONS REPORTING
  //=============================================================================

  def report_writeLogo()=
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes report header lines to report file.
    //
  {
    fprintf(Frpt.file, FMT08)
    fprintf(Frpt.file, FMT09)
    fprintf(Frpt.file, FMT10)
    //FIXME:
  //  time(&SysTime)                    // Save starting wall clock time
  }

  //=============================================================================

  def report_writeTitle() =
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes project title to report file.
    //
  {
  
//    if ( ErrorCode ) return
//    for (i <- 0 to MAXTITLE) if ( Title(i).length > 0 )
//  {
//    report_writeLine(Title(i))
//  }
  }

  //=============================================================================

  def report_writeOptions() =
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes analysis options in use to report file.
    //
  {
    val str:String =""
    report_writeLine("")
    report_writeLine("*********************************************************")
    report_writeLine("NOTE: The summary statistics displayed in this report are")
    report_writeLine("based on results found at every computational time step,  ")
    report_writeLine("not just on results from each reporting time step.")
    report_writeLine("*********************************************************")
    report_writeLine("")
    report_writeLine("****************")
    report_writeLine("Analysis Options")
    report_writeLine("****************")
//FIXME
    //    fprintf(Frpt.file, "\n  Flow Units ............... %s",
//      FlowUnitWords(FlowUnits))
    fprintf(Frpt.file, "\n  Process Models:")
    fprintf(Frpt.file, "\n    Rainfall/Runoff ........ ")
//  FIXME
//    if ( gIgnoreRainfall || Nobjects(eGAGE) == null )
//      fprintf(Frpt.file, "NO")
 //   else fprintf(Frpt.file, "YES")

    fprintf(Frpt.file, "\n    RDII ................... ")                     //(5.1.004)
    //FIXME
//    if ( gIgnoreRDII || Nobjects[UNITHYD] == 0 )
//      fprintf(Frpt.file, "NO")
 //   else fprintf(Frpt.file, "YES")

    fprintf(Frpt.file, "\n    Snowmelt ............... ")
    if ( gIgnoreSnowmelt || Nobjects(eSNOWMELT) == 0 )
      fprintf(Frpt.file, "NO")
    else fprintf(Frpt.file, "YES")
    fprintf(Frpt.file, "\n    Groundwater ............ ")
    if ( gIgnoreGwater || Nobjects(eAQUIFER) == 0 )
      fprintf(Frpt.file, "NO")
    else fprintf(Frpt.file, "YES")
    fprintf(Frpt.file, "\n    Flow Routing ........... ")
    if ( gIgnoreRouting || Nobjects(eLINK) == 0 )
      fprintf(Frpt.file, "NO")
    else
    {
      fprintf(Frpt.file, "YES")
      fprintf(Frpt.file, "\n    Ponding Allowed ........ ")
      if ( gInstance.AllowPonding ) fprintf(Frpt.file, "YES")
      else                fprintf(Frpt.file, "NO")
    }
    fprintf(Frpt.file, "\n    Water Quality .......... ")
    if ( gIgnoreQuality || Nobjects(ePOLLUT) == 0 )
      fprintf(Frpt.file, "NO")
    else fprintf(Frpt.file, "YES")
//FiXME
//    if ( Nobjects(eSUBCATCH) > 0 )
//      fprintf(Frpt.file, "\n  Infiltration Method ...... %s",
//        InfilModelWords[InfilModel])
//    if ( Nobjects(eLINK) > 0 )
//      fprintf(Frpt.file, "\n  Flow Routing Method ...... %s",
//        RouteModelWords[RouteModel])
//    datetime_dateToStr(gInstance.StartDate, str)
//    fprintf(Frpt.file, "\n  Starting Date ............ %s", str)
//    datetime_timeToStr(gInstance.StartTime, str)
//    fprintf(Frpt.file, " %s", str)
//    datetime_dateToStr(gInstance.EndDate, str)
//    fprintf(Frpt.file, "\n  Ending Date .............. %s", str)
//    datetime_timeToStr(gInstance.EndTime, str)
//    fprintf(Frpt.file, " %s", str)
//    fprintf(Frpt.file, "\n  Antecedent Dry Days ...... %.1f", gInstance.StartDryDays)
//    datetime_timeToStr(datetime_encodeTime(0, 0, ReportStep), str)
//    fprintf(Frpt.file, "\n  Report Time Step ......... %s", str)
//    if ( Nobjects(eSUBCATCH) > 0 )
//    {
//      datetime_timeToStr(datetime_encodeTime(0, 0, WetStep), str)
//      fprintf(Frpt.file, "\n  Wet Time Step ............ %s", str)
//      datetime_timeToStr(datetime_encodeTime(0, 0, DryStep), str)
//      fprintf(Frpt.file, "\n  Dry Time Step ............ %s", str)
//    }
    if ( Nobjects(eLINK) > 0 )
    {
      fprintf(Frpt.file, "\n  Routing Time Step ........ %.2f sec", gInstance.RouteStep)
      if ( gInstance.RouteModel == RouteModelType.DW )
      {
        fprintf(Frpt.file, "\n  Variable Time Step ....... ")
        if ( gInstance.CourantFactor > 0.0 ) fprintf(Frpt.file, "YES")
        else                       fprintf(Frpt.file, "NO")
        fprintf(Frpt.file, "\n  Maximum Trials ........... %d", gInstance.MaxTrials)
        fprintf(Frpt.file, "\n  Number of Threads ........ %d", gInstance.NumThreads)   //(5.1.008)
        fprintf(Frpt.file, "\n  Head Tolerance ........... %.6f ",
          gInstance.HeadTol*UCF(eLENGTH))                                              //(5.1.008)
        if ( UnitSystem == US ) fprintf(Frpt.file, "ft")
        else                    fprintf(Frpt.file, "m")
      }
    }
    report_writeLine("")
  }


  //=============================================================================
  //      RAINFALL FILE REPORTING
  //=============================================================================

  def report_writeRainStats( i:Int,  r:TRainStats)=
    //
    //  Input:   i = rain gage index
    //           r = rain file summary statistics
    //  Output:  none
    //  Purpose: writes summary of rain data read from file to report file.
    //
  {
    val date1 = "***********"
    val date2 = "***********"
    if ( i < 0 )
    {
      report_writeLine("")
      report_writeLine("*********************")
      report_writeLine("Rainfall File Summary")
      report_writeLine("*********************")
      fprintf(Frpt.file,
        "\n  Station    First        Last         Recording   Periods    Periods    Periods")
      fprintf(Frpt.file,
        "\n  ID         Date         Date         Frequency  w/Precip    Missing    Malfunc.")
      fprintf(Frpt.file,
        "\n  -------------------------------------------------------------------------------\n")
    }
    else
    {
      //FIXME
//      if ( r.startDate != NO_DATE ) datetime_dateToStr(r.startDate, date1)
//      if ( r.endDate   != NO_DATE ) datetime_dateToStr(r.endDate, date2)
      fprintf(Frpt.file, "  %-10s %-11s  %-11s  %5d min    %6ld     %6ld     %6ld\n",
        Gage(i).staID, date1, date2, Gage(i).rainInterval/60,
        r.periodsRain, r.periodsMissing, r.periodsMalfunc)
    }
  }


  //=============================================================================
  //      RDII REPORTING
  //=============================================================================

  def report_writeRdiiStats( rainVol:Double,  rdiiVol:Double)=
    //
    //  Input:   rainVol = total rainfall volume over sewershed
    //           rdiiVol = total RDII volume produced
    //  Output:  none
    //  Purpose: writes summary of RDII inflow to report file.
    //
  {
    var ratio =.0
    var  ucf1, ucf2 =.0

    ucf1 = UCF(eLENGTH) * UCF(eLANDAREA)
    if ( UnitSystem == US) ucf2 = SwmmConst.MGDperCFS / SwmmConst.SECperDAY
    else                   ucf2 = SwmmConst.MLDperCFS / SwmmConst.SECperDAY

    report_writeLine("")
    fprintf(Frpt.file,
      "\n  **********************           Volume        Volume")
    if ( UnitSystem == US) fprintf(Frpt.file,
      "\n  Rainfall Dependent I/I        acre-feet      10^6 gal")
    else fprintf(Frpt.file,
      "\n  Rainfall Dependent I/I        hectare-m      10^6 ltr")
    fprintf(Frpt.file,
      "\n  **********************        ---------     ---------")

    fprintf(Frpt.file, "\n  Sewershed Rainfall ......%14.3f%14.3f",
      rainVol * ucf1, rainVol * ucf2)

    fprintf(Frpt.file, "\n  RDII Produced ...........%14.3f%14.3f",
      rdiiVol * ucf1, rdiiVol * ucf2)

    if ( rainVol == 0.0 ) ratio = 0.0
    else ratio = rdiiVol / rainVol
    fprintf(Frpt.file, "\n  RDII Ratio ..............%14.3f", ratio)
    report_writeLine("")
  }


  //=============================================================================
  //      CONTROL ACTIONS REPORTING
  //=============================================================================

  def   report_writeControlActionsHeading() =
  {
    report_writeLine("")
    report_writeLine("*********************")
    report_writeLine("Control Actions Taken")
    report_writeLine("*********************")
    fprintf(Frpt.file, "\n")
  }

  //=============================================================================

  def   report_writeControlAction( aDate:DateTime,  linkID:String,  value:Double,
    ruleID:String) =
    //
    //  Input:   aDate  = date/time of rule action
    //           linkID = ID of link being controlled
    //           value  = new status value of link
    //           ruleID = ID of rule implementing the action
    //  Output:  none
    //  Purpose: reports action taken by a control rule.
    //
  {
    var     theDate:String =""
    val     theTime:String =""
    //FIXME date to String
//    datetime_dateToStr(aDate, theDate)
//    datetime_timeToStr(aDate, theTime)
    fprintf(Frpt.file,
      "  %11s: %8s Link %s setting changed to %6.2f by Control %s\n",
      theDate, theTime, linkID, value, ruleID)
  }


  //=============================================================================
  //      CONTINUITY ERROR REPORTING
  //=============================================================================

  def report_writeRunoffError( totals:TRunoffTotals,  totalArea:Double):Int=
    //
    //  Input:  totals = accumulated runoff totals
    //          totalArea = total area of all subcatchments
    //  Output:  none
    //  Purpose: writes runoff continuity error to report file.
    //
  {

    if ( Frunoff.mode == FileUsageType.USE_FILE )
    {
      report_writeLine("")
      fprintf(Frpt.file,
        "\n  **************************"+
      "\n  Runoff Quantity Continuity"+
      "\n  **************************"+
      "\n  Runoff supplied by interface file %s", Frunoff.name)
      report_writeLine("")
      return  0
    }

    if ( totalArea == 0.0 ) return 0
    report_writeLine("")

    fprintf(Frpt.file,
      "\n  **************************        Volume         Depth")
    if ( UnitSystem == US) fprintf(Frpt.file,
      "\n  Runoff Quantity Continuity     acre-feet        inches")
    else fprintf(Frpt.file,
      "\n  Runoff Quantity Continuity     hectare-m            mm")
    fprintf(Frpt.file,
      "\n  **************************     ---------       -------")

    if ( totals.initStorage > 0.0 )
    {
      fprintf(Frpt.file, "\n  Initial LID Storage ......%14.3f%14.3f",
        totals.initStorage * UCF(eLENGTH) * UCF(eLANDAREA),
        totals.initStorage / totalArea * UCF(eRAINDEPTH))
    }

    if ( Nobjects(eSNOWMELT) > 0 )
    {
      fprintf(Frpt.file, "\n  Initial Snow Cover .......%14.3f%14.3f",
        totals.initSnowCover * UCF(eLENGTH) * UCF(eLANDAREA),
        totals.initSnowCover / totalArea * UCF(eRAINDEPTH))
    }

    fprintf(Frpt.file, "\n  Total Precipitation ......%14.3f%14.3f",
      totals.rainfall * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.rainfall / totalArea * UCF(eRAINDEPTH))

    ////  Following code segment added to release 5.1.008.  ////                   //(5.1.008)
    if ( totals.runon > 0.0 )
    {
      fprintf(Frpt.file, "\n  Outfall Runon ............%14.3f%14.3f",
        totals.runon * UCF(eLENGTH) * UCF(eLANDAREA),
        totals.runon / totalArea * UCF(eRAINDEPTH))
    }
    ////

    fprintf(Frpt.file, "\n  Evaporation Loss .........%14.3f%14.3f",
      totals.evap * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.evap / totalArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Infiltration Loss ........%14.3f%14.3f",
      totals.infil * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.infil / totalArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Surface Runoff ...........%14.3f%14.3f",
      totals.runoff * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.runoff / totalArea * UCF(eRAINDEPTH))

    ////  Following code segment added to release 5.1.008.  ////                   //(5.1.008)
    if ( totals.drains > 0.0 )
    {
      fprintf(Frpt.file, "\n  LID Drainage .............%14.3f%14.3f",
        totals.drains * UCF(eLENGTH) * UCF(eLANDAREA),
        totals.drains / totalArea * UCF(eRAINDEPTH))
    }

    if ( Nobjects(eSNOWMELT) > 0 )
    {
      fprintf(Frpt.file, "\n  Snow Removed .............%14.3f%14.3f",
        totals.snowRemoved * UCF(eLENGTH) * UCF(eLANDAREA),
        totals.snowRemoved / totalArea * UCF(eRAINDEPTH))
      fprintf(Frpt.file, "\n  Final Snow Cover .........%14.3f%14.3f",
        totals.finalSnowCover * UCF(eLENGTH) * UCF(eLANDAREA),
        totals.finalSnowCover / totalArea * UCF(eRAINDEPTH))
    }

    fprintf(Frpt.file, "\n  Final Storage ............%14.3f%14.3f",           //(5.1.008)
      totals.finalStorage * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.finalStorage / totalArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Continuity Error (%%) .....%14.3f",
      totals.pctError)
    report_writeLine("")
    1
  }

  //=============================================================================

  def report_writeLoadingError( totals:Array[TLoadingTotals])
    //
    //  Input:   totals = accumulated pollutant loading totals
    //           area = total area of all subcatchments
    //  Output:  none
    //  Purpose: writes runoff loading continuity error to report file.
    //
  {

    var p1 = 1
    var p2 = Math.min(5, Nobjects(ePOLLUT))
    while ( p1 <= Nobjects(ePOLLUT) )
    {
      report_LoadingErrors(p1-1, p2-1, totals)
      p1 = p2 + 1
      p2 = p1 + 4
      p2 = Math.min(p2, Nobjects(ePOLLUT))
    }
  }

  //=============================================================================

  def report_LoadingErrors( p1:Int,  p2:Int,  totals:Array[TLoadingTotals]) =
    //
    //  Input:   p1 = index of first pollutant to report
    //           p2 = index of last pollutant to report
    //           totals = accumulated pollutant loading totals
    //           area = total area of all subcatchments
    //  Output:  none
    //  Purpose: writes runoff loading continuity error to report file for
    //           up to 5 pollutants at a time.
    //
  {
    var    i,p:Int=0
    var cf:Double = 1.0
    var   units:String=""

    report_writeLine("")
    fprintf(Frpt.file, "\n  **************************")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14s", Pollut(p).id)
    }
    fprintf(Frpt.file, "\n  Runoff Quality Continuity ")
    for ( pr <- p1 to p2 )
    {
      i = UnitSystem.ordinal()
      if ( Pollut(p).units == ConcUnitsType.COUNT ) i = 2
      //FIXME retrieve set value
//      units= if(LoadUnitsWords(i))
//      fprintf(Frpt.file, "%14s", units)
    }
    fprintf(Frpt.file, "\n  **************************")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "    ----------")
    }

    fprintf(Frpt.file, "\n  Initial Buildup ..........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).initLoad*cf)
    }
    fprintf(Frpt.file, "\n  Surface Buildup ..........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).buildup*cf)
    }
    fprintf(Frpt.file, "\n  Wet Deposition ...........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).deposition*cf)
    }
    fprintf(Frpt.file, "\n  Sweeping Removal .........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).sweeping*cf)
    }
    fprintf(Frpt.file, "\n  Infiltration Loss ........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).infil*cf)
    }
    fprintf(Frpt.file, "\n  BMP Removal ..............")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).bmpRemoval*cf)
    }
    fprintf(Frpt.file, "\n  Surface Runoff ...........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).runoff*cf)
    }
    fprintf(Frpt.file, "\n  Remaining Buildup ........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).finalLoad*cf)
    }
    fprintf(Frpt.file, "\n  Continuity Error (%%) .....")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", totals(p).pctError)
    }
    report_writeLine("")
  }

  //=============================================================================

  def report_writeGwaterError( totals:TGwaterTotals,  gwArea:Double)=
    //
    //  Input:   totals = accumulated groundwater totals
    //           gwArea = total area of all subcatchments with groundwater
    //  Output:  none
    //  Purpose: writes groundwater continuity error to report file.
    //
  {
    report_writeLine("")
    fprintf(Frpt.file,
      "\n  **************************        Volume         Depth")
    if ( UnitSystem == US) fprintf(Frpt.file,
      "\n  Groundwater Continuity         acre-feet        inches")
    else fprintf(Frpt.file,
      "\n  Groundwater Continuity         hectare-m            mm")
    fprintf(Frpt.file,
      "\n  **************************     ---------       -------")
    fprintf(Frpt.file, "\n  Initial Storage ..........%14.3f%14.3f",
      totals.initStorage * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.initStorage / gwArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Infiltration .............%14.3f%14.3f",
      totals.infil * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.infil / gwArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Upper Zone ET ............%14.3f%14.3f",
      totals.upperEvap * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.upperEvap / gwArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Lower Zone ET ............%14.3f%14.3f",
      totals.lowerEvap * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.lowerEvap / gwArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Deep Percolation .........%14.3f%14.3f",
      totals.lowerPerc * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.lowerPerc / gwArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Groundwater Flow .........%14.3f%14.3f",
      totals.gwater * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.gwater / gwArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Final Storage ............%14.3f%14.3f",
      totals.finalStorage * UCF(eLENGTH) * UCF(eLANDAREA),
      totals.finalStorage / gwArea * UCF(eRAINDEPTH))

    fprintf(Frpt.file, "\n  Continuity Error (%%) .....%14.3f",
      totals.pctError)
    report_writeLine("")
  }

  //=============================================================================

  def report_writeFlowError(totals:TRoutingTotals)=
    //
    //  Input:  totals = accumulated flow routing totals
    //  Output:  none
    //  Purpose: writes flow routing continuity error to report file.
    //
  {
    var ucf1, ucf2 = .0

     ucf1 = UCF(eLENGTH) * UCF(eLANDAREA)
    if ( UnitSystem == US) ucf2 = SwmmConst.MGDperCFS / SwmmConst.SECperDAY
    else                   ucf2 = SwmmConst.MLDperCFS / SwmmConst.SECperDAY

    report_writeLine("")
    fprintf(Frpt.file,
      "\n  **************************        Volume        Volume")
    if ( UnitSystem == US) fprintf(Frpt.file,
      "\n  Flow Routing Continuity        acre-feet      10^6 gal")
    else fprintf(Frpt.file,
      "\n  Flow Routing Continuity        hectare-m      10^6 ltr")
    fprintf(Frpt.file,
      "\n  **************************     ---------     ---------")

    fprintf(Frpt.file, "\n  Dry Weather Inflow .......%14.3f%14.3f",
      totals.dwInflow * ucf1, totals.dwInflow * ucf2)

    fprintf(Frpt.file, "\n  Wet Weather Inflow .......%14.3f%14.3f",
      totals.wwInflow * ucf1, totals.wwInflow * ucf2)

    fprintf(Frpt.file, "\n  Groundwater Inflow .......%14.3f%14.3f",
      totals.gwInflow * ucf1, totals.gwInflow * ucf2)

    fprintf(Frpt.file, "\n  RDII Inflow ..............%14.3f%14.3f",
      totals.iiInflow * ucf1, totals.iiInflow * ucf2)

    fprintf(Frpt.file, "\n  External Inflow ..........%14.3f%14.3f",
      totals.exInflow * ucf1, totals.exInflow * ucf2)

    fprintf(Frpt.file, "\n  External Outflow .........%14.3f%14.3f",
      totals.outflow * ucf1, totals.outflow * ucf2)

    fprintf(Frpt.file, "\n  Flooding Loss ............%14.3f%14.3f",           //(5.1.008)
      totals.flooding * ucf1, totals.flooding * ucf2)

    fprintf(Frpt.file, "\n  Evaporation Loss .........%14.3f%14.3f",
      totals.evapLoss * ucf1, totals.evapLoss * ucf2)

    fprintf(Frpt.file, "\n  Exfiltration Loss ........%14.3f%14.3f",           //(5.1.007)
      totals.seepLoss * ucf1, totals.seepLoss * ucf2)

    fprintf(Frpt.file, "\n  Initial Stored Volume ....%14.3f%14.3f",
      totals.initStorage * ucf1, totals.initStorage * ucf2)

    fprintf(Frpt.file, "\n  Final Stored Volume ......%14.3f%14.3f",
      totals.finalStorage * ucf1, totals.finalStorage * ucf2)

    fprintf(Frpt.file, "\n  Continuity Error (%%) .....%14.3f",
      totals.pctError)
    report_writeLine("")
  }

  //=============================================================================

  def report_writeQualError( QualTotals:Array[TRoutingTotals])=
  //
  //  Input:   totals = accumulated quality routing totals for each pollutant
  //  Output:  none
  //  Purpose: writes quality routing continuity error to report file.
  //
  {
//    int p1, p2
    var p1 = 1
    var p2 = Math.min(5, Nobjects(ePOLLUT))
    while ( p1 <= Nobjects(ePOLLUT) )
    {
      report_QualErrors(p1-1, p2-1, QualTotals)
      p1 = p2 + 1
      p2 = p1 + 4
      p2 = Math.min(p2, Nobjects(ePOLLUT))
    }
  }

  //=============================================================================

  def report_QualErrors( p1:Int,  p2:Int,  QualTotals:Array[TRoutingTotals])=
  {
    var   i =0
    var   p =0
    var  units:String =""

    report_writeLine("")
    fprintf(Frpt.file, "\n  **************************")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14s", Pollut(p).id)
    }
    fprintf(Frpt.file, "\n  Quality Routing Continuity")
    for ( pr <- p1 to p2 )
    {
      i = UnitSystem.ordinal()
      //FIXME
//      if ( Pollut(p).units == COUNT ) i = 2
//      units=LoadUnitsWords
      fprintf(Frpt.file, "%14s", units)
    }
    fprintf(Frpt.file, "\n  **************************")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "    ----------")
    }

    fprintf(Frpt.file, "\n  Dry Weather Inflow .......")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).dwInflow)
    }

    fprintf(Frpt.file, "\n  Wet Weather Inflow .......")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).wwInflow)
    }

    fprintf(Frpt.file, "\n  Groundwater Inflow .......")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).gwInflow)
    }

    fprintf(Frpt.file, "\n  RDII Inflow ..............")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).iiInflow)
    }

    fprintf(Frpt.file, "\n  External Inflow ..........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).exInflow)
    }

    fprintf(Frpt.file, "\n  External Outflow .........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).outflow)
    }

    fprintf(Frpt.file, "\n  Flooding Loss ............")                      //(5.1.008)
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).flooding)
    }

    ////  Following code segment added to release 5.1.008.  ////                   //(5.1.008)
    ////
    fprintf(Frpt.file, "\n  Exfiltration Loss ........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).seepLoss)
    }
    ////

    fprintf(Frpt.file, "\n  Mass Reacted .............")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).reacted)
    }

    fprintf(Frpt.file, "\n  Initial Stored Mass ......")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).initStorage)
    }

    fprintf(Frpt.file, "\n  Final Stored Mass ........")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).finalStorage)
    }

    fprintf(Frpt.file, "\n  Continuity Error (%%) .....")
    for ( pr <- p1 to p2 )
    {
      fprintf(Frpt.file, "%14.3f", QualTotals(p).pctError)
    }
    report_writeLine("")
  }

  //=============================================================================

  def report_writeMaxStats( maxMassBalErrs:Array[TMaxStats],  maxCourantCrit:Array[TMaxStats],
   nMaxStats:Int):Int =
  //
  //  Input:   maxMassBal[] = nodes with highest mass balance errors
  //           maxCourantCrit[] = nodes most often Courant time step critical
  //           maxLinkTimes[] = links most often Courant time step critical
  //           nMaxStats = number of most critical nodes/links saved
  //  Output:  none
  //  Purpose: lists nodes & links with highest mass balance errors and
  //           time Courant time step critical
  //
  {
    var j, k =0

    if ( gInstance.RouteModel != RouteModelType.DW || Nobjects(eLINK) == 0 ) return 0
    if ( nMaxStats <= 0 ) return 0
    if ( maxMassBalErrs(0).index >= 0 )
    {
      report_writeLine("")
      report_writeLine("*************************")
      report_writeLine("Highest Continuity Errors")
      report_writeLine("*************************")
      for (i <- 0 to nMaxStats)
      {
        j = maxMassBalErrs(i).index
        if ( j >= 0 )
          {
            fprintf(Frpt.file, "\n  Node %s (%.2f%%)",
              Node(j).id, maxMassBalErrs(i).value)

          }
      }
      report_writeLine("")
    }

    if ( gInstance.CourantFactor == 0.0 ) return 0
    report_writeLine("")
    report_writeLine("***************************")
    report_writeLine("Time-Step Critical Elements")
    report_writeLine("***************************")
    k = 0
    for( i <- 0 to nMaxStats)
    {
      j = maxCourantCrit(i).index
      //FIXME
//      if ( j < 0 ) continue
//      k++
//      if ( maxCourantCrit(i).objType == NODE )
//        fprintf(Frpt.file, "\n  Node %s", Node(j).id)
//      else fprintf(Frpt.file, "\n  Link %s", Link(j).id)
      fprintf(Frpt.file, " (%.2f%%)", maxCourantCrit(i).value)
    }
    if ( k == 0 ) fprintf(Frpt.file, "\n  None")
    report_writeLine("")
    1
  }

  //=============================================================================

  def report_writeMaxFlowTurns( flowTurns:Array[TMaxStats],  nMaxStats:Int) :Int  =
  //
  //  Input:   flowTurns[] = links with highest number of flow turns
  //           nMaxStats = number of links in flowTurns[]
  //  Output:  none
  //  Purpose: lists links with highest number of flow turns (i.e., fraction
  //           of time periods where the flow is higher (or lower) than the
  //           flows in the previous and following periods).
  //
  {
    var j =0

    if ( Nobjects(eLINK) == 0 ) return 0
    report_writeLine("")
    report_writeLine("********************************")
    report_writeLine("Highest Flow Instability Indexes")
    report_writeLine("********************************")
    if ( nMaxStats <= 0 || flowTurns(0).index <= 0 )
    fprintf(Frpt.file, "\n  All links are stable.")
    else
    {
      for (i <- 0 to nMaxStats)
      {
        j = flowTurns(i).index
        if ( j >=0)
          fprintf(Frpt.file, "\n  Link %s (%.0f)",
            Link(j).id, flowTurns(i).value)
      }
    }
    report_writeLine("")
    1
  }

  //=============================================================================

  def report_writeSysStats( sysStats:TSysStats):Boolean =
    //
    //  Input:   sysStats = simulation statistics for overall system
    //  Output:  none
    //  Purpose: writes simulation statistics for overall system to report file.
    //
  {
    var x =.0 

    if ( Nobjects(eLINK) == 0 || gInstance.StepCount == 0 ) return false
    report_writeLine("")
    report_writeLine("*************************")
    report_writeLine("Routing Time Step Summary")
    report_writeLine("*************************")
    fprintf(Frpt.file,
      "\n  Minimum Time Step           :  %7.2f sec",
      sysStats.minTimeStep)
    fprintf(Frpt.file,
      "\n  Average Time Step           :  %7.2f sec",
      sysStats.avgTimeStep / gInstance.StepCount)
    fprintf(Frpt.file,
      "\n  Maximum Time Step           :  %7.2f sec",
      sysStats.maxTimeStep)
    x = sysStats.steadyStateCount / gInstance.StepCount * 100.0
    fprintf(Frpt.file,
      "\n  Percent in Steady State     :  %7.2f", Math.min(x, 100.0))
    fprintf(Frpt.file,
      "\n  Average Iterations per Step :  %7.2f",
      sysStats.avgStepCount / gInstance.StepCount)
    fprintf(Frpt.file,
      "\n  Percent Not Converging      :  %7.2f",
      100.0 * gInstance.NonConvergeCount / gInstance.StepCount)
    report_writeLine("")
    true
  }


  //=============================================================================
  //      SIMULATION RESULTS REPORTING
  //=============================================================================

  def report_writeReport():Int=
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes simulation results to report file.
    //
  {
    if ( gInstance.ErrorCode>0 ) return 0
    if ( gInstance.Nperiods == 0 ) return 0
    if (RptFlags.subcatchments == null || !(gIgnoreRainfall == FALSE ||
      !gIgnoreSnowmelt ||
      !gIgnoreGwater)) {

    } else {
      report_Subcatchments()
    }

    if ( gIgnoreRouting && gIgnoreQuality ) return 0
    if ( RptFlags.nodes >0 ) report_Nodes()
    if ( RptFlags.links >0 ) report_Links()
    1
  }

  //=============================================================================

  def report_Subcatchments():Int =
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes results for selected subcatchments to report file.
    //
  {
    var      j, p, k =0
    var      period =0
 //   var days:Datetime =null
    var     theDate:String =""
    var      theTime:String =""
    var      hasSnowmelt = Nobjects(eSNOWMELT) > 0 && !gIgnoreSnowmelt
    var      hasGwater   = Nobjects(eAQUIFER) > 0 && !gIgnoreGwater
    var      hasQuality  = Nobjects(ePOLLUT) > 0 && !gIgnoreQuality

    if ( Nobjects(eSUBCATCH) == 0 ) return 0
    report_writeLine("") 
    report_writeLine("********************")
    report_writeLine("Subcatchment Results")
    report_writeLine("********************")

    for (j <- 0 to Nobjects(eSUBCATCH))
    {
      if ( Subcatch(j).rptFlag == TRUE )
      {
        report_SubcatchHeader(Subcatch(j).id)
        //FIXME
//        for ( period <- 1 to Nperiods  )
//        {
//          output_readDateTime(period, &days)
//          datetime_dateToStr(days, theDate)
//          datetime_timeToStr(days, theTime)
//          output_readSubcatchResults(period, k)
//          fprintf(Frpt.file, "\n  %11s %8s %10.3f%10.3f%10.4f",
//            theDate, theTime, SubcatchResults[SUBCATCH_RAINFALL],
//            SubcatchResults[SUBCATCH_EVAP]/24.0 +
//              SubcatchResults[SUBCATCH_INFIL],
//            SubcatchResults[SUBCATCH_RUNOFF])
//          if ( hasSnowmelt )
//            fprintf(Frpt.file, "  %10.3f",
//              SubcatchResults[SUBCATCH_SNOWDEPTH])
//          if ( hasGwater )
//            fprintf(Frpt.file, "%10.3f%10.4f",
//              SubcatchResults[SUBCATCH_GW_ELEV],
//              SubcatchResults[SUBCATCH_GW_FLOW])
//          if ( hasQuality )
//            for (p <- 0 to Nobjects(ePOLLUT)-1)
//            fprintf(Frpt.file, "%10.3f",
//            SubcatchResults(SUBCATCH_WASHOFF+p))
//        }
//        report_writeLine("")
//        k++
      }
    }
    1
  }

  //=============================================================================

  def  report_SubcatchHeader(id:String) =
    //
    //  Input:   id = subcatchment ID name
    //  Output:  none
    //  Purpose: writes table headings for subcatchment results to report file.
    //
  {
    var i =0
    val hasSnowmelt = Nobjects(eSNOWMELT) > 0 && !gIgnoreSnowmelt
    val hasGwater   = Nobjects(eAQUIFER) > 0  && !gIgnoreGwater
    val hasQuality  = Nobjects(ePOLLUT) > 0 && !gIgnoreQuality

    // --- print top border of header
    report_writeLine("")
    fprintf(Frpt.file,"\n  <<< Subcatchment %s >>>", id)
    report_writeLine(LINE_51)
    if ( hasSnowmelt   ) fprintf(Frpt.file, LINE_12)
    if ( hasGwater )
    {
      fprintf(Frpt.file, LINE_10)
      fprintf(Frpt.file, LINE_10)
    }
    if ( hasQuality )
    {
      for (i <- 0 to Nobjects(ePOLLUT) ) fprintf(Frpt.file, LINE_10)
    }

    // --- print first line of column headings
    fprintf(Frpt.file,
      "\n  Date        Time        Precip.    Losses    Runoff")
    if ( hasSnowmelt ) fprintf(Frpt.file, "  Snow Depth")
    if ( hasGwater   ) fprintf(Frpt.file, "  GW Elev.   GW Flow")
    if ( hasQuality )
      for (i <- 0 to Nobjects(ePOLLUT) )
        fprintf(Frpt.file, "%10s", Pollut(i).id)

    // --- print second line of column headings
    //FIXME:
//    if ( UnitSystem == US ) fprintf(Frpt.file,
//      "\n                            in/hr     in/hr %9s", FlowUnitWords(FlowUnits))
//    else fprintf(Frpt.file,
//      "\n                            mm/hr     mm/hr %9s", FlowUnitWords(FlowUnits))
//    if ( hasSnowmelt )
//    {
//      if ( UnitSystem == US ) fprintf(Frpt.file, "      inches")
//      else                    fprintf(Frpt.file, "     mmeters")
//    }
//    if ( hasGwater )
//    {
//      if ( UnitSystem == US )
//        fprintf(Frpt.file, "      feet %9s", FlowUnitWords(FlowUnits))
//      else
//        fprintf(Frpt.file, "    meters %9s", FlowUnitWords(FlowUnits))
//    }
//    if ( hasQuality )
//      for (i <- 0 to Nobjects(ePOLLUT) )
//        fprintf(Frpt.file, "%10s", QualUnitsWords[Pollut(i).units])

    // --- print lower border of header
    report_writeLine(LINE_51)
    if ( hasSnowmelt ) fprintf(Frpt.file, LINE_12)
    if ( hasGwater )
    {
      fprintf(Frpt.file, LINE_10)
      fprintf(Frpt.file, LINE_10)
    }
    if ( hasQuality )
      for (i <- 0 to Nobjects(ePOLLUT) )
        fprintf(Frpt.file, LINE_10)
  }

  //=============================================================================

  def report_Nodes() =
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes results for selected nodes to report file.
    //
  {
    var       j, p, k=0
    var      period:Int =0
  //  var days:DateTime
    var     theDate, theTime:String =""

   //FIXME? Not right
   // if ( Nobjects(eNODE) == 0 ) return
    report_writeLine("")
    report_writeLine("************")
    report_writeLine("Node Results")
    report_writeLine("************")

     for (j<- 0 to Nobjects(eNode) )
    {
      if ( Node(j).rptFlag == TRUE )
      {
        report_NodeHeader(Node(j).id)
        for ( period <- 1  to gInstance.Nperiods.toInt )
        {
          //FIXME
//          output_readDateTime(period, &days)
//          datetime_dateToStr(days, theDate)
//          datetime_timeToStr(days, theTime)
//          output_readNodeResults(period, k)
//          fprintf(Frpt.file, "\n  %11s %8s  %9.3f %9.3f %9.3f %9.3f",
//            theDate, theTime, NodeResults[NODE_INFLOW],
//            NodeResults[NODE_OVERFLOW], NodeResults[NODE_DEPTH],
//            NodeResults[NODE_HEAD])
//          if ( !gIgnoreQuality ) for (p = 0 p < Nobjects(ePOLLUT) p++)
//          fprintf(Frpt.file, " %9.3f", NodeResults[NODE_QUAL + p])
        }
        report_writeLine("")
        //???
       // k ++
      }
    }
  }

  //=============================================================================

  def  report_NodeHeader(id:String) =
    //
    //  Input:   id = node ID name
    //  Output:  none
    //  Purpose: writes table headings for node results to report file.
    //
  {
    var i =0
    var lengthUnits=""
    report_writeLine("")
    fprintf(Frpt.file,"\n  <<< Node %s >>>", id)
    report_writeLine(LINE_64)
    for (i <- 0 to Nobjects(ePOLLUT) ) fprintf(Frpt.file, LINE_10)

    fprintf(Frpt.file,
      "\n                           Inflow  Flooding     Depth      Head")
    if ( !gIgnoreQuality ) for (i <- 0 to Nobjects(ePOLLUT) )
    fprintf(Frpt.file, "%10s", Pollut(i).id)
    if ( UnitSystem == US) lengthUnits= "feet"
    else lengthUnits = "meters"
//FIXME
    //    fprintf(Frpt.file,
//      "\n  Date        Time      %9s %9s %9s %9s",
//      FlowUnitWords(FlowUnits), FlowUnitWords(FlowUnits),
//      lengthUnits, lengthUnits)
//    if ( !gIgnoreQuality ) for (i <- 0 to Nobjects(ePOLLUT) )
//    fprintf(Frpt.file, "%10s", QualUnitsWords[Pollut(i).units])

    report_writeLine(LINE_64)
    if ( !gIgnoreQuality )
      for (i <- 0 to Nobjects(ePOLLUT) ) fprintf(Frpt.file, LINE_10)
  }

  //=============================================================================

  def report_Links()=
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes results for selected links to report file.
    //
  {
    var       j, p, k =0

    var days:DateTime=null
    var     theDate:String =""
    var     theTime:String =""

    if ( Nobjects(eLINK) != 0 )
      {
        report_writeLine("")
        report_writeLine("************")
        report_writeLine("Link Results")
        report_writeLine("************")

        for (j <-  0 to  Nobjects(eLINK)-1)
        {
          if ( Link(j).rptFlag == TRUE )
          {
            //FIXME: TODO
            //        report_LinkHeader(LINK(j).id)
            //        for ( period <- 1 to Nperiods  )
            //        {
            //
            ////          output_readDateTime(period, &days)
            ////          datetime_dateToStr(days, theDate)
            ////          datetime_timeToStr(days, theTime)
            ////          output_readLinkResults(period, k)
            ////          fprintf(Frpt.file, "\n  %11s %8s  %9.3f %9.3f %9.3f %9.3f",
            ////            theDate, theTime, LinkResults[LINK_FLOW],
            ////            LinkResults[LINK_VELOCITY], LinkResults[LINK_DEPTH],
            ////            LinkResults[LINK_CAPACITY])
            ////          if ( !gIgnoreQuality ) for (p <- 0 to Nobjects(ePOLLUT))
            ////          fprintf(Frpt.file, " %9.3f", LinkResults[LINK_QUAL + p])
            //        }
            //        report_writeLine("")
            //        k++
          }
        }

      }
  }

  //=============================================================================

  def  report_LinkHeader(id:String) =
    //
    //  Input:   id = link ID name
    //  Output:  none
    //  Purpose: writes table headings for link results to report file.
    //
  {
    var i =0
    report_writeLine("")
    fprintf(Frpt.file,"\n  <<< Link %s >>>", id)
    report_writeLine(LINE_64)
    for (i <- 0 to Nobjects(ePOLLUT) ) fprintf(Frpt.file, LINE_10)

    fprintf(Frpt.file,
      "\n                             Flow  Velocity     Depth  Capacity/")
    if ( !gIgnoreQuality ) for (i <- 0 to Nobjects(ePOLLUT) )
    fprintf(Frpt.file, "%10s", Pollut(i).id)
//FIXME
//    if ( UnitSystem == US )
//      fprintf(Frpt.file,
//        "\n  Date        Time     %10s    ft/sec      feet   Setting ",
//        FlowUnitWords(FlowUnits))
//    else
//      fprintf(Frpt.file,
//        "\n  Date        Time     %10s     m/sec    meters   Setting ",
//        FlowUnitWords(FlowUnits))
//    if ( !gIgnoreQuality ) for (i <- 0 to Nobjects(ePOLLUT) )
//    fprintf(Frpt.file, " %9s", QualUnitsWords[ePollut(i).units])

    report_writeLine(LINE_64)
    if ( !gIgnoreQuality )
      for (i <- 0 to Nobjects(ePOLLUT) ) fprintf(Frpt.file, LINE_10)
  }


  //=============================================================================
  //      ERROR REPORTING
  //=============================================================================

  def report_writeErrorMsg( code:Int, s:String)=
    //
    //  Input:   code = error code
    //           s = error message text
    //  Output:  none
    //  Purpose: writes error message to report file.
    //
  {
    if ( Frpt.file  !=null)
    {
      report_writeLine("")
      //FIXME
 //     fprintf(Frpt.file, ErrorUtil.error_getMsg(code), s)
    }
    //FIXMI
 //   ErrorCode = code
  }

  //=============================================================================

  def report_writeErrorCode()
    //
    //  Input:   none
    //  Output:  none
    //  Purpose: writes error message to report file.
    //
  {
    if ( Frpt.file  !=null)
    {
      //FIXME:
//      if ( (ErrorCode >= ERR_MEMORY && ErrorCode <= ERR_TIMESTEP)
//        ||   (ErrorCode >= ERR_FILE_NAME && ErrorCode <= ERR_OUT_FILE)
//        ||   (ErrorCode == ERR_SYSTEM) )
//        fprintf(Frpt.file, ErrorUtil.error_getMsg(ErrorCode))
    }
  }

  //=============================================================================

  def report_writeInputErrorMsg( k:Int, sect:Int,  line:String,  lineCount:Long) =
    //
    //  Input:   k = error code
    //           sect = number of input data section where error occurred
    //           line = line of data containing the error
    //           lineCount = line number of data file containing the error
    //  Output:  none
    //  Purpose: writes input error message to report file.
    //
  {
    if ( Frpt.file  !=null)
    {
      //FIXME
//      report_writeErrorMsg(k, ErrString)
//      if ( sect < 0 ) fprintf(Frpt.file, FMT17, lineCount)
//      else            fprintf(Frpt.file, FMT18, lineCount, SectWords[sect])
      fprintf(Frpt.file, "\n  %s", line)
    }
  }

  //=============================================================================

  def report_writeWarningMsg( msg:String,  id:String) =
    //
    //  Input:   msg = text of warning message
    //           id = ID name of object that message refers to
    //  Output:  none
    //  Purpose: writes a warning message to the report file.
    //
  {
    fprintf(Frpt.file, "\n  %s %s", msg, id)
  }

  //=============================================================================

  def report_writeTseriesErrorMsg( code:Int,  tseries:TTable) =
    //
    //  Input:   tseries = pointer to a time series
    //  Output:  none
    //  Purpose: writes the date where a time series' data is out of order.
    //
  {
    var     theDate:String=""
    var     theTime:String=""
    var     x:DateTime=null 

    if (code == ERR_CURVE_SEQUENCE.ordinal())
    {
    // x = tseries.x2
      //FIXME:
//      datetime_dateToStr(x, theDate)
//      datetime_timeToStr(x, theTime)
//      report_writeErrorMsg(ERR_TIMESERIES_SEQUENCE, tseries.id)
      fprintf(Frpt.file, " at %s %s.", theDate, theTime)
    }
    else report_writeErrorMsg(code, tseries.id)
  }

}
