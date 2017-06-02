package swmm.runoff

import swmm.cmigrate.Keywords._
import swmm.cmigrate.SWMMText._
import swmm.configdata.jnodes.GlobalContext
import swmm.configdata.jnodes.SwmmEnum.EvapType._
import swmm.configdata.jnodes.SwmmEnum._
import swmm.configdata.jnodes.Errorh._
import swmm.configdata.jnodes.types.{TTable, TPattern, TBase}
import swmm.util.{Project, ErrorUtil}

/**
 * Created by ning on 11/2/15.
  *
 */
object  SClimate {
  val gInstance =Project.getInstance
//  val project = gInstance.project
  def climate_validate() =
  {

  }
  def climate_initState() =
    {

    }
  def climate_readEvapParams(tok:Array[String],  ntoks:Int):Int=
//
//  Input:   tok[] = array of string tokens
//           ntoks = number of tokens
//  Output:  returns error code
//  Purpose: reads evaporation parameters from input line of data.
//
//  Data formats are:
//    CONSTANT  value
//    MONTHLY   v1 ... v12
//    TIMESERIES name
//    TEMPERATURE
//    FILE      (v1 ... v12)
//    RECOVERY   name
//    DRY_ONLY   YES/NO
//
{
    var i:TPattern=null
    var x:Double= .0

    // --- find keyword indicating what form the evaporation data is in
    if (EvapTypeWords(tok(0)))
    {

    }


    // --- check for RECOVERY pattern data
    if (w_RECOVERY.equals(tok(0)) )
    {
        if ( ntoks ==2 )
          {
            i = gInstance.findTPattern(tok(1)).map(_!=null).asInstanceOf[TPattern]

          }

        gInstance.Evap.recoveryPattern = i
        return 0
    }

    // --- check for no evaporation in wet periods
    if (w_DRYONLY.equals(tok(0) ))
    {
        if ( ntoks < 2 ) return ErrorUtil.error_setInpError(ErrorType.ERR_ITEMS, "")
        if      (w_NO .equals(tok(1)) )  gInstance.Evap.dryOnly = false
        else if (  w_YES .equals(tok(1) )) gInstance.Evap.dryOnly = true
        else return ErrorUtil.error_setInpError(ErrorType.ERR_KEYWORD, tok(1))
        return 0
    }

    // --- process data depending on its form
  gInstance.Evap.sType =EvapType.valueOf(tok(0))
    if (  gInstance.Evap.sType!= TEMPERATURE_EVAP && ntoks < 2 )
        return ErrorUtil.error_setInpError(ErrorType.ERR_ITEMS, "")
  gInstance.Evap.sType match
    {
      case  CONSTANT_EVAP=>
        // --- for constant evap., fill monthly avg. values with same number
        val x = tok(1).toDouble
       
        for (i <- 0 to 11) gInstance.Evap.monthlyEvap(i) = x


      case  MONTHLY_EVAP=>
        // --- for monthly evap., read a value for each month of year
        if ( ntoks >=13 )
        for ( i<- 1 to 11)
          gInstance.Evap.monthlyEvap(i) = tok(i+1).toDouble


      case  TIMESERIES_EVAP=>
        // --- for time series evap., read name of time series
        val tt = gInstance.findTSeries(tok(1)).filter(_!=null).asInstanceOf[TTable]
       // if ( i < 0 ) return ErrorUtil.error_setInpError(ErrorType.ERR_NAME, tok(1))
        gInstance.Evap.tSeries = tt.pos
        gInstance.Tseries(tt.pos).refersTo = TIMESERIES_EVAP


      case  FILE_EVAP=>
        // --- for evap. from climate file, read monthly pan coeffs.
        //     if they are provided (default values are 1.0)
        if ( ntoks > 1)
        {
          // Strange Logic ???
            if ( ntoks >=13 ) 
            for (i<- 0 to 11)
            {
              gInstance.Evap.panCoeff(i) = tok(i+1).toDouble
            }
        }
       
    }
     0
}

}
class SClimate {

}
