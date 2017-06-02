package swmm.runoff

import org.joda.time.DateTime
import swmm.cmigrate.{ SwmmConst, SWMMText, Keywords }
import swmm.configdata.jnodes.Errorh.ErrorType
import swmm.configdata.jnodes.types.TTable
import swmm.configdata.types.TGage
import swmm.util._
import swmm.configdata.jnodes.{ SwmmEnum, GlobalContext }
import swmm.configdata.jnodes.SwmmEnum.{ UnitsType, ConversionType, GageDataType, RainfallType }
import swmm.util.FunctionUtil.UCF

/**
 * Created by ning on 11/10/15.
 *
 *
 *
 */
object SGage {
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
  val TRUE: Int = 1
  val FALSE: Int = 0
  val OneSecond = 1.1574074e-5 // TODO: Need to further dicide the value
  val project = Project.getInstance

  //=============================================================================

  def gage_readParams( tok: List[String]): TGage =
    //
    //  Input:   j = rain gage index
    //           tok(] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns an error code
    //  Purpose: reads rain gage parameters from a line of input data
    //
    //  Data formats are:
    //    Name RainType RecdFreq SCF TIMESERIES SeriesName
    //    Name RainType RecdFreq SCF FILE FileName Station Units StartDate
    //
    {
      var k, err: Int = 0

      var fname: String = ""
      var staID: String = ""
      var x = new Array[Double](7)

      // --- check that gage exists
 
      val id = project.findGage(tok(0))
      if(id.isDefined)
        return id.asInstanceOf[TGage]



      // --- assign default parameter values
      x(0) = -1.0 // No time series index
      x(1) = 1.0 // Rain type is volume
      x(2) = 3600.0 // Recording freq. is 3600 sec
      x(3) = 1.0 // Snow catch deficiency factor
      x(4) = -1 // Default is no start/end date
      x(5) = -1
      x(6) = 0.0 // US units

      if (Keywords.GageDataWords(tok(4))) {
        if (k == GageDataType.RAIN_TSERIES.ordinal()) {
          err = readGageSeriesFormat(tok.toArray,  x)
        } else if (k == GageDataType.RAIN_FILE.ordinal()) {
        
          fname = tok(5)
          staID = tok(6)
          err = readGageFileFormat(tok.toArray,  x)
        }

      } 

      // --- save parameters to rain gage object
      var sGage = new TGage
      sGage.id = tok(0)
      sGage.tSeries = x(0).toInt
      sGage.rainType = x(1).toInt
      sGage.rainInterval = x(2).toInt
      sGage.snowFactor = x(3)
      sGage.rainUnits = x(6).toInt
      if (sGage.tSeries >= 0) sGage.dataSource = GageDataType.RAIN_TSERIES.name()
      else sGage.dataSource = GageDataType.RAIN_FILE.name()
      if (sGage.dataSource == "RAIN_FILE") {
        sGage.fname = fname
        sGage.staID = staID
        //FIXME
        //      sGage.startFileDate = x(4)
        //      sGage.endFileDate = x(5)
      }
      sGage.unitsFactor = 1.0
      sGage.coGage = -1
      sGage.isUsed = false
     sGage
    }

  //=============================================================================

  def readGageSeriesFormat(tok: Array[String],x: Array[Double]): Int =
    {
      var m, ts: Int = 0
      var aTime: DateTime = null

     

      // --- determine type of rain data
      var hasMatch = Keywords.RainTypeWords.contains(tok(1))
      
      if (!hasMatch) return ErrorUtil.error_setInpError(ErrorType.ERR_KEYWORD, tok(1))
      //FIXME
      x(1) = m

      // --- get data time interval & convert to seconds

      x(2) = tok(2).toDouble
      x(2) = Math.floor(x(2) * 3600 + 0.5)
      aTime = new DateTime(x(2).toInt)

      x(2) = Math.floor(aTime.getMillisOfSecond * SwmmConst.SECperDAY + 0.5)

      if (x(2) <= 0.0) return ErrorUtil.error_setInpError(ErrorType.ERR_DATETIME, tok(2))

      // --- get snow catch deficiency factor
      //    if ( !getDouble(tok(3), x(3)) )
      //    return ErrorUtil.error_setInpError(ErrorType.ERR_DATETIME, tok(3))
      x(3) = tok(3).toDouble
      // --- get time series index
      val ats = project.findTSeries(tok(5)).map(_!=null)
      if (ats ==None) return ErrorUtil.error_setInpError(ErrorType.ERR_NAME, tok(5))
      x(0) = ts
      tok(2) = ""
      0
    }

