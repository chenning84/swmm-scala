package swmm.runoff

import swmm.cmigrate.Keywords
import swmm.configdata.jnodes.SwmmEnum
import swmm.configdata.jnodes.SwmmEnum.ConversionType
import swmm.configdata.jnodes.types.{TPattern, TAquifer}
import swmm.util.Project
import swmm.util.FunctionUtil.UCF

/**
  * Created by ning on 12/7/15.
  */
object SGWater {
  val gInstance = Project.getInstance

  val tSeries = gInstance.Tseries
  val tSnow = gInstance.Snow
  val tFrain = gInstance.Frain
  val tTemp = gInstance.Temp
  val tAdjust = gInstance.Adjust
  val NO_DATE = Keywords.NO_DATE

  val gIgnoreSnowmelt = gInstance.IgnoreSnowmelt
  val gIgnoreRainfall = gInstance.IgnoreRainfall


  val TRUE: Int = 1
  val FALSE: Int = 0
  val OneSecond = 1.1574074e-5 // TODO: Need to further dicide the value
  val project = Project.getInstance
  //=============================================================================

  def gwater_readAquiferParams( tok:Array[String]):TAquifer =
    //
    //  Input:   j = aquifer index
    //           tok[] = array of string tokens
    //           ntoks = number of tokens
    //  Output:  returns error message
    //  Purpose: reads aquifer parameter values from line of input data
    //
    //  Data line contains following parameters:
    //    ID, porosity, wiltingPoint, fieldCapacity,     conductivity,
    //    conductSlope, tensionSlope, upperEvapFraction, lowerEvapDepth,
    //    gwRecession,  bottomElev,   waterTableElev,    upperMoisture
    //    (evapPattern)
    //
  {
  // var p:Option[TPattern] =null
    val   x= new Array[Double](12)


    // --- check that aquifer exists
   // if ( ntoks < 13 ) return error_setInpError(ERR_ITEMS, "") 
    val id = project.project_findID("AQUIFER", tok(0)) 
   // if ( id == NULL ) return error_setInpError(ERR_NAME, tok(0)) 

    // --- read remaining tokens as numbers
  //  for (i = 0  i < 11  i++) x(i) = 0.0 
    for (i <- 1 to 12)
    {
      x(i-1)= tok(i).toDouble
     
    }

    // --- read upper evap pattern if present
  var ap:TPattern =null;
    if ( tok.size > 13 )
    {
     ap  = project.findTPattern(tok(13)).map(_!=null).asInstanceOf[TPattern]
   //   if ( p < 0 ) return error_setInpError(ERR_NAME, tok(13)) 
    }

    // --- assign parameters to aquifer object.
    val aquifer = new TAquifer

    aquifer.id = id.getOrElse("")
    aquifer.porosity       = x(0) 
    aquifer.wiltingPoint   = x(1) 
    aquifer.fieldCapacity  = x(2) 
    aquifer.conductivity   = x(3) / UCF(ConversionType.RAINFALL.ordinal) 
    aquifer.conductSlope   = x(4) 
    aquifer.tensionSlope   = x(5) / UCF(ConversionType.LENGTH.ordinal) 
    aquifer.upperEvapFrac  = x(6) 
    aquifer.lowerEvapDepth = x(7) / UCF(ConversionType.LENGTH.ordinal)
    aquifer.lowerLossCoeff = x(8) / UCF(ConversionType.RAINFALL.ordinal)
    aquifer.bottomElev     = x(9) / UCF(ConversionType.LENGTH.ordinal)
    aquifer.waterTableElev = x(10) / UCF(ConversionType.LENGTH.ordinal)
    aquifer.upperMoisture  = x(11) 
    aquifer.upperEvapPat   = ap
   aquifer
  }

}
