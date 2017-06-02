package swmm.configdata.jnodes.types;


// CROSS SECTION DATA STRUCTURE

  
 public    class      TXsect extends TBase
    {
       // int           type;            // type code of cross section shape
        public int           culvertCode;     // type of culvert (if any)
        public TTransect    transect;        // index of transect/shape (if applicable)
        public double        yFull;           // depth when full (ft)
        public double        wMax;            // width at widest point (ft)
        public double        ywMax;           // depth at widest point (ft)
        public double        aFull;           // area when full (ft2)
        public double        rFull;           // hyd. radius when full (ft)
        public double        sFull;           // section factor when full (ft^4/3)
        public double        sMax;            // section factor at max. flow (ft^4/3)

        // These variables have different meanings depending on section shape
        public double        yBot;            // depth of bottom section
        public double        aBot;            // area of bottom section
        public double        sBot;            // slope of bottom section
        public double        rBot;            // radius of bottom section

    }


