package swmm.configdata.jnodes.types;
import swmm.configdata.jnodes.SwmmEnum.*;

// SUBAREA OBJECT
// An array of 3 subarea objects is associated with each subcatchment object.
// They describe the runoff process on 3 types of surfaces:
//   1 - impervious with no depression storage
//   2 - impervious with depression storage
//   3 - pervious

  

    public class      TSubarea
    {
        public RunoffRoutingType           routeTo;         // code indicating where outflow is sent
        public  double        fOutlet;         // fraction of outflow to outlet
        public double        N;               // Manning's n
        public double        fArea;           // fraction of total area
        public double        dStore;          // depression storage (ft)

        public double        alpha;           // overland flow factor
        public double        inflow;          // inflow rate (ft/sec)
        public double        runoff;          // runoff rate (ft/sec)
        public double        depth;           // depth of surface runoff (ft)

   
    }
