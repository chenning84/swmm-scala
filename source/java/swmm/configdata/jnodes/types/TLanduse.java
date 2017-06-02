package swmm.configdata.jnodes.types;


// LANDUSE OBJECT


public class TLanduse {
    public String ID;              // landuse name
    public double sweepInterval;   // street sweeping interval (days)
    public double sweepRemoval;    // fraction of buildup available for sweeping
    public double sweepDays0;      // days since last sweeping at start
    public TBuildup buildupFunc;     // array of buildup functions for pollutants
    public TWashoff washoffFunc;     // array of washoff functions for pollutants


}


    