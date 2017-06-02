package swmm

import org.joda.time.DateTime

import swmm.cmigrate._
import swmm.configdata.ConfigObject
import swmm.configdata.jnodes.GlobalContext
import swmm.runoff.SGage
import swmm.util.{InputParse, InputReport, Project, Report}

/**
  * Created by ning on 5/27/15.
  *
  */
object Swmm5 {

  def main(args: Array[String]) {
    //     val inputPars = new swmm.util.InputParse("/home/trevor/code/aws/swmm-scala/data/zsection/subarea.inp")
    //     val inputPars = new swmm.util.InputParse("/home/trevor/code/aws/swmm-scala/data/zsection/subarea.inp")
    //val inputPars = new swmm.util.InputParse("/home/ning/sdcard/code/swmm-scala/data/zsection/evaporation.inp")
    val inputPars = new InputParse("/Users/v429798/newcode/swmm-scala/data/user5/user5.inp")
  //  val inputPars = new InputParse("/home/ning/sdcard/code/swmm-scala/data/user5/user5.inp")
    val gInstance = Project.getInstance
    val project = gInstance


    val content = inputPars.parseFile()
    val section = inputPars.divideSection(content)
    if (section.nonEmpty)
      println("hwat hup")
    val names = section filter (_.nonEmpty) map (inputPars.parseSection(_))
    val configObjects = names.map(_.initSectionData)
    val optionNode = configObjects.filter(_.name.equalsIgnoreCase("OPTIONS"))
    if(optionNode.size ==1){
      project.readOptionSection(optionNode.head.dat)
    }
    val  gageSection = configObjects.map(_.parseData())
    println("end ")

   // project.project_readOption()
//    if (configObjects != Nil) {
//      val inflowObject = configObjects.head.callJavaNode()
//      println("The java object " + inflowObject.getBaseline)
//    }


  }
}

class Swmm5 {
 // val log = Logging(context.system, classOf[MyActor])
  val gInstance = Project.getInstance
  val project =gInstance
 // val report = new Report
  val inputReport = new InputReport
//  def initObject(configObject:ConfigObject)=configObject.name match
//    {
//    case "RAINGAGES" =>
//      gInstance.Gage=(configObject.dat.map(SGage.gage_readParams(_))).toArray
//  }
  def swmm_run(f1: String, f2: String, f3: String) =
  //
  //  Input:   f1 = name of input file
  //           f2 = name of report file
  //           f3 = name of binary output file
  //  Output:  returns error code
  //  Purpose: runs a SWMM simulation.
  //
  {
    var newHour, oldHour: Long = 0
    var theDay, theHour: Long = 0
    var elapsedTime: DateTime = null

    // --- open the files & read input data
    gInstance.ErrorCode = 0
    swmm_open(f1, f2, f3)
    //
    //    // --- run the simulation if input data OK
    //    if ( !ErrorCode )
    //    {
    //        // --- initialize values
    //        swmm_start(TRUE)
    //
    //        // --- execute each time step until elapsed time is re-set to 0
    //        if ( !ErrorCode )
    //        {
    //            writecon("\n o  Simulating day: 0     hour:  0")
    //            do
    //            {
    //                swmm_step(&elapsedTime)
    //                newHour = (long)(elapsedTime * 24.0)
    //                if ( newHour > oldHour )
    //                {
    //                    theDay = (long)elapsedTime
    //                    theHour = (long)((elapsedTime - floor(elapsedTime)) * 24.0)
    //                    writecon("\b\b\b\b\b\b\b\b\b\b\b\b\b\b")
    //                    sprintf(Msg, "%-5d hour: %-2d", theDay, theHour)
    //                    writecon(Msg)
    //                    oldHour = newHour
    //                }
    //            } while ( elapsedTime > 0.0 && !ErrorCode )
    //            writecon("\b\b\b\b\b\b\b\b\b\b\b\b\b\b"+
    //                     "\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b")
    //            writecon("Simulation complete           ")
    //        }
    //
    //        // --- clean up
    //        swmm_end()
    //    }
    //
    //    // --- report results
    //    if ( Fout.mode == SCRATCH_FILE ) swmm_report()
    //
    //    // --- close the system
    //    swmm_close()
    //     ErrorCode
  }

  //=============================================================================

