package swmm.configdata.jnodes.types;

// NODE OBJECT

  public class TNode  extends TBase
{
//    public String         ID;              // node ID
//    public   type;            // node type code
    public int            subIndex;        // index of node's sub-category
    public int           rptFlag;         // reporting flag
    public double        invertElev;      // invert elevation (ft)
    public double        initDepth;       // initial storage level (ft)
    public double        fullDepth;       // dist. from invert to surface (ft)
    public double        surDepth;        // added depth under surcharge (ft)
    public double        pondedArea;      // area filled by ponded water (ft2)
    public TExtInflow   extInflow;       // popublic int er to external inflow data
    public TDwfInflow   dwfInflow;       // popublic int er to dry weather flow inflow data
    public TRdiiInflow  rdiiInflow;      // popublic int er to RDII inflow data
    public TTreatment   treatment;       // array of treatment data

    public int            degree;          // number of outflow links
    char          updated;         // true if state has been updated
    public double        crownElev;       // top of highest connecting conduit (ft)
    public double        inflow;          // total inflow (cfs)
    public double        outflow;         // total outflow (cfs)
    public double        losses;          // evap + exfiltration loss (ft3);           //(5.1.007)
    public double        oldVolume;       // previous volume (ft3)
    public double        newVolume;       // current volume (ft3)
    public double        fullVolume;      // max. storage available (ft3)
    public double        overflow;        // overflow rate (cfs)
    public double        oldDepth;        // previous water depth (ft)
    public double        newDepth;        // current water depth (ft)
    public double        oldLatFlow;      // previous lateral inflow (cfs)
    public double        newLatFlow;      // current lateral inflow (cfs)
    public double        oldQual;         // previous quality state
    public double        newQual;         // current quality state
    public double        oldFlowInflow;   // previous flow inflow
    public double        oldNetInflow;    // previous net inflow

}


