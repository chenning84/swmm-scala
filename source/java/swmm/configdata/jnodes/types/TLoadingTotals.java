package swmm.configdata.jnodes.types;

    // CUMULATIVE LOADING TOTALS
    
      public class      TLoadingTotals
    {                                 // All loading totals are in lbs.
        public double        initLoad;        // initial loading
        public double        buildup;         // loading added from buildup
        public double        deposition;      // loading added from wet deposition
        public double        sweeping;        // loading removed by street sweeping
        public double        bmpRemoval;      // loading removed by BMPs
        public double        infil;           // loading removed by infiltration
        public double        runoff;          // loading removed by runoff
        public double        finalLoad;       // final loading
        public double        pctError;        // continuity error (%)

 
    }


    