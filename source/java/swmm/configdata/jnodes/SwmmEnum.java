package swmm.configdata.jnodes;




public class SwmmEnum {
    //-----------------------------------------------------------------------------
//  public enums.h
//
//  Project: EPA SWMM5
//  Version: 5.1
//  Date:    03/20/14  (Build 5.1.001)
//           04/14/14  (Build 5.1.004)
//           09/15/14  (Build 5.1.007)
//           03/19/15  (Build 5.1.008)
//  Author:  L. Rossman
//
//  public enumerated variables
//
//  Build 5.1.004:
//  - IGNORE_RDII for the ignore RDII option added.
//
//  Build 5.1.007:
//  - s_GWF for [GWF] input file section added.
//  - s_ADJUST for [ADJUSTMENTS] input file section added.
//
//  Build 5.1.008:
//  - public enumerations for fullness state of a conduit added.
//  - NUM_THREADS added for number of parallel threads option.
//  - Runoff flow categories added to represent mass balance components.
//
//-----------------------------------------------------------------------------

    //-------------------------------------
//Names of major object types
//-------------------------------------
    public enum ObjectType {
        GAGE,                            // rain gage
        SUBCATCH,                        // subcatchment
        NODE,                            // conveyance system node
        LINK,                            // conveyance system link
        POLLUT,                          // pollutant
        LANDUSE,                         // land use category
        TIMEPATTERN,                     // dry weather flow time pattern
        CURVE,                           // generic table of values
        TSERIES,                         // generic time series of values
        CONTROL,                         // conveyance system control rules
        TRANSECT,                        // irregular channel cross-section
        AQUIFER,                         // groundwater aquifer
        UNITHYD,                         // RDII unit hydrograph
        SNOWMELT,                        // snowmelt parameter set
        SHAPE,                           // custom conduit shape
        LID,                             // LID treatment units
        MAX_OBJ_TYPES
    }

    //-------------------------------------
//Names of Node sub-types
//-------------------------------------
//#define MAX_NODE_TYPES 4
    public final int MAX_NODE_TYPES = 4;

    public enum NodeType {
        JUNCTION,
        OUTFALL,
        STORAGE,
        DIVIDER
    }

    //-------------------------------------
//Names of Link sub-types
//-------------------------------------
//#define MAX_LINK_TYPES 5
    public final int MAX_LINK_TYPES = 5;

    public enum LinkType {
        CONDUIT,
        PUMP,
        ORIFICE,
        WEIR,
        OUTLET
    }

    //-------------------------------------
//File types
//-------------------------------------
    public enum FileType {
        RAINFALL_FILE,                   // rainfall file
        RUNOFF_FILE,                     // runoff file
        HOTSTART_FILE,                   // hotstart file
        RDII_FILE,                       // RDII file
        INFLOWS_FILE,                    // inflows interface file
        OUTFLOWS_FILE
    }                  // outflows interface file

    //-------------------------------------
//File usage types
//-------------------------------------
    public enum FileUsageType {
        NO_FILE,                         // no file usage
        SCRATCH_FILE,                    // use temporary scratch file
        USE_FILE,                        // use previously saved file
        SAVE_FILE
    }                      // save file currently in use

    //-------------------------------------
//Rain gage data types
//-------------------------------------
    public enum GageDataType {
        RAIN_TSERIES,                    // rainfall from user-supplied time series
        RAIN_FILE
    }                      // rainfall from external file

    //-------------------------------------
//Cross section shape types
//-------------------------------------
    public enum XsectType {
        DUMMY,                           // 0
        CIRCULAR,                        // 1      closed
        FILLED_CIRCULAR,                 // 2      closed
        RECT_CLOSED,                     // 3      closed
        RECT_OPEN,                       // 4
        TRAPEZOIDAL,                     // 5
        TRIANGULAR,                      // 6
        PARABOLIC,                       // 7
        POWERFUNC,                       // 8
        RECT_TRIANG,                     // 9
        RECT_ROUND,                      // 10
        MOD_BASKET,                      // 11
        HORIZ_ELLIPSE,                   // 12     closed
        VERT_ELLIPSE,                    // 13     closed
        ARCH,                            // 14     closed
        EGGSHAPED,                       // 15     closed
        HORSESHOE,                       // 16     closed
        GOTHIC,                          // 17     closed
        CATENARY,                        // 18     closed
        SEMIELLIPTICAL,                  // 19     closed
        BASKETHANDLE,                    // 20     closed
        SEMICIRCULAR,                    // 21     closed
        IRREGULAR,                       // 22
        CUSTOM,                          // 23     closed
        FORCE_MAIN
    }                     // 24     closed

