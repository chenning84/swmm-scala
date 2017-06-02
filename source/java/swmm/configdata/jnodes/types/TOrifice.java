package swmm.configdata.jnodes.types;


// ORIFICE OBJECT

  
    public class      TOrifice extends TLink
    {
        public String           sType;            // orifice type code
        public int           shape;           // orifice shape code
        public Double        cDisch;          // discharge coeff.
        public Double        orate;           // time to open/close (sec)

        public Double        cOrif;           // coeff. for orifice flow (ft^2.5/sec)
        public Double        hCrit;           // inlet depth where weir flow begins (ft)
        public Double        cWeir;           // coeff. for weir flow (cfs)
        public Double        length;          // equivalent length (ft)
        public Double        surfArea;        // equivalent surface area (ft2)

  
    }


    