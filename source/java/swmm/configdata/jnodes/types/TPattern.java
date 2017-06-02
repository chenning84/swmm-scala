package swmm.configdata.jnodes.types;


// TIME PATTERN DATA

  
    public class    TPattern extends TBase
    {
      //  String        ID;               // time pattern name
      public   int          type;             // time pattern type code
      public   int          count;            // number of factors
      public   double[]       factor=new double[24];       // time pattern factors


    }



    