  //  int DLLEXPORT swmm_open(char* f1, char* f2, char* f3)
  def swmm_open(f1: String, f2: String, f3: String): Int =
  //
  //  Input:   f1 = name of input file
  //           f2 = name of report file
  //           f3 = name of binary output file
  //  Output:  returns error code
  //  Purpose: opens a SWMM project.
  //
  {

    {
      // --- initialize error & warning codes
      //datetime_setDateFormat(M_D_Y)
      gInstance.ErrorCode = 0
      gInstance.WarningCode = 0
      //      gInstance.IsOpenFlag = FALSE
      //      gInstance.IsStartedFlag = FALSE
      //      gInstance. ExceptionCount = 0

      // --- open a SWMM project
      project.project_open(f1, f2, f3)
      if(gInstance.ErrorCode >0)
         return gInstance.ErrorCode
    //  .IsOpenFlag = TRUE
      Report.report_writeLogo()
      printf(SWMMText.FMT06)

      // --- retrieve project data from input file
      project.project_readInput()
      if (gInstance.ErrorCode>0) return gInstance.ErrorCode

      // --- write project title to report file & validate data
      Report.report_writeTitle()
      project.project_validate()

      // --- write input summary to report file if requested
      if (gInstance.RptFlags.input==1) inputReport.inputrpt_writeInput()
    }


    return gInstance.ErrorCode
    1
  }

  //=============================================================================

  //  int DLLEXPORT swmm_start(int saveResults)
  def swmm_start(saveResults: Int): Int =
  //
  //  Input:   saveResults = TRUE if simulation results saved to binary file
  //  Output:  returns an error code
  //  Purpose: starts a SWMM simulation.
  //
  {
    //    // --- check that a project is open & no run started
    //    if ( ErrorCode ) return ErrorCode
    //    if ( !IsOpenFlag || IsStartedFlag )
    //    {
    //        report_writeErrorMsg(ERR_NOT_OPEN, "")
    //        return ErrorCode
    //    }
    //    ExceptionCount = 0
    //
    //
    //    {
    //        // --- initialize runoff, routing & reporting time (in milliseconds)
    //        NewRunoffTime = 0.0
    //        NewRoutingTime = 0.0
    //        ReportTime =   (double)(1000 * ReportStep)
    //        StepCount = 0
    //        NonConvergeCount = 0
    //        IsStartedFlag = TRUE
    //
    //        // --- initialize global continuity errors
    //        RunoffError = 0.0
    //        GwaterError = 0.0
    //        FlowError = 0.0
    //        QualError = 0.0
    //
    //        // --- open rainfall processor (creates/opens a rainfall
    //        //     interface file and generates any RDII flows)
    //        if ( !IgnoreRainfall ) rain_open()
    //        if ( ErrorCode ) return ErrorCode
    //
    //        // --- initialize state of each major system component
    //        project_init()
    //
    //        // --- see if runoff & routing needs to be computed
    //        if ( Nobjects[SUBCATCH] > 0 ) DoRunoff = TRUE
    //        else DoRunoff = FALSE
    //        if ( Nobjects[NODE] > 0 && !IgnoreRouting ) DoRouting = TRUE
    //        else DoRouting = FALSE
    //
    //////  Following section modified for release 5.1.008.  ////                    //(5.1.008)
    //////
    //        // --- open binary output file
    //        output_open()
    //
    //        // --- open runoff processor
    //        if ( DoRunoff ) runoff_open()
    //
    //        // --- open & read hot start file if present
    //        if ( !hotstart_open() ) return ErrorCode
    //
    //        // --- open routing processor
    //        if ( DoRouting ) routing_open()
    //
    //        // --- open mass balance and statistics processors
    //        massbal_open()
    //        stats_open()
    //
    //        // --- write project options to report file
    //	    report_writeOptions()
    //        if ( RptFlags.controls ) report_writeControlActionsHeading()
    //////
    //    }
    //
    //
    //
    //    // --- save saveResults flag to global variable
    //    SaveResultsFlag = saveResults
    //    return ErrorCode
    1
  }

  //=============================================================================

