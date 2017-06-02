package swmm.configdata.jnodes.types;


// UNIT HYDROGRAPH GROUP OBJECT

  
    public class      TUnitHyd extends TBase
    {
       public String         ID;              // name of the unit hydrograph object
       public  int           rainGage;        // index of rain gage
        public double [][]        iaMax=new double[12][3];    // max. initial abstraction (IA) (in or mm)
        public double [][]        iaRecov=new double[12][3];  // IA recovery rate (in/day or mm/day)
        public double [][]        iaInit=new double[12][3];   // starting IA (in or mm)
        public double [][]        r=new double[12][3];        // fraction of rainfall becoming I&I
        public long[] []         tBase=new long[12][3];    // time base of each UH in each month (sec)
        public long[][]          tPeak=new long[12][3];    // time to peak of each UH in each month (sec)

    
    }


    