  //=============================================================================

  def readGageFileFormat(tok: Array[String],  x: Array[Double]): Int =
    {
      //var    u:Int =0
      var aDate: DateTime = null
      var aTime: DateTime = null
      // DateTime aTime

      // --- determine type of rain data
      val m = Keywords.RainTypeWords.contains(tok(1))

      if (!m) return ErrorUtil.error_setInpError(ErrorType.ERR_KEYWORD, tok(1))
      x(1) = if (m) 1 else 0

      // --- get data time interval & convert to seconds
      //  if ( getDouble(tok(2), x(2)) ) x(2) *= 3600
      x(2) = tok(2).toDouble * 3000.0
      aTime = new DateTime(tok(2))

      x(2) = Math.floor(aTime.getMillis * SwmmConst.SECperDAY + 0.5) toInt

      if (x(2) <= 0.0) return ErrorUtil.error_setInpError(ErrorType.ERR_DATETIME, tok(2))

      // --- get snow catch deficiency factor
      x(3) = tok(3) toDouble
      //    if ( !getDouble(tok(3), x(3)) )
      //    return ErrorUtil.error_setInpError(ErrorType.ERR_NUMBER.ordinal(), tok(3))

      // --- get rain depth units
      val has = Keywords.RainUnitsWords(tok(7))
      if (!has) return ErrorUtil.error_setInpError(ErrorType.ERR_KEYWORD, tok(7))
      x(6) = if (has) 1 else -1

      // --- get start date (if present)
      if ( tok(8) != '*') {
        aDate = new DateTime(tok(8))
        //FIXME
        // x(4) = aDate
      }
       0
    }

  //=============================================================================

  def gage_validate(j:Int) =
    //
    //  Input:   j = rain gage index
    //  Output:  none
    //  Purpose: checks for valid rain gage parameters
    //
    //  NOTE: assumes that any time series used by a rain gage has been
    //        previously validated.
    //
    {
      val gage =gInstance.Gage
      val sGage = gage(j)
      var i, k: Int = 0
      var gageInterval: Int = 0

      // --- for gage with time series data:
      if (sGage.dataSource == GageDataType.RAIN_TSERIES.ordinal) {
        // --- check gage's recording interval against that of time series
        k = sGage.tSeries

        if (tSeries(k).refersTo.ordinal()>= 0) {
          Report.report_writeErrorMsg(ErrorType.ERR_RAIN_GAGE_TSERIES.ordinal, sGage.id)
        }
        gageInterval = Math.floor(tSeries(k).dxMin * SwmmConst.SECperDAY + 0.5).toInt
        if (gageInterval > 0 && sGage.rainInterval > gageInterval) {
          Report.report_writeErrorMsg(ErrorType.ERR_RAIN_GAGE_INTERVAL.ordinal(), sGage.id)
        }
        if (sGage.rainInterval < gageInterval) {
          Report.report_writeWarningMsg(SWMMText.WARN09, sGage.id)
        }
        if (sGage.rainInterval < gInstance.WetStep) {
          Report.report_writeWarningMsg(SWMMText.WARN01, sGage.id)
          gInstance.WetStep = sGage.rainInterval
        }

        // --- see if gage uses same time series as another gage
        for (i <- 1 to j) {
          if (gage(i).dataSource.equals(GageDataType.RAIN_TSERIES.name) && gage(i).tSeries == k) {
            sGage.coGage = i

            // --- check that both gages record same type of data
            if (sGage.rainType != gage(i).rainType) {
              Report.report_writeErrorMsg(ErrorType.ERR_RAIN_GAGE_FORMAT.ordinal, sGage.id)
            }

          }
        }
      }
    }