  //  int DLLEXPORT swmm_step(DateTime* elapsedTime)
  def swmm_step(elapsedTime: DateTime): Int =
  //
  //  Input:   elapsedTime = current elapsed time in decimal days
  //  Output:  updated value of elapsedTime,
  //           returns error code
  //  Purpose: advances the simulation by one routing time step.
  //
  {
    //    // --- check that simulation can proceed
    //    if ( ErrorCode ) return ErrorCode
    //    if ( !IsOpenFlag || !IsStartedFlag  )
    //    {
    //        report_writeErrorMsg(ERR_NOT_OPEN, "")
    //        return ErrorCode
    //    }
    //
    //
    //    {
    //        // --- if routing time has not exceeded total duration
    //        if ( NewRoutingTime < TotalDuration )
    //        {
    //            // --- route flow & WQ through drainage system
    //            //     (runoff will be calculated as needed)
    //            //     (NewRoutingTime is updated)
    //            execRouting(*elapsedTime)
    //        }
    //
    //        // --- save results at next reporting time
    //        if ( NewRoutingTime >= ReportTime )
    //        {
    //            if ( SaveResultsFlag ) output_saveResults(ReportTime)
    //            ReportTime = ReportTime + (double)(1000 * ReportStep)
    //        }
    //
    //        // --- update elapsed time (days)
    //        if ( NewRoutingTime < TotalDuration )
    //        {
    //            *elapsedTime = NewRoutingTime / MSECperDAY
    //        }
    //
    //        // --- otherwise end the simulation
    //        else *elapsedTime = 0.0
    //    }
    //
    //
    //
    //     ErrorCode
    1
  }

  //=============================================================================

  //void execRouting(DateTime elapsedTime)
  def execRouting(elapsedTime: DateTime) =
  //
  //  Input:   elapsedTime = current elapsed time in decimal days
  //  Output:  none
  //  Purpose: routes flow & WQ through drainage system over a single time step.
  //
  {
    //    double   nextRoutingTime          // updated elapsed routing time (msec)
    //    double   routingStep              // routing time step (sec)
    //
    //
    //
    //    {
    //        // --- determine when next routing time occurs
    //        StepCount++
    //        if ( !DoRouting ) routingStep = MIN(WetStep, ReportStep)
    //        else routingStep = routing_getRoutingStep(RouteModel, RouteStep)
    //        if ( routingStep <= 0.0 )
    //        {
    //            ErrorCode = ERR_TIMESTEP
    //            return
    //        }
    //        nextRoutingTime = NewRoutingTime + 1000.0 * routingStep
    //
    //////  Following section added to release 5.1.008.  ////                        //(5.1.008)
    //////
    //        // --- adjust routing step so that total duration not exceeded
    //        if ( nextRoutingTime > TotalDuration )
    //        {
    //            routingStep = (TotalDuration - NewRoutingTime) / 1000.0
    //            routingStep = MAX(routingStep, 1./1000.0)
    //            nextRoutingTime = TotalDuration
    //        }
    //////
    //
    //        // --- compute runoff until next routing time reached or exceeded
    //        if ( DoRunoff ) while ( NewRunoffTime < nextRoutingTime )
    //        {
    //            runoff_execute()
    //            if ( ErrorCode ) return
    //        }
    //
    //        // --- if no runoff analysis, update climate state (for evaporation)
    //        else climate_setState(getDateTime(NewRoutingTime))
    //
    //        // --- route flows & pollutants through drainage system                //(5.1.008)
    //        //     (while updating NewRoutingTime)                                 //(5.1.008)
    //       // printf("do routing from swmm5\n")
    //        if ( DoRouting ) routing_execute(RouteModel, routingStep)
    //        else NewRoutingTime = nextRoutingTime
    //    }
    //

  }

  //=============================================================================

  //  int DLLEXPORT swmm_end(void)
  def swmm_end(): Int =
  //
  //  Input:   none
  //  Output:  none
  //  Purpose: ends a SWMM simulation.
  //
  {
    //    // --- check that project opened and run started
    //    if ( !IsOpenFlag )
    //    {
    //        report_writeErrorMsg(ERR_NOT_OPEN, "")
    //        return ErrorCode
    //    }
    //
    //    if ( IsStartedFlag )
    //    {
    //        // --- write ending records to binary output file
    //        if ( Fout.file ) output_end()
    //
    //        // --- report mass balance results and system statistics
    //        if ( !ErrorCode )
    //        {
    //            massbal_report()
    //            stats_report()
    //        }
    //
    //        // --- close all computing systems
    //        stats_close()
    //        massbal_close()
    //        if ( !IgnoreRainfall ) rain_close()
    //        if ( DoRunoff ) runoff_close()
    //        if ( DoRouting ) routing_close(RouteModel)
    //        hotstart_close()
    //        IsStartedFlag = FALSE
    //    }
    //    return ErrorCode
    1
  }

  //=============================================================================

