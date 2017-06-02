package swmm.configdata.jnodes.types;


    // CUMULATIVE RUNOFF TOTALS
    
  
    public class      TRunoffTotals
    {                                 // All volume totals are in ft3.             //(5.1.008)
        public double        rainfall;        // rainfall volume
        public double        evap;            // evaporation loss
        public double        infil;           // infiltration loss
        public double        runoff;          // runoff volume
        public double        drains;          // LID drains                                //(5.1.008)
        public double        runon;           // runon from outfalls                       //(5.1.008)
        public double        initStorage;     // inital surface storage
        public double        finalStorage;    // final surface storage
        public double        initSnowCover;   // initial snow cover
        public double        finalSnowCover;  // final snow cover
        public double        snowRemoved;     // snow removal
        public double        pctError;        // continuity error (%)


    }


    