    //-------------------------------------
//Measurement units types
//-------------------------------------
    public enum UnitsType {
        US,                              // US units
        SI
    }                             // SI (metric) units

    public enum FlowUnitsType {
        CFS,                             // cubic feet per second
        GPM,                             // gallons per minute
        MGD,                             // million gallons per day
        CMS,                             // cubic meters per second
        LPS,                             // liters per second
        MLD
    }                            // million liters per day

    public enum ConcUnitsType {
        MG,                              // Milligrams / L
        UG,                              // Micrograms / L
        COUNT
    }                          // Counts / L

    //--------------------------------------
//Quantities requiring unit conversions
//--------------------------------------
    public enum ConversionType {
        RAINFALL,
        RAINDEPTH,
        EVAPRATE,
        LENGTH,
        LANDAREA,
        VOLUME,
        WINDSPEED,
        TEMPERATURE,
        MASS,
        GWFLOW,
        FLOW
    }                           // Flow must always be listed last

    //-------------------------------------
//Computed subcatchment quantities
//-------------------------------------
//#define MAX_SUBCATCH_RESULTS 9
    public final int MAX_SUBCATCH_RESULTS = 9;

    public enum SubcatchResultType {
        SUBCATCH_RAINFALL,               // rainfall intensity
        SUBCATCH_SNOWDEPTH,              // snow depth
        SUBCATCH_EVAP,                   // evap loss
        SUBCATCH_INFIL,                  // infil loss
        SUBCATCH_RUNOFF,                 // runoff flow rate
        SUBCATCH_GW_FLOW,                // groundwater flow rate to node
        SUBCATCH_GW_ELEV,                // elevation of saturated gw table
        SUBCATCH_SOIL_MOIST,             // soil moisture
        SUBCATCH_WASHOFF
    }               // pollutant washoff concentration

    //-------------------------------------
//Computed node quantities
//-------------------------------------
//#define MAX_NODE_RESULTS 7
    public final int MAX_NODE_RESULTS = 7;

    public enum NodeResultType {
        NODE_DEPTH,                      // water depth above invert
        NODE_HEAD,                       // hydraulic head
        NODE_VOLUME,                     // volume stored & ponded
        NODE_LATFLOW,                    // lateral inflow rate
        NODE_INFLOW,                     // total inflow rate
        NODE_OVERFLOW,                   // overflow rate
        NODE_QUAL
    }                      // concentration of each pollutant

    //-------------------------------------
//Computed link quantities
//-------------------------------------
//#define MAX_LINK_RESULTS 6
    public final int MAX_LINK_RESULTS = 6;

    public enum LinkResultType {
        LINK_FLOW,                       // flow rate
        LINK_DEPTH,                      // flow depth
        LINK_VELOCITY,                   // flow velocity
        LINK_VOLUME,                     // link volume
        LINK_CAPACITY,                   // ratio of area to full area
        LINK_QUAL
    }                      // concentration of each pollutant

    //-------------------------------------
//System-wide flow quantities
//-------------------------------------
//#define MAX_SYS_RESULTS 14
    public final int MAX_SYS_RESULTS = 14;

    public enum SysFlowType {
        SYS_TEMPERATURE,                  // air temperature
        SYS_RAINFALL,                     // rainfall intensity
        SYS_SNOWDEPTH,                    // snow depth
        SYS_INFIL,                        // infil
        SYS_RUNOFF,                       // runoff flow
        SYS_DWFLOW,                       // dry weather inflow
        SYS_GWFLOW,                       // ground water inflow
        SYS_IIFLOW,                       // RDII inflow
        SYS_EXFLOW,                       // external inflow
        SYS_INFLOW,                       // total lateral inflow
        SYS_FLOODING,                     // flooding outflow
        SYS_OUTFLOW,                      // outfall outflow
        SYS_STORAGE,                      // storage volume
        SYS_EVAP
    }                        // evaporation

