package swmm.configdata.jnodes.types;


// CURVE/TIME SERIES OBJECT
//[TIMESERIES]
//        ;;Name             Date       Time       Value
//        ;;-------------------------------------------------
//        INFLOW@1SB1      08/11/1998 00:00      0.00000
//        INFLOW@1SB1                 00:05      39.60000
//        INFLOW@1SB1                 00:10      425.10000
//        INFLOW@1SB1                 00:15      634.10000
//        INFLOW@1SB1                 00:20      591.00000
//        INFLOW@1SB1      08/11/1998 00:25      441.00000
//        INFLOW@1SB1                 00:30      313.00000

import swmm.configdata.jnodes.SwmmEnum.*;

public class TTable extends TBase {
  //  public String ID;              // Table/time series ID
    public int curveType;       // type of curve tabulated
    public EvapType refersTo;        // reference to some other object
    public double dxMin;           // smallest x-value interval
    public double lastDate;        // last input date for time series
    public double x1;          // current bracket on x-values
    public double x2;          // current bracket on x-values
    public double y1;          // current bracket on y-values
    public double y2;          // current bracket on y-values
    public TTableEntry firstEntry;      // first data point
    public TTableEntry lastEntry;       // last data point
    public TTableEntry thisEntry;       // current data point
    public TFile file;            // external data file

}


    