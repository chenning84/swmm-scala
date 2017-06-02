package swmm.configdata.jnodes.types;

// SNOWMELT OBJECT
// Snowmelt objects contain parameters that describe the melting
// process of snow packs on 3 different types of surfaces:
//   1 - plowable impervious area
//   2 - non-plowable impervious area
//   3 - pervious area
/*
   char*         ID;              // snowmelt parameter set name
   double        snn;             // fraction of impervious area plowable
   double        si[3];           // snow depth for 100% cover
   double        dhmin[3];        // min. melt coeff. for each surface (ft/sec-F)
   double        dhmax[3];        // max. melt coeff. for each surface (ft/sec-F)
   double        tbase[3];        // base temp. for melting (F)
   double        fwfrac[3];       // free water capacity / snow depth
   double        wsnow[3];        // initial snow depth on each surface (ft)
   double        fwnow[3];        // initial free water in snow pack (ft)
   double        weplow;          // depth at which plowing begins (ft)
   double        sfrac[5];        // fractions moved to other areas by plowing
   int           toSubcatch;      // index of subcatch receiving plowed snow

   double        dhm[3];          // melt coeff. for each surface (ft/sec-F)
 */

      public class      TSnowmelt extends TBase
    {
       // public  String         ID;              // snowmelt parameter set name
        public double        snn;             // fraction of impervious area plowable
        public double[]      si=new double[3];           // snow depth for 100% cover
        public double[]        dhmin=new double[3];        // min. melt coeff. for each surface (ft/sec-F)
        public double[]        dhmax=new double[3];        // max. melt coeff. for each surface (ft/sec-F)
        public  double[]        tbase=new double[3];        // base temp. for melting (F)
        public double[]        fwfrac=new double[3];       // free water capacity / snow depth
        public double []       wsnow=new double[3];        // initial snow depth on each surface (ft)
        public double[]        fwnow=new double[3];        // initial free water in snow pack (ft)
        public double        weplow;          // depth at which plowing begins (ft)
        public double[]        sfrac=new double[5];        // fractions moved to other areas by plowing
        public  int           toSubcatch;      // index of subcatch receiving plowed snow

        public double[]        dhm=new double[3];          // melt coeff. for each surface (ft/sec-F)


    }


    