package swmm.configdata.jnodes.types;

// GROUNDWATER OBJECT

      public class     TGroundwater
    {
        int           aquifer;        // index of associated gw aquifer
        int           node;           // index of node receiving gw flow
        double        surfElev;       // elevation of ground surface (ft)
        double        a1, b1;         // ground water outflow coeff. & exponent
        double        a2, b2;         // surface water outflow coeff. & exponent
        double        a3;             // surf./ground water interaction coeff.
        double        fixedDepth;     // fixed surface water water depth (ft)
        double        nodeElev;       // elevation of receiving node invert (ft)
        double        bottomElev;     // bottom elevation of lower GW zone (ft)
        double        waterTableElev; // initial water table elevation (ft)
        double        upperMoisture;  // initial moisture content of unsat. zone

        double        theta;          // upper zone moisture content
        double        lowerDepth;     // depth of saturated zone (ft)
        double        oldFlow;        // gw outflow from previous time period (cfs)
        double        newFlow;        // gw outflow from current time period (cfs)
        double        evapLoss;       // evaporation loss rate (ft/sec)
        double        maxInfilVol;    // max. infil. upper zone can accept (ft)
        TGWaterStats  stats;          // gw statistics                             //(5.1.008)

        public TGroundwater(int aquifer, int node, double surfElev, double a1, double b1, double a2, double b2, double a3,
                            double fixedDepth, double nodeElev, double bottomElev, double waterTableElev, double upperMoisture,
                            double theta, double lowerDepth, double oldFlow, double newFlow, double evapLoss, double maxInfilVol, TGWaterStats stats) {
            this.aquifer = aquifer;
            this.node = node;
            this.surfElev = surfElev;
            this.a1 = a1;
            this.b1 = b1;
            this.a2 = a2;
            this.b2 = b2;
            this.a3 = a3;
            this.fixedDepth = fixedDepth;
            this.nodeElev = nodeElev;
            this.bottomElev = bottomElev;
            this.waterTableElev = waterTableElev;
            this.upperMoisture = upperMoisture;
            this.theta = theta;
            this.lowerDepth = lowerDepth;
            this.oldFlow = oldFlow;
            this.newFlow = newFlow;
            this.evapLoss = evapLoss;
            this.maxInfilVol = maxInfilVol;
            this.stats = stats;
        }
    }

    