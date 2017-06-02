package swmm.configdata.jnodes.types;


// AQUIFER OBJECT


public class TAquifer extends TBase {
 //   public String ID;               // aquifer name
    public double porosity;         // soil porosity
    public double wiltingPoint;     // soil wilting point
    public double fieldCapacity;    // soil field capacity
    public double conductivity;     // soil hyd. conductivity (ft/sec)
    public double conductSlope;     // slope of conductivity v. moisture curve
    public double tensionSlope;     // slope of tension v. moisture curve
    public double upperEvapFrac;    // evaporation available in upper zone
    public double lowerEvapDepth;   // evap depth existing in lower zone (ft)
    public double lowerLossCoeff;   // coeff. for losses to deep GW (ft/sec)
    public double bottomElev;       // elevation of bottom of aquifer (ft)
    public double waterTableElev;   // initial water table elevation (ft)
    public double upperMoisture;    // initial moisture content of unsat. zone
    public TPattern upperEvapPat;     // monthly upper evap. adjustment factors

}


////  Added to release 5.1.008.  ////                                          //(5.1.008)
