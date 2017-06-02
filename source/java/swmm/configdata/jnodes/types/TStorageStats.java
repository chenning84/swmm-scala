package swmm.configdata.jnodes.types;


// STORAGE STATISTICS


import org.joda.time.DateTime;

public class      TStorageStats
    {
        double        initVol;
        double        avgVol;
        double        maxVol;
        double        maxFlow;
        double        evapLosses;
        double        exfilLosses;
        DateTime maxVolDate;

        public TStorageStats(double initVol, double avgVol, double maxVol, double maxFlow, double evapLosses, double exfilLosses, DateTime maxVolDate) {
            this.initVol = initVol;
            this.avgVol = avgVol;
            this.maxVol = maxVol;
            this.maxFlow = maxFlow;
            this.evapLosses = evapLosses;
            this.exfilLosses = exfilLosses;
            this.maxVolDate = maxVolDate;
        }
    }


    