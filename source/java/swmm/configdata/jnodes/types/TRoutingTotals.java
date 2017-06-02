package swmm.configdata.jnodes.types;

// CUMULATIVE ROUTING TOTALS

      public class      TRoutingTotals
    {                                  // All routing totals are in ft3.
        public double        dwInflow;         // dry weather inflow
        public double        wwInflow;         // wet weather inflow
        public double        gwInflow;         // groundwater inflow
        public double        iiInflow;         // RDII inflow
        public double        exInflow;         // direct inflow
        public double        flooding;         // internal flooding
        public double        outflow;          // external outflow
        public double        evapLoss;         // evaporation loss
        public double        seepLoss;         // seepage loss
        public double        reacted;          // reaction losses
        public double        initStorage;      // initial storage volume
        public double        finalStorage;     // final storage volume
        public double        pctError;         // continuity error

 
    }


    