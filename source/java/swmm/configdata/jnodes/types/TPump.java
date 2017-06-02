package swmm.configdata.jnodes.types;

// PUMP OBJECT

public class TPump extends TLink {
    public String sType;            // pump type
    public TPump pumpCurve;       // pump curve table index
    public String initSetting;     // initial speed setting
    public double yOn;             // startup depth (ft)
    public double yOff;            // shutoff depth (ft)
    public double xMin;            // minimum pt. on pump curve
    public double xMax;            // maximum pt. on pump curve


}


    