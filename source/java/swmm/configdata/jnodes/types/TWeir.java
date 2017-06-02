package swmm.configdata.jnodes.types;

// WEIR OBJECT

      public class      TWeir extends TLink
    {
      //  int           type;            // weir type code
        public int           shape;           // weir shape code
        public double        cDisch1;         // discharge coeff.
        public double        cDisch2;         // discharge coeff. for ends
        public double        endCon;          // end contractions
        public boolean           canSurcharge;    // true if weir can surcharge                //(5.1.007)

        public double        cSurcharge;      // orifice coeff. for surcharge              //(5.1.007)
        public double        length;          // equivalent length (ft)
        public double        slope;           // slope for Vnotch & Trapezoidal weirs
        public double        surfArea;        // equivalent surface area (ft2)

   
    }


    