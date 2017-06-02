package swmm.configdata.jnodes.types;


// ADJUSTMENTS OBJECT

  
public    class  TAdjust
{
    public double[]       temp=new double[12];        // monthly temperature adjustments (deg F)
    public double[]       evap=new double[12];        // monthly evaporation adjustments (ft/s)
    public double[]       rain=new double[12];        // monthly rainfall adjustment multipliers
    public double[]       hydcon=new double[12];      // hyd. conductivity adjustment multipliers  //(5.1.008)
    public double       rainFactor;      // current rainfall adjustment multiplier
    public double       hydconFactor;    // current conductivity multiplier           //(5.1.008)

}


    