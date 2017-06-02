package swmm.configdata.jnodes.types;


// GROUNDWATER STATISTICS

  
    public class      TGWaterStats
    {
        double       infil;           // total infiltration (ft)
        double       evap;            // total evaporation (ft)
        double       latFlow;         // total lateral outflow (ft)
        double       deepFlow;        // total flow to deep aquifer (ft)
        double       avgUpperMoist;   // avg. upper zone moisture
        double       finalUpperMoist; // final upper zone moisture
        double       avgWaterTable;   // avg. water table height (ft)
        double       finalWaterTable; // final water table height (ft)
        double       maxFlow;         // max. lateral outflow (cfs)

        public TGWaterStats(double infil, double evap, double latFlow, double deepFlow, double avgUpperMoist,
                            double finalUpperMoist, double avgWaterTable, double finalWaterTable, double maxFlow) {
            this.infil = infil;
            this.evap = evap;
            this.latFlow = latFlow;
            this.deepFlow = deepFlow;
            this.avgUpperMoist = avgUpperMoist;
            this.finalUpperMoist = finalUpperMoist;
            this.avgWaterTable = avgWaterTable;
            this.finalWaterTable = finalWaterTable;
            this.maxFlow = maxFlow;
        }
    }


    