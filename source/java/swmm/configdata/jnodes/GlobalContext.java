package swmm.configdata.jnodes;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import swmm.configdata.jnodes.types.TStorage;
import swmm.configdata.jnodes.types.*;
import swmm.configdata.jnodes.SwmmEnum.*;
import swmm.configdata.types.TGage;
import swmm.util.Project;

/**
 * Created by ning on 11/4/15.
 *
 */
public class GlobalContext extends SwmmEnum {
    //-----------------------------------------------------------------------------
//   globals.h
//
//   Project: EPA SWMM5
//   Version: 5.1
//   Date:    03/19/14  (Build 5.1.000)
//            04/14/14  (Build 5.1.004)
//            09/15/14  (Build 5.1.007)
//            03/19/15  (Build 5.1.008)
//   Author:  L. Rossman
//
//   Global Variables
//
//   Build 5.1.004:
//   - Ignore RDII option added.
//
//   Build 5.1.007:
//   - Monthly climate variable adjustments added.
//
//   Build 5.1.008:
//   - Number of parallel threads for dynamic wave routing added.
//   - Minimum dynamic wave routing variable time step added.
//
//-----------------------------------------------------------------------------

     public TFile
                Finp,                     // Input file
                Fout,                     // Output file
                Frpt,                     // Report file
                Fclimate,                 // Climate file
                Frain,                    // Rainfall file
                Frunoff,                  // Runoff file
                Frdii,                    // RDII inflow file
                Fhotstart1,               // Hot start input file
                Fhotstart2,               // Hot start output file
                Finflows,                 // Inflows routing file
                Foutflows;                // Outflows routing file

     public long
            Nperiods,                 // Number of reporting periods
            StepCount,                // Number of routing steps used
            NonConvergeCount;         // Number of non-converging steps
    public int[]
            Nobjects,  // Number of each object type
            Nnodes,   // Number of each node sub-type
            Nlinks;   // Number of each link sub-type



    //    Nobjects[MAX_OBJ_TYPES],  // Number of each object type
        //    Nnodes[MAX_NODE_TYPES],   // Number of each node sub-type
        //    Nlinks[MAX_LINK_TYPES];   // Number of each link sub-type
    public boolean  IgnoreSnowmelt;          // Ignore snowmelt
    public boolean
            IgnoreRainfall,           // Ignore rainfall/runoff
            IgnoreRDII,               // Ignore RDII                     //(5.1.004)

            IgnoreGwater,             // Ignore groundwater
            IgnoreRouting,            // Ignore flow routing
            AllowPonding,             // Allow water to pond at nodes
            IgnoreQuality;            // Ignore water quality
//    public String flowUnits ;
    public FlowUnitsType FlowUnits;     // Flow units
    public UnitsType   UnitSystem;               // Unit system
    public InfilH.InfilType InfilModel;
    public RouteModelType RouteModel;               // Flow routing method
    public InerDampingType        InertDamping;             // Degree of inertial damping
    public OffsetType LinkOffsets;             // Link offset convention
    public NormalFlowType NormalFlowLtd ;            //  normal flow limitation
    public ForceMainType        ForceMainEqn  ;               // Flow equation for force mains
    public int
         SlopeWeighting,           // Use slope weighting
        Compatibility,            // SWMM 5/3/4 compatibility
        SkipSteadyState,          // Skip over steady state periods

        ErrorCode,                // Error code number
        WarningCode,              // Warning code number
        WetStep,                  // Runoff wet time step (sec)
        DryStep,                  // Runoff dry time step (sec)
        ReportStep,               // Reporting time step (sec)
        SweepStart,               // Day of year when sweeping starts
        SweepEnd,                 // Day of year when sweeping ends
        MaxTrials,                // Max. trials for DW routing
        NumThreads;               // Number of parallel threads used //(5.1.008)

    public  String
        Msg,            // Text of output message
        TempDir;      // Temporary file directory
     public String[]   Title;// Project title

     public TRptFlags    RptFlags;                 // Reporting options


    public  double
            RouteStep,                // Routing time step (sec)
            MinRouteStep,             // Minimum variable time step (sec) //(5.1.008)
            LengtheningStep,          // Time step for lengthening (sec)
            StartDryDays,             // Antecedent dry days
            CourantFactor,            // Courant time step factor
            MinSurfArea,              // Minimum nodal surface area
            MinSlope,                 // Minimum conduit slope
            RunoffError,              // Runoff continuity error
            GwaterError,              // Groundwater continuity error
            FlowError,                // Flow routing error
            QualError,                // Quality routing error
            HeadTol,                  // DW routing head tolerance (ft)
            SysFlowTol,               // Tolerance for steady system flow
            LatFlowTol;               // Tolerance for steady nodal inflow       

    public  DateTime
                StartDate,                // Starting date
                StartDateTime,            // Starting Date+Time
                EndDate,                  // Ending date
                EndDateTime,              // Ending Date+Time
                ReportStartDate,          // Report start date
                ReportStart;              // Report start Date+Time
    public LocalTime
            StartTime,                // Starting time
            EndTime,                  // Ending time
            ReportStartTime;          // Report start time
    //        ReportStart;              // Report start Date+Time

     public double
            ReportTime,               // Current reporting time (msec)
            OldRunoffTime,            // Previous runoff time (msec)
            NewRunoffTime,            // Current runoff time (msec)
            OldRoutingTime,           // Previous routing time (msec)
            NewRoutingTime,           // Current routing time (msec)
            TotalDuration;            // Simulation duration (msec)

     public TTemp      Temp;                     // Temperature data
     public TEvap      Evap;                     // Evaporation data
     public TWind      Wind;                     // Wind speed data
     public TSnow      Snow;                     // Snow melt data
     public TAdjust    Adjust;                   // Climate adjustments             //(5.1.007)

     public TSnowmelt[] Snowmelt;                 // Array of snow melt objects
     public TGage[]     Gage;                     // Array of rain gages
     public TSubcatch[] Subcatch;                 // Array of subcatchments
     public TAquifer[]  Aquifer;                  // Array of groundwater aquifers
     public TUnitHyd[]  UnitHyd;                  // Array of unit hydrographs
     public TNode[]     Node;                     // Array of nodes
     public TOutfall[]  Outfall;                  // Array of outfall nodes
     public TDivider[]  Divider;                  // Array of divider nodes
     public TStorage[]  Storage;                  // Array of storage nodes
     public TLink[]     Link;                     // Array of links
     public TConduit[]  Conduit;                  // Array of conduit links
     public TPump[]     Pump;                     // Array of pump links
     public TOrifice[]  Orifice;                  // Array of orifice links
     public TWeir[]     Weir;                     // Array of weir links
     public TOutlet[]   Outlet;                   // Array of outlet device links
     public TPollut[]   Pollut;                   // Array of pollutants
     public TLanduse[]  Landuse;                  // Array of landuses
     public TPattern[]  Pattern;                  // Array of time patterns
     public TTable[]    Curve;                    // Array of curve tables
     public TTable[]    Tseries;                  // Array of time series tables
     public TTransect[] Transect;                 // Array of transect data
     public TShape[]    Shape;                    // Array of custom conduit shapes

    public  GlobalContext() {

    }
//    public static GlobalContext getInstance()
//    {
//        if(singletonInstance==null)
//        {
//            singletonInstance = new GlobalContext();
//        }
//        return singletonInstance;
//    }
  //  public Project project;
 //   static private GlobalContext singletonInstance;

}
