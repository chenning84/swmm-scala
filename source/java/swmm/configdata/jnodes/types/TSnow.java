package swmm.configdata.jnodes.types;

// SNOW OBJECT

public class TSnow {
    public double snotmp;          // temp. dividing rain from snow (deg F)
    public double tipm;            // antecedent temp. index parameter
    public double rnm;             // ratio of neg. melt to melt coeff.
    public double[][] adc = new  double[2][10];      // areal depletion curves (pervious &
// imperv. area curves w/ 10 pts.each)

    public double season;          // snowmelt season
    public double removed;         // total snow plowed out of system (ft3)


}


    