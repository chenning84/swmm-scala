package swmm.configdata.jnodes.types;


// CUSTOM CROSS SECTION SHAPE STRUCTURE


    public class      TShape extends TBase
    {
        public static final int N_SHAPE_TBL = 51  ;// size of shape geometry tables
        public int          curve;                     // index of shape curve
        public int          nTbl;                      // size of geometry tables
        public double       aFull;                     // area when full
        public double       rFull;                     // hyd. radius when full
        public double       wMax;                      // max. width
        public double       sMax;                      // max. section factor
        public double       aMax;                      // area at max. section factor
        public double[]       areaTbl=new  double[N_SHAPE_TBL];      // table of area v. depth
        public double[]       hradTbl=new  double[N_SHAPE_TBL];      // table of hyd. radius v. depth
        public double[]       widthTbl=new  double[N_SHAPE_TBL];     // table of top width v. depth

   
        
    }


    