  //  int DLLEXPORT swmm_report()
  def swmm_reposrt() =
  //
  //  Input:   none
  //  Output:  returns an error code
  //  Purpose: writes simulation results to report file.
  //
  {
    //    if ( Fout.mode == SCRATCH_FILE ) output_checkFileSize()
    //    if ( ErrorCode ) report_writeErrorCode()
    //    else
    //    {
    //        writecon(FMT07)
    //        report_writeReport()
    //    }
    //     ErrorCode
  }

  //=============================================================================

  // int DLLEXPORT swmm_close()
  def swmm_close() =
  //
  //  Input:   none
  //  Output:  returns an error code
  //  Purpose: closes a SWMM project.
  //
  {
    //    if ( Fout.file ) output_close()
    //    if ( IsOpenFlag ) project_close()
    //    report_writeSysTime()
    //    if ( Finp.file != NULL ) fclose(Finp.file)
    //    if ( Frpt.file != NULL ) fclose(Frpt.file)
    //    if ( Fout.file != NULL )
    //    {
    //        fclose(Fout.file)
    //        if ( Fout.mode == SCRATCH_FILE ) remove(Fout.name)
    //    }
    //    IsOpenFlag = FALSE
    //    IsStartedFlag = FALSE
    //    return 0
  }

  //=============================================================================

  //  int  DLLEXPORT swmm_getMassBalErr(float* runoffErr, float* flowErr,
  //                                  float* qualErr)
  def swmm_getMassBalErr(runoffErr: Float, flowErr: Float, qualErr: Float) =
  //
  //  Input:   none
  //  Output:  runoffErr = runoff mass balance error (percent)
  //           flowErr   = flow routing mass balance error (percent)
  //           qualErr   = quality routing mass balance error (percent)
  //           returns an error code
  //  Purpose: reports a simulation's mass balance errors.
  //
  {
    //    *runoffErr = 0.0
    //    *flowErr   = 0.0
    //    *qualErr   = 0.0
    //
    //    if ( IsOpenFlag && !IsStartedFlag)
    //    {
    //        *runoffErr = (float)RunoffError
    //        *flowErr   = (float)FlowError
    //        *qualErr   = (float)QualError
    //    }
    //    return 0
  }

  //=============================================================================

  // int  DLLEXPORT swmm_getVersion(void)
  def swmm_getVersion() =
  //
  //  Input:   none
  //  Output:  returns SWMM engine version number
  //  Purpose: retrieves version number of current SWMM engine which
  //           uses a format of xyzzz where x = major version number,
  //           y = minor version number, and zzz = build number.
  //
  {
     SwmmConst.VERSION
  }


  //=============================================================================
  //   General purpose functions
  //=============================================================================

  def UCF(u: Int): Double =
  //
  //  Input:   u = integer code of quantity being converted
  //  Output:  returns a units conversion factor
  //  Purpose: computes a conversion factor from SWMM's internal
  //           units to user's units
  //
  {
    //    if ( u < FLOW ) return Ucf[u][UnitSystem]
    //    else            return Qcf[FlowUnits]
    2.1
  }

  //=============================================================================

  //  char* sstrncpy(char *dest, const char *src, size_t maxlen)
  ////
  ////  Input:   dest = string to be copied to
  ////           src = string to be copied from
  ////           maxlen = number of characters to copy
  ////  Output:  returns a pointer to dest
  ////  Purpose: safe version of standard strncpy function
  ////
  //{
  //     strncpy(dest, src, maxlen)
  //     dest[maxlen] = '\0'
  //     return dest
  //}

  //=============================================================================
  //
  //  int  strcomp(char *s1, char *s2)
  ////
  ////  Input:   s1 = a character string
  ////           s2 = a character string
  ////  Output:  returns 1 if s1 is same as s2, 0 otherwise
  ////  Purpose: does a case insensitive comparison of two strings.
  ////
  //{
  //    int i
  //    for (i = 0 UCHAR(s1[i]) == UCHAR(s2[i]) i++)
  //    {
  //        if (!s1[i+1] && !s2[i+1]) return(1)
  //    }
  //    return(0)
  //}

