package swmm.configdata.jnodes.types;

// NODE STATISTICS

import org.joda.time.DateTime;

public class      TNodeStats
{
    double        avgDepth;
    double        maxDepth;
    DateTime maxDepthDate;
    double        maxRptDepth;                                                  //(5.1.008)
    double        volFlooded;
    double        timeFlooded;
    double        timeSurcharged;
    double        timeCourantCritical;
    double        totLatFlow;
    double        maxLatFlow;
    double        maxInflow;
    double        maxOverflow;
    double        maxPondedVol;
    DateTime      maxInflowDate;
    DateTime      maxOverflowDate;

    public TNodeStats(double avgDepth, double maxDepth, DateTime maxDepthDate, double maxRptDepth, double volFlooded,
                      double timeFlooded, double timeSurcharged, double timeCourantCritical, double totLatFlow, double maxLatFlow,
                      double maxInflow, double maxOverflow, double maxPondedVol, DateTime maxInflowDate, DateTime maxOverflowDate) {
        this.avgDepth = avgDepth;
        this.maxDepth = maxDepth;
        this.maxDepthDate = maxDepthDate;
        this.maxRptDepth = maxRptDepth;
        this.volFlooded = volFlooded;
        this.timeFlooded = timeFlooded;
        this.timeSurcharged = timeSurcharged;
        this.timeCourantCritical = timeCourantCritical;
        this.totLatFlow = totLatFlow;
        this.maxLatFlow = maxLatFlow;
        this.maxInflow = maxInflow;
        this.maxOverflow = maxOverflow;
        this.maxPondedVol = maxPondedVol;
        this.maxInflowDate = maxInflowDate;
        this.maxOverflowDate = maxOverflowDate;
    }
}


    