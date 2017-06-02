package swmm.configdata.jnodes.types;

//import swmm.configdata.jnodes.TExfil;// STORAGE UNIT OBJECT

public class TStorage extends TNode{
 //   public String sType;
    public double fEvap;
    public double aConst;            // surface area at zero height (ft2)
    public double aCoeff;            // coeff. of area v. height curve
    public double aExpon;            // exponent of area v. height curve
    public String aCurve;            // index of tabulated area v. height curve
    public TExfil exfil;             // ptr. to exfiltration object               //(5.1.007)

    public double hrt;               // hydraulic residence time (sec)
    public double evapLoss;          // evaporation loss (ft3)
    public double exfilLoss;         // exfiltration loss (ft3)                   //(5.1.007)

}



    