  //=============================================================================

  def gage_initState( j:Int ):Int =
    //
    //  Input:   j = rain gage index
    //  Output:  none
    //  Purpose: initializes state of rain gage.
    //
    {
      // --- assume gage not used by any subcatchment
      //     (will be updated in subcatch_initState)a
      val gGages = gInstance.Gage
      val sGage = gGages(j)
      sGage.isUsed = false
      sGage.rainfall = 0.0
      sGage.reportRainfall = 0.0
      if (gIgnoreRainfall) return 0

      // --- for gage with file data:
      if (sGage.dataSource == GageDataType.RAIN_FILE) {
        // --- set current file position to start of period of record
        sGage.currentFilePos = sGage.startFilePos

        // --- assign units conversion factor
        //     (rain depths on interface file are in inches)
        if (gInstance.UnitSystem == UnitsType.SI) sGage.unitsFactor = SwmmConst.MMperINCH
      }

      // --- get first & next rainfall values
      if (getFirstRainfall(j) == 1) {
        // --- find date at end of starting rain interval
        sGage.endDate = sGage.startDate.plusSeconds(sGage.rainInterval)

        // --- if rainfall record begins after start of simulation,
        if (sGage.startDate.compareTo(gInstance.StartDateTime) > 0) {
          // --- make next rainfall date the start of the rain record
          sGage.nextDate = sGage.startDate
          sGage.nextRainfall = sGage.rainfall

          // --- make start of current rain interval the simulation start
          sGage.startDate = gInstance.StartDateTime
          sGage.endDate = sGage.nextDate
          sGage.rainfall = 0.0
        } // --- otherwise find next recorded rainfall
        else if (getNextRainfall(j) == 1) sGage.nextDate = null
      } else sGage.startDate = null
      1
    }

  //=============================================================================

  def gage_setState(sGage:TGage, t: DateTime): Unit =
    //
    //  Input:   j = rain gage index
    //           t = a calendar date/time
    //  Output:  none
    //  Purpose: updates state of rain gage for specified date. 
    //
    {
      val gage = gInstance.Gage
      // --- return if gage not used by any subcatchment
      if (sGage.isUsed == false) return

      // --- set rainfall to zero if disabled
      if (gIgnoreRainfall) {
        sGage.rainfall = 0.0
        return
      }

      // --- use rainfall from co-gage (gage with lower index that uses
      //     same rainfall time series or file) if it exists
      if (sGage.coGage >= 0) {
        sGage.rainfall = gage(sGage.coGage).rainfall
        return
      }

      // --- otherwise march through rainfall record until date t is bracketed

      t.plusSeconds(1)

      // FIXME:  TODO:
      //    for ()
      //    {
      //      // --- no rainfall if no interval start date
      //      if ( sGage.startDate == NO_DATE )
      //      {
      //        sGage.rainfall = 0.0
      //        return
      //      }
      //
      //      // --- no rainfall if time is before interval start date
      //      if ( t < sGage.startDate )
      //      {
      //        sGage.rainfall = 0.0
      //        return
      //      }
      //
      //      // --- use current rainfall if time is before interval end date
      //      if ( t < sGage.endDate )
      //      {
      //        return
      //      }
      //
      //      // --- no rainfall if t >= interval end date & no next interval exists
      //      if ( sGage.nextDate == NO_DATE )
      //      {
      //        sGage.rainfall = 0.0
      //        return
      //      }
      //
      //      // --- no rainfall if t > interval end date & <  next interval date
      //      if ( t < sGage.nextDate )
      //      {
      //        sGage.rainfall = 0.0
      //        return
      //      }
      //
      //      // --- otherwise update next rainfall interval date
      //      sGage.startDate = sGage.nextDate
      //      sGage.endDate = sGage.startDate.plusSeconds(sGage.rainInterval)
      //      sGage.rainfall = sGage.nextRainfall
      //      if ( !getNextRainfall(j) ) sGage.nextDate = NO_DATE
      //    }
    }

