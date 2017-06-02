package swmm.configdata.jnodes.types;

// LINK STATISTICS

import org.joda.time.DateTime;
import swmm.configdata.jnodes.SwmmEnum;

public class      TLinkStats
    {
        double        maxFlow;
        DateTime maxFlowDate;
        double        maxVeloc;
        //DateTime      maxVelocDate;  //deprecated                                 //(5.1.008)
        double        maxDepth;
        double        timeNormalFlow;
        double        timeInletControl;
        double        timeSurcharged;
        double        timeFullUpstream;
        double        timeFullDnstream;
        double        timeFullFlow;
        double        timeCapacityLimited;
        double[]        timeInFlowClass= new double[SwmmEnum.MAX_FLOW_CLASSES];
        double        timeCourantCritical;
        long          flowTurns;
        int           flowTurnSign;

        public TLinkStats(double maxFlow, DateTime maxFlowDate, double maxVeloc, double maxDepth, double timeNormalFlow, double timeInletControl, double timeSurcharged, double timeFullUpstream, double timeFullDnstream,
                          double timeFullFlow, double timeCapacityLimited, double[] timeInFlowClass, double timeCourantCritical, long flowTurns, int flowTurnSign) {
            this.maxFlow = maxFlow;
            this.maxFlowDate = maxFlowDate;
            this.maxVeloc = maxVeloc;
            this.maxDepth = maxDepth;
            this.timeNormalFlow = timeNormalFlow;
            this.timeInletControl = timeInletControl;
            this.timeSurcharged = timeSurcharged;
            this.timeFullUpstream = timeFullUpstream;
            this.timeFullDnstream = timeFullDnstream;
            this.timeFullFlow = timeFullFlow;
            this.timeCapacityLimited = timeCapacityLimited;
            this.timeInFlowClass = timeInFlowClass;
            this.timeCourantCritical = timeCourantCritical;
            this.flowTurns = flowTurns;
            this.flowTurnSign = flowTurnSign;
        }
    }


    