    //-------------------------------------
//Conduit flow classifications
//-------------------------------------
    static public final int MAX_FLOW_CLASSES = 7;                                             //(5.1.008)

    public enum FlowClassType {
        DRY,                             // dry conduit
        UP_DRY,                          // upstream end is dry
        DN_DRY,                          // downstream end is dry
        SUBCRITICAL,                     // sub-critical flow
        SUPCRITICAL,                     // super-critical flow
        UP_CRITICAL,                     // free-fall at upstream end
        DN_CRITICAL,                     // free-fall at downstream end
        MAX_FLOW_CLASSES,                // number of distinct flow classes      //(5.1.008)
        UP_FULL,                         // upstream end is full                 //(5.1.008)
        DN_FULL,                         // downstream end is full               //(5.1.008)
        ALL_FULL
    }                       // completely full                      //(5.1.008)


    //// Added to release 5.1.008.  ////                                          //(5.1.008)
//------------------------
//Runoff flow categories
//------------------------
    public enum RunoffFlowType {
        RUNOFF_RAINFALL,                  // rainfall
        RUNOFF_EVAP,                      // evaporation
        RUNOFF_INFIL,                     // infiltration
        RUNOFF_RUNOFF,                    // runoff
        RUNOFF_DRAINS,                    // LID drain flow
        RUNOFF_RUNON
    }                    // runon from outfalls

    //-------------------------------------
//Surface pollutant loading categories
//-------------------------------------
    public enum LoadingType {
        BUILDUP_LOAD,                    // pollutant buildup load
        DEPOSITION_LOAD,                 // rainfall deposition load
        SWEEPING_LOAD,                   // load removed by sweeping
        BMP_REMOVAL_LOAD,                // load removed by BMPs
        INFIL_LOAD,                      // runon load removed by infiltration
        RUNOFF_LOAD,                     // load removed by runoff
        FINAL_LOAD
    }                     // load remaining on surface

    //-------------------------------------
//Input data options
//-------------------------------------
    public enum RainfallType {
        RAINFALL_INTENSITY,              // rainfall expressed as intensity
        RAINFALL_VOLUME,                 // rainfall expressed as volume
        CUMULATIVE_RAINFALL
    }            // rainfall expressed as cumulative volume

    public enum TempType {
        NO_TEMP,                         // no temperature data supplied
        TSERIES_TEMP,                    // temperatures come from time series
        FILE_TEMP
    }                      // temperatures come from file

    public enum WindType {
        MONTHLY_WIND,                    // wind speed varies by month
        FILE_WIND
    }                      // wind speed comes from file

    public enum EvapType {
        CONSTANT_EVAP,                   // constant evaporation rate
        MONTHLY_EVAP,                    // evaporation rate varies by month
        TIMESERIES_EVAP,                 // evaporation supplied by time series
        TEMPERATURE_EVAP,                // evaporation from daily temperature
        FILE_EVAP,                       // evaporation comes from file
        RECOVERY,                        // soil recovery pattern
        DRYONLY,
        UNDEFINED
    }                        // evap. allowed only in dry periods

    public enum NormalizerType {
        PER_AREA,                        // buildup is per unit or area
        PER_CURB
    }                       // buildup is per unit of curb length

    public enum BuildupType {
        NO_BUILDUP,                      // no buildup
        POWER_BUILDUP,                   // power function buildup equation
        EXPON_BUILDUP,                   // exponential function buildup equation
        SATUR_BUILDUP,                   // saturation function buildup equation
        EXTERNAL_BUILDUP
    }               // external time series buildup

    public enum WashoffType {
        NO_WASHOFF,                      // no washoff
        EXPON_WASHOFF,                   // exponential washoff equation
        RATING_WASHOFF,                  // rating curve washoff equation
        EMC_WASHOFF
    }                    // event mean concentration washoff

