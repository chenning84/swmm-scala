package swmm.configdata.jnodes.types;


// OUTLET DEVICE OBJECT


import swmm.runoff.infil.TCurveNum;

public class TOutlet extends TLink {
    public Double qCoeff;          // discharge coeff.
    public Double qExpon;          // discharge exponent
    public TCurveNum qCurve;          // index of discharge rating curve
    public int curveType;       // rating curve type


}


    