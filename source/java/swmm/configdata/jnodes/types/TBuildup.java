package swmm.configdata.jnodes.types;

// BUILDUP FUNCTION OBJECT

public class TBuildup {
    public int normalizer;      // normalizer code (area or curb length)
    public int funcType;        // buildup function type code
    public double[] coeff = new double[3];        // coeffs. of buildup function
    public double maxDays;         // time to reach max. buildup (days)


}


