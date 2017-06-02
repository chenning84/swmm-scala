package swmm.configdata.jnodes.types;


// OUTFALL OBJECT

  
public class      TOutfall extends TNode
{
    public String       hasFlapGate;        // true if contains flap gate
    public double     fixedStage;         // fixed outfall stage (ft)
    public String        tideCurve;          // index of tidal stage curve
    public String        stageSeries;        // index of outfall stage time series
    public String        routeTo;            // subcatchment index routed onto            //(5.1.008)
    public double     vRouted;            // flow volume routed (ft3)                  //(5.1.008)
    public double     wRouted;            // pollutant load routed (mass)              //(5.1.008)

}


    