  //=============================================================================

  def gage_getNextRainDate(sGage:TGage, aDate: DateTime): DateTime =
    //
    //  Input:   j = rain gage index
    //           aDate = calendar date/time
    //  Output:  next date with rainfall occurring
    //  Purpose: finds the next date from  specified date when rainfall occurs.
    //
    {
      if (sGage.isUsed == false) return aDate
      aDate.plusSeconds(1)
      if (aDate.compareTo(sGage.startDate) < 0)
        return sGage.startDate
      if (aDate.compareTo(sGage.endDate) < 0)
        return sGage.endDate
      sGage.nextDate
    }

  //=============================================================================

  def gage_getPrecip(sGage:TGage, rainfall: Double, snowfall: Double): Double =
    //
    //  Input:   j = rain gage index
    //  Output:  rainfall = rainfall rate (ft/sec)
    //           snowfall = snow fall rate (ft/sec)
    //           returns total precipitation (ft/sec)
    //  Purpose: determines whether gage's recorded rainfall is rain or snow.
    //
    {
      var vrainfall = 0.0
      var snowfall = 0.0
      if (!gIgnoreSnowmelt && tTemp.ta <= tSnow.snotmp) {
        snowfall = sGage.rainfall * sGage.snowFactor / UCF(eRAINFALL)
      } else vrainfall = sGage.rainfall / UCF(eRAINFALL)
      vrainfall + snowfall
    }

  //=============================================================================

  def gage_setReportRainfall(sGage:TGage, reportDate: DateTime): Int =
    //
    //  Input:   j = rain gage index
    //           reportDate = date/time value of current reporting time
    //  Output:  none
    //  Purpose: sets the rainfall value reported at the current reporting time.
    //
    {
      var result: Double = .0

      // --- use value from co-gage if it exists
      if (sGage.coGage >= 0) {
        sGage.reportRainfall = gInstance.Gage(sGage.coGage).reportRainfall
        return 1
      }

      // --- otherwise increase reporting time by 1 second to avoid
      //     roundoff problems
      reportDate.plusSeconds(1)

      // --- use current rainfall if report date/time is before end
      //     of current rain interval
      // TODO: Double check me
      if (reportDate.compareTo(sGage.endDate) < 0) result = sGage.rainfall

      // --- use 0.0 if report date/time is before start of next rain interval
      // TODO: Double check me
      else if (reportDate.compareTo(sGage.nextDate) < 0) result = 0.0

      // --- otherwise report date/time falls right on end of current rain
      //     interval and start of next interval so use next interval's rainfall
      else result = sGage.nextRainfall
      sGage.reportRainfall = result
      1
    }

  //=============================================================================

