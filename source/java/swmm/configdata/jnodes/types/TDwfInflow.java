package swmm.configdata.jnodes.types;


// DRY WEATHER FLOW INFLOW OBJECT

  
    public class  TDwfInflow
    {
        int            param;          // pollutant index (flow = -1)
        double         avgValue;       // average value (cfs or concen.)
        int[]            patterns=new int[4];    // monthly, daily, hourly, weekend time patterns
        TDwfInflow next;        // pointer to next inflow data object

        public TDwfInflow(int param, double avgValue, int[] patterns, TDwfInflow next) {
            this.param = param;
            this.avgValue = avgValue;
            this.patterns = patterns;
            this.next = next;
        }
    }
 //   class       DwfInflow TDwfInflow;


    