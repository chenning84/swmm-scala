package swmm.configdata.jnodes.types;


// POLLUTANT OBJECT

  
    public class      TPollut extends TBase
    {
  //      public String         ID;              // Pollutant ID
        public int          units;           // units
        public double        mcf;             // mass conversion factor
        public double        dwfConcen;       // dry weather sanitary flow concen.
        public double        pptConcen;       // precip. concen.
        public double        gwConcen;        // groundwater concen.
        public double        rdiiConcen;      // RDII concen.
        public double        initConcen;      // initial concen. in conveyance network
        public double        kDecay;          // decay constant (1/sec)
        public int          coPollut;        // co-pollutant index
        public double        coFraction;      // co-pollutant fraction
        public int          snowOnly;        // TRUE if buildup occurs only under snow

  
    }


    