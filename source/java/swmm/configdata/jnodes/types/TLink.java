package swmm.configdata.jnodes.types;

// LINK OBJECT

public class TLink extends TBase {
    //public String ID;              // link ID
    //public int type;            // link type code
    public int subIndex;        // index of link's sub-category
    public int rptFlag;         // reporting flag
    public TBase node1;           // start node index
    public TBase node2;           // end node index
    public double offset1;         // ht. above start node invert (ft)
    public double offset2;         // ht. above end node invert (ft)
    public TXsect xsect;           // cross section data
    public double q0;              // initial flow (cfs)
    public double qLimit;          // constrapublic int on max. flow (cfs)
    public double cLossInlet;      // inlet loss coeff.
    public double cLossOutlet;     // outlet loss coeff.
    public double cLossAvg;        // avg. loss coeff.
    public double seepRate;        // seepage rate (ft/sec)
    public boolean hasFlapGate;     // true if flap gate present

    public double oldFlow;         // previous flow rate (cfs)
    public double newFlow;         // current flow rate (cfs)
    public double oldDepth;        // previous flow depth (ft)
    public double newDepth;        // current flow depth (ft)
    public double oldVolume;       // previous flow volume (ft3)
    public double newVolume;       // current flow volume (ft3)
    public double surfArea1;       // upstream surface area (ft2)
    public double surfArea2;       // downstream surface area (ft2)
    public double qFull;           // flow when full (cfs)
    public double setting;         // current control setting
    public double targetSetting;   // target control setting
    public double froude;          // Froude number
    public double oldQual;         // previous quality state
    public double newQual;         // current quality state
    public double totalLoad;       // total quality mass loading
    public int flowclass;       // flow public  class ification
    public double dqdh;            // change in flow w.r.t. head (ft2/sec)
    public char direction;       // flow direction flag
    public char bypassed;        // bypass dynwave calc. flag
    public char normalFlow;      // normal flow limited flag
    public char inletControl;    // culvert inlet control flag


}


    