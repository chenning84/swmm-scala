package swmm.configdata.jnodes.types;


// CONDUIT OBJECT


public    class      TConduit extends TLink
    {
        public double        length;          // conduit length (ft)
        public double        roughness;       // Manning's n
        public int          barrels;         // number of barrels

        public double        modLength;       // modified conduit length (ft)
        public double        roughFactor;     // roughness factor for DW routing
        public double        slope;           // slope
        public double        beta;            // discharge factor
        public double        qMax;            // max. flow (cfs)
        public double        a1, a2;          // upstream & downstream areas (ft2)
        public double        q1, q2;          // upstream & downstream flows per barrel (cfs)
// public double        q1Old, q2Old;    // (deprecated)                              //(5.1.008)
        public double        evapLossRate;    // evaporation rate (cfs)
        public double        seepLossRate;    // seepage rate (cfs)
        public String          capacityLimited; // capacity limited flag
        public String          superCritical;   // super-critical flow flag
        public String          hasLosses;       // local losses flag
        public String          fullState;       // determines if either or both ends full    //(5.1.008)

    
    }


    