    public enum SubAreaType {
        IMPERV0,                         // impervious w/o depression storage
        IMPERV1,                         // impervious w/ depression storage
        PERV
    }                           // pervious

    public enum RunoffRoutingType {
        TO_OUTLET,                       // perv & imperv runoff goes to outlet
        TO_IMPERV,                       // perv runoff goes to imperv area
        TO_PERV
    }                        // imperv runoff goes to perv subarea

    public enum RouteModelType {
        NO_ROUTING,                      // no routing
        SF,                              // steady flow model
        KW,                              // kinematic wave model
        EKW,                             // extended kin. wave model
        DW
    }                             // dynamic wave model

    public enum ForceMainType {
        H_W,                             // Hazen-Williams eqn.
        D_W
    }                            // Darcy-Weisbach eqn.

    public enum OffsetType {
        DEPTH,
        ELEVATION
//        DEPTH_OFFSET,                    // offset measured as depth
//        ELEV_OFFSET
    }                    // offset measured as elevation

    public enum KinWaveMethodType {
        NORMAL,                          // normal method
        MODIFIED
    }                       // modified method

    public enum CompatibilityType {
        SWMM5,                           // SWMM 5 weighting for area & hyd. radius
        SWMM3,                           // SWMM 3 weighting
        SWMM4
    }                          // SWMM 4 weighting

    public enum NormalFlowType {
        SLOPE,                           // based on slope only
        FROUDE,                          // based on Fr only
        BOTH
    }                           // based on slope & Fr

    public enum InertialDampingType {
        NO_DAMPING,                      // no inertial damping
        PARTIAL_DAMPING,                 // partial damping
        FULL_DAMPING
    }                   // full damping

    public enum InflowType {
        EXTERNAL_INFLOW,                 // user-supplied external inflow
        DRY_WEATHER_INFLOW,              // user-supplied dry weather inflow
        WET_WEATHER_INFLOW,              // computed runoff inflow
        GROUNDWATER_INFLOW,              // computed groundwater inflow
        RDII_INFLOW,                     // computed I&I inflow
        FLOW_INFLOW,                     // inflow parameter is flow
        CONCEN_INFLOW,                   // inflow parameter is pollutant concen.
        MASS_INFLOW
    }                    // inflow parameter is pollutant mass

    public enum PatternType {
        MONTHLY_PATTERN,                 // DWF multipliers for each month
        DAILY_PATTERN,                   // DWF multipliers for each day of week
        HOURLY_PATTERN,                  // DWF multipliers for each hour of day
        WEEKEND_PATTERN
    }                // hourly multipliers for week end days

    public enum OutfallType {
        FREE_OUTFALL,                    // critical depth outfall condition
        NORMAL_OUTFALL,                  // normal flow depth outfall condition
        FIXED_OUTFALL,                   // fixed depth outfall condition
        TIDAL_OUTFALL,                   // variable tidal stage outfall condition
        TIMESERIES_OUTFALL
    }             // variable time series outfall depth

    public enum StorageType {
        TABULAR,                         // area v. depth from table
        FUNCTIONAL
    }                     // area v. depth from power function

    public enum ReactorType {
        CSTR,                            // completely mixed reactor
        PLUG
    }                           // plug flow reactor

    public enum TreatmentType {
        REMOVAL,                         // treatment stated as a removal
        CONCEN
    }                         // treatment stated as effluent concen.

    public enum DividerType {
        CUTOFF_DIVIDER,                  // diverted flow is excess of cutoff flow
        TABULAR_DIVIDER,                 // table of diverted flow v. inflow
        WEIR_DIVIDER,                    // diverted flow proportional to excess flow
        OVERFLOW_DIVIDER
    }               // diverted flow is flow > full conduit flow

    public enum PumpCurveType {
        TYPE1_PUMP,                      // flow varies stepwise with wet well volume
        TYPE2_PUMP,                      // flow varies stepwise with inlet depth
        TYPE3_PUMP,                      // flow varies with head delivered
        TYPE4_PUMP,                      // flow varies with inlet depth
        IDEAL_PUMP
    }                     // outflow equals inflow