  def getFirstRainfall(j:Int): Int =
    //
    //  Input:   j = rain gage index
    //  Output:  returns TRUE if successful
    //  Purpose: positions rainfall record to date with first rainfall.
    //
    {
      val gGages = gInstance.Gage
      val sGage = gGages(j)
      var k: Int = 0 // time series index
      var vFirst: Double = .0 // first rain volume (ft or m)
      var rFirst: Double = .0 // first rain intensity (in/hr or mm/hr)

      // --- assign default values to date & rainfall
      sGage.startDate = null
      sGage.rainfall = 0.0

      // --- initialize internal cumulative rainfall value
      sGage.rainAccum = 0

      // --- use rain interface file if applicable
      if (sGage.dataSource == GageDataType.RAIN_FILE) {
        if ((tFrain.file != null) && sGage.endFilePos > sGage.startFilePos) {
          // --- retrieve 1st date & rainfall volume from file
          //        fseek(tFrain.file, sGage.startFilePos, SEEK_SET)
          //        fread(sGage.startDate, sizeof(DateTime), 1, tFrain.file)
          //        fread(vFirst, sizeof(float), 1, tFrain.file)
          //        sGage.currentFilePos = ftell(tFrain.file)

          // --- convert rainfall to intensity
          sGage.rainfall = convertRainfall(j, vFirst)
          return 1
        }
        0
      } // --- otherwise access user-supplied rainfall time series
      else {
        k = sGage.tSeries
        if (k >= 0) {
          // --- retrieve first rainfall value from time series
          val newFirst =STable.table_getFirstEntry(tSeries(k), sGage.startDate, rFirst)
          if(newFirst._1 !=null){
   //       if (STable.table_getFirstEntry(tSeries(k), sGage.startDate, rFirst)) {
            // --- convert rainfall to intensity
            //TODO check
            sGage.rainfall = convertRainfall(j, newFirst._3)
            return 1
          }
        }
        0
      }
    }

  //=============================================================================

  def getNextRainfall(j:Int): Int =

    //
    //  Input:   j = rain gage index
    //  Output:  returns 1 if successful 0 if not
    //  Purpose: positions rainfall record to date with next non-zero rainfall
    //           while updating the gage's next rain intensity value.
    //
    //  Note: zero rainfall values explicitly entered into a rain file or
    //        time series are skipped over so that a proper accounting of
    //        wet and dry periods can be maintained.
    //
    {
      val gGages = gInstance.Gage
      val sGage = gGages(j)
      var k: Int = 0 // time series index
      var vNext: Double = .0 // next rain volume (ft or m)
      var rNext: Double = .0 // next rain intensity (in/hr or mm/hr)

      sGage.nextRainfall = 0.0
      do {
        if (sGage.dataSource == GageDataType.RAIN_FILE) {
          if ((tFrain.file != null) && sGage.currentFilePos < sGage.endFilePos) {
            //          fseek(tFrain.file, sGage.currentFilePos, SEEK_SET)
            //          fread(&sGage.nextDate, sizeof(DateTime), 1, tFrain.file)
            //          fread(&vNext, sizeof(float), 1, tFrain.file)
            //          sGage.currentFilePos = ftell(tFrain.file)
            //          rNext = convertRainfall(j, (double)vNext)
          } else return 0
        } else {
          k = sGage.tSeries
          if (k >= 0) {
            if (!STable.table_getNextEntry(tSeries(k), sGage.nextDate, rNext))
              return 0
            rNext = convertRainfall(j, rNext)
          } else return 0
        }
      } while (rNext == 0.0)
      sGage.nextRainfall = rNext
      1
    }

  //=============================================================================

  def convertRainfall(j:Int, r: Double): Double =
    //
    //  Input:   j = rain gage index
    //           r = rainfall value (user units)
    //  Output:  returns rainfall intensity (user units)
    //  Purpose: converts rainfall value to an intensity (depth per hour).
    //
    {
      val gGages = gInstance.Gage
      val sGage = gGages(j)
      var r1: Double = .0
      sGage.rainType match {
        case 0=> // RainfallType.RAINFALL_INTENSITY.ordinal =>
          r1 = r
        case 1=> // RainfallType.RAINFALL_VOLUME.ordinal =>
          r1 = r / sGage.rainInterval * 3600.0
        case 2 =>  //  RainfallType.CUMULATIVE_RAINFALL.ordinal =>
          if (r < sGage.rainAccum)
            r1 = r / sGage.rainInterval * 3600.0
          else r1 = (r - sGage.rainAccum) / sGage.rainInterval * 3600.0
          sGage.rainAccum = r
        case _ => r1 = r
      }
      r1 * sGage.unitsFactor * tAdjust.rainFactor //(5.1.007)
    }

}
