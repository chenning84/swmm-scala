package swmm.configdata.jnodes.types;

// CROSS SECTION TRANSECT DATA STRUCTURE


  
    public class   TTransect extends  TBase
    {
        public  static int  N_TRANSECT_TBL = 51;       // size of transect geometry tables

     //   public String        ID;                        // section ID
        public double       yFull;                     // depth when full (ft)
        public double       aFull;                     // area when full (ft2)
        public double       rFull;                     // hyd. radius when full (ft)
        public double       wMax;                      // width at widest point (ft)
        public double       ywMax;                     // depth at max width (ft)
        public double       sMax;                      // section factor at max. flow (ft^4/3)
        public double       aMax;                      // area at max. flow (ft2)
        public double       lengthFactor;              // floodplain / channel length

        public double       roughness;                 // Manning's n
        public double[]       areaTbl=new  double[N_TRANSECT_TBL];   // table of area v. depth
        public double[]       hradTbl=new  double[N_TRANSECT_TBL];   // table of hyd. radius v. depth
        public double[]       widthTbl=new  double[N_TRANSECT_TBL];  // table of top width v. depth
        public int          nTbl;                      // size of geometry tables

  
        
    }