    public enum OrificeType {
        SIDE_ORIFICE,                    // side orifice
        BOTTOM_ORIFICE
    }                 // bottom orifice

    public enum WeirType {
        TRANSVERSE_WEIR,                 // transverse weir
        SIDEFLOW_WEIR,                   // side flow weir
        VNOTCH_WEIR,                     // V-notch (triangular) weir
        TRAPEZOIDAL_WEIR
    }               // trapezoidal weir

    public enum CurveType {
        STORAGE_CURVE,                   // surf. area v. depth for storage node
        DIVERSION_CURVE,                 // diverted flow v. inflow for divider node
        TIDAL_CURVE,                     // water elev. v. hour of day for outfall
        RATING_CURVE,                    // flow rate v. head for outlet link
        CONTROL_CURVE,                   // control setting v. controller variable
        SHAPE_CURVE,                     // width v. depth for custom x-section
        PUMP1_CURVE,                     // flow v. wet well volume for pump
        PUMP2_CURVE,                     // flow v. depth for pump (discrete)
        PUMP3_CURVE,                     // flow v. head for pump (continuous)
        PUMP4_CURVE
    }                    // flow v. depth for pump (continuous)

    public enum InputSectionType {
        s_TITLE, s_OPTION, s_FILE, s_RAINGAGE,
        s_TEMP, s_EVAP, s_SUBCATCH, s_SUBAREA,
        s_INFIL, s_AQUIFER, s_GROUNDWATER, s_SNOWMELT,
        s_JUNCTION, s_OUTFALL, s_STORAGE, s_DIVIDER,
        s_CONDUIT, s_PUMP, s_ORIFICE, s_WEIR,
        s_OUTLET, s_XSECTION, s_TRANSECT, s_LOSSES,
        s_CONTROL, s_POLLUTANT, s_LANDUSE, s_BUILDUP,
        s_WASHOFF, s_COVERAGE, s_INFLOW, s_DWF,
        s_PATTERN, s_RDII, s_UNITHYD, s_LOADING,
        s_TREATMENT, s_CURVE, s_TIMESERIES, s_REPORT,
        s_COORDINATE, s_VERTICES, s_POLYGON, s_LABEL,
        s_SYMBOL, s_BACKDROP, s_TAG, s_PROFILE,
        s_MAP, s_LID_CONTROL, s_LID_USAGE, s_GWF,                   //(5.1.007)
        s_ADJUST
    }                                                               //(5.1.007)

    public enum InputOptionType {
        FLOW_UNITS, INFIL_MODEL, ROUTE_MODEL,
        START_DATE, START_TIME, END_DATE,
        END_TIME, REPORT_START_DATE, REPORT_START_TIME,
        SWEEP_START, SWEEP_END, START_DRY_DAYS,
        WET_STEP, DRY_STEP, ROUTE_STEP,
        REPORT_STEP, ALLOW_PONDING, INERT_DAMPING,
        SLOPE_WEIGHTING, VARIABLE_STEP, NORMAL_FLOW_LTD,
        LENGTHENING_STEP, MIN_SURFAREA, COMPATIBILITY,
        SKIP_STEADY_STATE, TEMPDIR, IGNORE_RAINFALL,
        FORCE_MAIN_EQN, LINK_OFFSETS, MIN_SLOPE,
        IGNORE_SNOWMELT, IGNORE_GWATER, IGNORE_ROUTING,
        IGNORE_QUALITY, MAX_TRIALS, HEAD_TOL,
        SYS_FLOW_TOL, LAT_FLOW_TOL, IGNORE_RDII,                       //(5.1.004)
        MIN_ROUTE_STEP, NUM_THREADS
    }                                         //(5.1.008)

    public enum NoYesType {
        NO,
        YES
    }

    public enum NoneAllType {
        NONE,
        ALL,
        SOME
    }

    public enum InerDampingType {
        NONE,
        PARTIAL,
        FULL
    }

    public enum LinkOffsetType {
        DEPTH,
        ELEVATION
    }


}
