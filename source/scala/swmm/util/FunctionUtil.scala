package swmm.util

import swmm.configdata.jnodes.GlobalContext
import swmm.configdata.jnodes.SwmmEnum.ConversionType

/**
  * Created by ning on 11/5/15.
  */
object FunctionUtil {
  val gContext = Project.getInstance
  val flowTypeValue = ConversionType.values().length

  //-----------------------------------------------------------------------------
  //  Unit conversion factors
  //-----------------------------------------------------------------------------
  val Ucf =new Array[Array[Double]](10)
  ( //  US      SI
    Ucf(0) = Array(43200.0, 1097280.0), // RAINFALL (in/hr, mm/hr --> ft/sec)
    Ucf(1) =Array(12.0, 304.8), // RAINDEPTH (in, mm --> ft)
    Ucf(2) =Array(1036800.0, 26334720.0), // EVAPRATE (in/day, mm/day --> ft/sec)
    Ucf(3) =Array(1.0, 0.3048), // LENGTH (ft, m --> ft)
    Ucf(4) =Array(2.2956e-5, 0.92903e-5), // LANDAREA (ac, ha --> ft2)
    Ucf(5) =Array(1.0, 0.02832), // VOLUME (ft3, m3 --> ft3)
    Ucf(6) =Array(1.0, 1.608), // WINDSPEED (mph, km/hr --> mph)
    Ucf(7) =Array(1.0, 1.8), // TEMPERATURE (deg F, deg C --> deg F)
    Ucf(8) =Array(2.203e-6, 1.0e-6), // MASS (lb, kg --> mg)
    Ucf(9) =Array(43560.0, 3048.0) // GWFLOW (cfs/ac, cms/ha --> ft/sec)
    )

  val Qcf = Array(// Flow Conversion Factors:
    1.0, 448.831, 0.64632, // cfs, gpm, mgd --> cfs
    0.02832, 28.317, 2.4466)

  // cms, lps, mld --> cfs
  def UCF(u: Int): Double =
  //
  //  Input:   u = integer code of quantity being converted
  //  Output:  returns a units conversion factor
  //  Purpose: computes a conversion factor from SWMM's internal
  //           units to user's units
  //
  {
    val sysId =gContext.UnitSystem.ordinal()
    if (u < flowTypeValue) Ucf(u)(sysId)
    else Qcf(gContext.FlowUnits.ordinal())
  }


}
