package swmm.configdata.jnodes.types;

// SUBCATCHMENT OBJECT

import com.udojava.evalex.Expression;

import swmm.configdata.types.TGage;
import swmm.runoff.infil.InfilT;

public class TSubcatch extends TBase {
 //   public String ID;              // subcatchment name
    public int rptFlag;         // reporting flag
    public TGage gage;            // raingage index
    public TNode outNode;         // outlet node index
    public TSubcatch outSubcatch;     // outlet subcatchment index
    public InfilT infil;           // infiltration object index
    public TSubarea[] subArea = new TSubarea[3];      // sub-area data
    public double width;           // overland flow width (ft)
    public double area;            // area (ft2)
    public double fracImperv;      // fraction impervious
    public double slope;           // slope (ft/ft)
    public double curbLength;      // total curb length (ft)
    public double initBuildup;     // initial pollutant buildup (mass/ft2)
    public TLandFactor landFactor;      // array of land use factors
    public TGroundwater groundwater;     // associated groundwater data
    public Expression gwLatFlowExpr;   // user-supplied lateral outflow expression  //(5.1.007)
    public Expression gwDeepFlowExpr;  // user-supplied deep percolation expression //(5.1.007)
    public TSnowpack snowpack;        // associated snow pack data

    public double lidArea;         // area devoted to LIDs (ft2)
    public double rainfall;        // current rainfall (ft/sec)
    public double evapLoss;        // current evap losses (ft/sec)
    public double infilLoss;       // current infil losses (ft/sec)
    public double runon;           // runon from other subcatchments (cfs)
    public double oldRunoff;       // previous runoff (cfs)
    public double newRunoff;       // current runoff (cfs)
    public double oldSnowDepth;    // previous snow depth (ft)
    public double newSnowDepth;    // current snow depth (ft)
    public double oldQual;         // previous runoff quality (mass/L)
    public double newQual;         // current runoff quality (mass/L)
    public double pondedQual;      // ponded surface water quality (mass)
    public double totalLoad;       // total washoff load (lbs or kg)

 

   
}



    