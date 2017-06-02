package swmm.configdata.jnodes.types;

// DIRECT EXTERNAL INFLOW OBJECT

      public class  TExtInflow
    {
        int            param;         // pollutant index (flow = -1)
        int            type;          // CONCEN or MASS
        int            tSeries;       // index of inflow time series
        int            basePat;       // baseline time pattern
        double         cFactor;       // units conversion factor for mass inflow
        double         baseline;      // constant baseline value
        double         sFactor;       // time series scaling factor
        TExtInflow      next;       // pointer to next inflow data object

        public TExtInflow(int param, int type, int tSeries, int basePat, double cFactor, double baseline, double sFactor, TExtInflow next) {
            this.param = param;
            this.type = type;
            this.tSeries = tSeries;
            this.basePat = basePat;
            this.cFactor = cFactor;
            this.baseline = baseline;
            this.sFactor = sFactor;
            this.next = next;
        }
    }
 //   class       ExtInflow TExtInflow;


    