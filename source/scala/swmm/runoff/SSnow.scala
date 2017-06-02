package swmm.runoff

import swmm.configdata.jnodes.{SwmmEnum, GlobalContext}
import swmm.configdata.jnodes.SwmmEnum.{UnitsType, ConversionType}
import swmm.configdata.jnodes.types.{TSubcatch, TSnowpack}
import swmm.runoff.SSnow.SnowKeywords
import swmm.util.FunctionUtil._
import swmm.util.Project


/**
 * Created by Ning  on 10/27/15.
  *
 */
object SSnow {
    //-----------------------------------------------------------------------------
    // Constants
    //-----------------------------------------------------------------------------
    // These symbolize the keywords listed in SnowmeltWords in keywords.c
 //   enum SnowKeywords {SNOW_PLOWABLE, SNOW_IMPERV, SNOW_PERV, SNOW_REMOVAL}
    object SnowKeywords extends Enumeration {
        type LidLayerTypes = Value
        val
        SNOW_PLOWABLE, SNOW_IMPERV, SNOW_PERV, SNOW_REMOVAL = Value
    }
  def snow_validateSnowmelt(i:Int)=
  {

  }
}
class SSnow {
  implicit val swmmContext = Project.getInstance

  def  snow_createSnowpack( j:Int,  k:Int):Boolean =
    //
    //  Input:   j = subcatchment index
    //           k = snow melt parameter set index
    //  Output:  returns TRUE if successful
    //  Purpose: creates a snowpack object for a subcacthment.
    //
    {
        val   snowpack = new TSnowpack()
        val subcatches =swmmContext.Subcatch
        subcatches(j).snowpack=snowpack
        snowpack.setSnowmeltIndex(k)
        return true
    }
    //=============================================================================

    def setMeltParams( j:Int,  k:Int,  x:Array[Double])=
    //
    //  Input:   j = snowmelt parameter set index
    //           k = data category index
    //           x = array of snow parameter values
    //  Output:  none
    //  Purpose: assigns values to parameters in a snow melt data set.
    //
    {
      val RAINFALL = ConversionType.RAINFALL.ordinal()
      val RAINDEPTH = ConversionType.RAINDEPTH.ordinal()
      val TEMPERATURE = ConversionType.TEMPERATURE.ordinal()
      val Snowmelt =swmmContext.Snowmelt
      val UnitSystem=swmmContext.UnitSystem
      val SI = UnitsType.SI.ordinal()
       // int i

        // --- snow pack melt parameters
        if ( k >=SnowKeywords.SNOW_PLOWABLE.id  && k <= SnowKeywords.SNOW_PERV.id )
        {
            // --- min/max melt coeffs.
            Snowmelt(j).dhmin(k)     = x(0) * UCF(TEMPERATURE) / UCF(RAINFALL)
            Snowmelt(j).dhmax(k)     = x(1) * UCF(TEMPERATURE) / UCF(RAINFALL)

            // --- base melt temp (deg F)
            Snowmelt(j).tbase(k)     = x(2)
            if ( UnitSystem == SI )
                Snowmelt(j).tbase(k) =  (9.0/5.0) * Snowmelt(j).tbase(k) + 32.0

            // --- free water fractions
            Snowmelt(j).fwfrac(k)    = x(3)

            // --- initial snow depth & free water depth
            Snowmelt(j).wsnow(k)     = x(4) / UCF(RAINDEPTH)
            x(5) = Math.min(x(5), (x(3)*x(4)))
            Snowmelt(j).fwnow(k)     = x(5) / UCF(RAINDEPTH)

            // --- fraction of impervious area that is plowable
            if ( k == SnowKeywords.SNOW_PLOWABLE.id ) Snowmelt(j).snn = x(6)

            // --- min. depth for 100% areal coverage on remaining
            //     impervious area or total pervious area
            else Snowmelt(j).si(k) = x(6) / UCF(RAINDEPTH)
        }

        // --- removal parameters
        else if ( k == SnowKeywords.SNOW_REMOVAL.id )
        {
            Snowmelt(j).weplow = x(0) / UCF(RAINDEPTH)
            for(i <- 1 to 4) Snowmelt(j).sfrac(i) = x(i +1)
            if ( x(6) >= 0.0 ) Snowmelt(j).toSubcatch = (x(6) + 0.01) toInt
            else               Snowmelt(j).toSubcatch = -1
        }
    }

}
