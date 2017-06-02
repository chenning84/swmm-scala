package swmm.configdata.jnodes.types;


// EVAPORATION OBJECT


import swmm.configdata.jnodes.SwmmEnum.*;

public     class  TEvap
{
    public EvapType sType;            // type of evaporation data
    public  int          tSeries;         // time series index
    public double []      monthlyEvap=new double[12]; // monthly evaporation values
    public double []      panCoeff = new double[12];    // monthly pan coeff. values
    public TPattern         recoveryPattern; // soil recovery factor pattern
    public boolean          dryOnly;         // true if evaporation only in dry periods

    public double       rate;            // current evaporation rate (ft/sec)
    public double       recoveryFactor;  // current soil recovery factor


}

    ////  Added for release 5.1.007  ////                                          //(5.1.007)