  //  //=============================================================================
  //
  //  char* getTempFileName(char* fname)
  ////
  ////  Input:   fname = file name string (with max size of MAXFNAME)
  ////  Output:  returns pointer to file name
  ////  Purpose: creates a temporary file name with path prepended to it.
  ////
  //{
  //
  //
  //    // --- use system function mkstemp() to create a temporary file name
  //    strcpy(fname, "swmmXXXXXX")
  //    mkstemp(fname)
  //    return fname
  //
  //}
  //
  //  //=============================================================================
  //
  //  void getElapsedTime(DateTime aDate, int* days, int* hrs, int* mins)
  ////
  ////  Input:   aDate = simulation calendar date + time
  ////  Output:  days, hrs, mins = elapsed days, hours & minutes for aDate
  ////  Purpose: finds elapsed simulation time for a given calendar date
  ////
  //{
  //    DateTime x
  //    int secs
  //    x = aDate - StartDateTime
  //    if ( x <= 0.0 )
  //    {
  //        *days = 0
  //        *hrs  = 0
  //        *mins = 0
  //    }
  //    else
  //    {
  //        *days = (int)x
  //        datetime_decodeTime(x, hrs, mins, &secs)
  //    }
  //}
  //
  //  //=============================================================================
  //
  //  DateTime getDateTime(double elapsedMsec)
  ////
  ////  Input:   elapsedMsec = elapsed milliseconds
  ////  Output:  returns date/time value
  ////  Purpose: finds calendar date/time value for elapsed milliseconds of
  ////           simulation time.
  ////
  //{
  //    return datetime_addSeconds(StartDateTime, (elapsedMsec+1)/1000.0)
  //}
  //
  //  //=============================================================================
  //
  //  void  writecon(char *s)
  ////
  ////  Input:   s = a character string
  ////  Output:  none
  ////  Purpose: writes string of characters to the console.
  ////
  //
  //
  //  //=============================================================================
  //
  ////
  ////  Input:   xc          = exception code
  ////           elapsedTime = simulation time when exception occurred (days)
  ////           step        = step count at time when exception occurred
  ////  Output:  returns an exception handling code
  ////  Purpose: exception filtering routine for operating system exceptions
  ////           under Windows.
  ////
  //{
  //    int  rc                           // result code
  //    long hour                         // current hour of simulation
  //    char msg[40]                      // exception type text
  //    char xmsg[120]                    // error message text
  //    switch (xc)
  //    {
  //    case EXCEPTION_ACCESS_VIOLATION:
  //        sprintf(msg, "\n  Access violation ")
  //        rc = EXCEPTION_EXECUTE_HANDLER
  //        break
  //    case EXCEPTION_FLT_DENORMAL_OPERAND:
  //        sprintf(msg, "\n  Illegal floating point operand ")
  //        rc = EXCEPTION_CONTINUE_EXECUTION
  //        break
  //    case EXCEPTION_FLT_DIVIDE_BY_ZERO:
  //        sprintf(msg, "\n  Floating point divide by zero ")
  //        rc = EXCEPTION_CONTINUE_EXECUTION
  //        break
  //    case EXCEPTION_FLT_INVALID_OPERATION:
  //        sprintf(msg, "\n  Illegal floating point operation ")
  //        rc = EXCEPTION_CONTINUE_EXECUTION
  //        break
  //    case EXCEPTION_FLT_OVERFLOW:
  //        sprintf(msg, "\n  Floating point overflow ")
  //        rc = EXCEPTION_CONTINUE_EXECUTION
  //        break
  //    case EXCEPTION_FLT_STACK_CHECK:
  //        sprintf(msg, "\n  Floating point stack violation ")
  //        rc = EXCEPTION_EXECUTE_HANDLER
  //        break
  //    case EXCEPTION_FLT_UNDERFLOW:
  //        sprintf(msg, "\n  Floating point underflow ")
  //        rc = EXCEPTION_CONTINUE_EXECUTION
  //        break
  //    case EXCEPTION_INT_DIVIDE_BY_ZERO:
  //        sprintf(msg, "\n  Integer divide by zero ")
  //        rc = EXCEPTION_CONTINUE_EXECUTION
  //        break
  //    case EXCEPTION_INT_OVERFLOW:
  //        sprintf(msg, "\n  Integer overflow ")
  //        rc = EXCEPTION_CONTINUE_EXECUTION
  //        break
  //    default:
  //        sprintf(msg, "\n  Exception %d", xc)
  //        rc = EXCEPTION_EXECUTE_HANDLER
  //    }
  //    hour = (long)(elapsedTime / 1000.0 / 3600.0)
  //    sprintf(xmsg, "%s at step %d, hour %d", msg, step, hour)
  //    if ( rc == EXCEPTION_EXECUTE_HANDLER ||
  //         ++ExceptionCount >= MAX_EXCEPTIONS )
  //    {
  //        strcat(xmsg, " --- execution halted.")
  //        rc = EXCEPTION_EXECUTE_HANDLER
  //    }
  //    report_writeLine(xmsg)
  //    return rc
  //}
  //
}
