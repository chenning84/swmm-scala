package swmm.configdata.jnodes;

/**
 * Created by ning on 10/24/15.
 */
public class InflowNode {
    public int            param;         // pollutant index (flow = -1)
    public int            type;          // CONCEN or MASS
    public int            tSeries;       // index of inflow time series
    public int            basePat;       // baseline time pattern
    public double         cFactor;       // units conversion factor for mass inflow

    public  double         baseline;      // constant baseline value
    public double         sFactor;       // time series scaling factor
    public InflowNode      next;       // pointer to next inflow data object


}
