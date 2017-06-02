package swmm.configdata.jnodes.types;

// CUMULATIVE GROUNDWATER TOTALS

      public class      TGwaterTotals
    {                                 // All GW flux totals are in feet.
        public double        infil;           // surface infiltration
        public double        upperEvap;       // upper zone evaporation loss
        public double        lowerEvap;       // lower zone evaporation loss
        public double        lowerPerc;       // percolation out of lower zone
        public double        gwater;          // groundwater flow
        public double        initStorage;     // initial groundwater storage
        public double        finalStorage;    // final groundwater storage
        public double        pctError;        // continuity error (%)

 
    }


    