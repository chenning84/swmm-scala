package swmm.cmigrate

/**
 * Created by Ning on 10/23/15.
 *
 */
object SWMMText {
  //-----------------------------------------------------------------------------
  //   text.h
  //
  //   Project: EPA SWMM5
  //   Version: 5.1
  //   Date:    03/19/14  (Build 5.1.001)
  //            04/02/14  (Build 5.1.003)
  //            04/14/14  (Build 5.1.004)
  //            04/23/14  (Build 5.1.005)
  //            05/19/14  (Build 5.1.006)
  //            09/15/14  (Build 5.1.007)
  //            03/19/15  (Build 5.1.008)
  //   Author:  L. Rossman
  //
  //   Text strings
  //-----------------------------------------------------------------------------

 val FMT01=
  "\n Correct syntax is:\n swmm5 <input file> <report file> <output file>\n"
 val FMT02= "\n... EPA-SWMM 5.1 (Build 5.1.008)\n"                           //(5.1.008)

 val FMT03= " There are errors.\n"
 val FMT04= " There are warnings.\n"
 val FMT05= "\n"
 val FMT06= "\n o  Retrieving project data"
 val FMT07= "\n o  Writing output report"
 val FMT08=
  "\n  EPA STORM WATER MANAGEMENT MODEL - VERSION 5.1 (Build 5.1.008)"         //(5.1.008)
 val FMT09=
  "\n  --------------------------------------------------------------"
 val FMT10= "\n"
 val FMT11=  "\n    Cannot use duplicate file names."
 val FMT12=  "\n    Cannot open input file "
 val FMT13=  "\n    Cannot open report file "
 val FMT14=  "\n    Cannot open output file "
 val FMT15=  "\n    Cannot open temporary output file"
 val FMT16=  "\n  ERROR %d detected. Execution halted."
 val FMT17=  "at line %d of input file:"
 val FMT18=  "at line %d of %s] section:"
 val FMT19=  "\n  Maximum error count exceeded."
 val FMT20=  "\n\n  Analysis begun on:  %s"
 val FMT20a= "  Analysis ended on:  %s"
 val FMT21 = "  Total elapsed time: "

  // Warning messages
 val WARN01= "WARNING 01: wet weather time step reduced to recording interval for Rain Gage"
 val WARN02= "WARNING 02: maximum depth increased for Node"
 val WARN03= "WARNING 03: negative offset ignored for Link"
 val WARN04= "WARNING 04: minimum elevation drop used for Conduit"
 val WARN05= "WARNING 05: minimum slope used for Conduit"
 val WARN06= "WARNING 06: dry weather time step increased to the wet weather time step"
 val WARN07= "WARNING 07: routing time step reduced to the wet weather time step"
 val WARN08= "WARNING 08: elevation drop exceeds length for Conduit"
 val WARN09= "WARNING 09: time series interval greater than recording interval for Rain Gage"
 val WARN10= "WARNING 10: crest elevation is below downstream invert for regulator Link"

  // Analysis Option Keywords
 val  w_FLOW_UNITS    ="FLOW_UNITS"
 val  w_INFIL_MODEL   ="INFILTRATION"
 val  w_ROUTE_MODEL   ="FLOW_ROUTING"
 val  w_START_DATE    =    "START_DATE"
 val  w_START_TIME     =   "START_TIME"
 val  w_END_DATE      =  "END_DATE"
 val  w_END_TIME      =    "END_TIME"
 val  w_REPORT_START_DATE= "REPORT_START_DATE"
 val  w_REPORT_START_TIME= "REPORT_START_TIME"
 val  w_SWEEP_START   ="SWEEP_START"
 val  w_SWEEP_END     ="SWEEP_END"
 val  w_START_DRY_DAYS =   "DRY_DAYS"
 val  w_WET_STEP      =    "WET_STEP"
 val  w_DRY_STEP      =    "DRY_STEP"
 val  w_ROUTE_STEP    =    "ROUTING_STEP"
 val  w_REPORT_STEP   =    "REPORT_STEP"
 val  w_ALLOW_PONDING =    "ALLOW_PONDING"
 val  w_INERT_DAMPING =    "INERTIAL_DAMPING"
 val  w_SLOPE_WEIGHTING =  "SLOPE_WEIGHTING"
 val  w_VARIABLE_STEP =    "VARIABLE_STEP"
 val  w_NORMAL_FLOW_LTD =  "NORMAL_FLOW_LIMITED"
 val  w_LENGTHENING_STEP=  "LENGTHENING_STEP"
 val  w_MIN_SURFAREA    =  "MIN_SURFAREA"
 val  w_COMPATIBILITY   =  "COMPATIBILITY"
 val  w_SKIP_STEADY_STATE= "SKIP_STEADY_STATE"
 val  w_TEMPDIR        =   "TEMPDIR"
 val  w_IGNORE_RAINFALL=   "IGNORE_RAINFALL"
 val  w_FORCE_MAIN_EQN =   "FORCE_MAIN_EQUATION"
 val  w_LINK_OFFSETS   =   "LINK_OFFSETS"
 val  w_MIN_SLOPE      =   "MIN_SLOPE"
 val  w_IGNORE_SNOWMELT=   "IGNORE_SNOWMELT"
 val  w_IGNORE_GWATER  =   "IGNORE_GROUNDWATER"
 val  w_IGNORE_ROUTING =   "IGNORE_ROUTING"
 val  w_IGNORE_QUALITY =   "IGNORE_QUALITY"
 val  w_MAX_TRIALS     =   "MAX_TRIALS"
 val  w_HEAD_TOL       =   "HEAD_TOLERANCE"
 val  w_SYS_FLOW_TOL   =   "SYS_FLOW_TOL"
 val  w_LAT_FLOW_TOL   =   "LAT_FLOW_TOL"
 val  w_IGNORE_RDII    =   "IGNORE_RDII"                                     //(5.1.004)
 val  w_MIN_ROUTE_STEP =   "MINIMUM_STEP"                                    //(5.1.008)
 val  w_NUM_THREADS    =   "THREADS"                                         //(5.1.008)

  // Flow Units
 val  w_CFS      ="CFS"
 val  w_GPM      ="GPM"
 val  w_MGD      ="MGD"
 val  w_CMS      ="CMS"
 val  w_LPS      ="LPS"
 val  w_MLD      ="MLD"

  // Flow Routing Methods
 val  w_NF        ="NF"
 val  w_KW        ="KW"
 val  w_EKW       ="EKW"
 val  w_DW        ="DW"

 val  w_STEADY        ="STEADY"
 val  w_KINWAVE       ="KINWAVE"
 val  w_XKINWAVE      ="XKINWAVE"
 val  w_DYNWAVE       ="DYNWAVE"

  // Infiltration Methods
 val  w_HORTON         ="HORTON"
 val  w_MOD_HORTON     ="MODIFIED_HORTON"
 val  w_GREEN_AMPT     ="GREEN_AMPT"
 val  w_CURVE_NUMEBR   ="CURVE_NUMBER"

  // Normal Flow Criteria
 val  w_SLOPE          ="SLOPE"
 val  w_FROUDE         ="FROUDE"
 val  w_BOTH           ="BOTH"

  // Snowmelt Data Keywords
 val  w_WINDSPEED     =    "WINDSPEED"
 val  w_SNOWMELT      =    "SNOWMELT"
 val  w_ADC           =    "ADC"
 val  w_PLOWABLE      =    "PLOWABLE"

  // swmm.Evaporation Data Options
 val  w_CONSTANT       ="CONSTANT"
 val  w_TIMESERIES     ="TIMESERIES"
 val  w_TEMPERATURE    ="TEMPERATURE"
 val  w_FILE           ="FILE"
 val  w_RECOVERY       ="RECOVERY"
 val  w_DRYONLY        ="DRY_ONLY"

  // DWF Time Pattern Types
 val  w_MONTHLY        ="MONTHLY"
 val  w_DAILY          ="DAILY"
 val  w_HOURLY         ="HOURLY"
 val  w_WEEKEND        ="WEEKEND"

  // Rainfall Record Types
 val  w_INTENSITY      ="INTENSITY"
// val  w_VOLUME         ="VOLUME"
 val  w_CUMULATIVE     ="CUMULATIVE"

  // Unit Hydrograph Types
 val  w_SHORT          ="SHORT"
 val  w_MEDIUM         ="MEDIUM"
 val  w_LONG           ="LONG"

  // Internal Runoff Routing Options
 val  w_OUTLET         ="OUTLET"
 val  w_IMPERV         ="IMPERV"
 val  w_PERV           ="PERV"

  // Outfall Node Types
 val  w_FREE           ="FREE"
 val  w_FIXED          ="FIXED"
 val  w_TIDAL          ="TIDAL"
 val  w_CRITICAL       ="CRITICAL"
 val  w_NORMAL         ="NORMAL"

  // Flow Divider Node Types
 val  w_FUNCTIONAL     ="FUNCTIONAL"
 val  w_TABULAR        ="TABULAR"
 val  w_CUTOFF         ="CUTOFF"
 val  w_OVERFLOW       ="OVERFLOW"

  // Pump Curve Types
 val  w_TYPE1          ="TYPE1"
 val  w_TYPE2          ="TYPE2"
 val  w_TYPE3          ="TYPE3"
 val  w_TYPE4          ="TYPE4"
 val  w_IDEAL          ="IDEAL"

  // Pump Curve Variables
 val  w_VOLUME         ="VOLUME"
 val  w_DEPTH          ="DEPTH"
 val  w_HEAD           ="HEAD"

  // Orifice Types
 val  w_SIDE           ="SIDE"
 val  w_BOTTOM         ="BOTTOM"

  // swmm.Weir Types
 val  w_TRANSVERSE     ="TRANSVERSE"
 val  w_SIDEFLOW       ="SIDEFLOW"
 val  w_VNOTCH         ="V-NOTCH"

  // Conduit Cross-Section Shapes
 val  w_DUMMY          ="DUMMY"
 val  w_CIRCULAR       ="CIRCULAR"
 val  w_FILLED_CIRCULAR=   "FILLED_CIRCULAR"
 val  w_RECT_CLOSED    ="RECT_CLOSED"
 val  w_RECT_OPEN      ="RECT_OPEN"
 val  w_TRAPEZOIDAL    ="TRAPEZOIDAL"
 val  w_TRIANGULAR     ="TRIANGULAR"
 val  w_PARABOLIC      ="PARABOLIC"
 val  w_POWERFUNC      ="POWER"
 val  w_RECT_TRIANG    ="RECT_TRIANGULAR"
 val  w_RECT_ROUND     ="RECT_ROUND"
 val  w_MOD_BASKET     ="MODBASKETHANDLE"
 val  w_HORIZELLIPSE   ="HORIZ_ELLIPSE"
 val  w_VERTELLIPSE    ="VERT_ELLIPSE"
 val  w_ARCH           ="ARCH"
 val  w_EGGSHAPED      ="EGG"
 val  w_HORSESHOE      ="HORSESHOE"
 val  w_GOTHIC         ="GOTHIC"
 val  w_CATENARY       ="CATENARY"
 val  w_SEMIELLIPTICAL =   "SEMIELLIPTICAL"
 val  w_BASKETHANDLE   ="BASKETHANDLE"
 val  w_SEMICIRCULAR   ="SEMICIRCULAR"
 val  w_IRREGULAR      ="IRREGULAR"
 val  w_CUSTOM         ="CUSTOM"
 val  w_FORCE_MAIN     ="FORCE_MAIN"
 val  w_H_W            ="H-W"
 val  w_D_W            ="D-W"

  // Link Offset Options
 val  w_ELEVATION      ="ELEVATION"

  // swmm.Transect Data Input Codes
 val  w_NC             ="NC"
 val  w_X1             ="X1"
 val  w_GR             ="GR"

  // Rain Volume Units
 val  w_INCHES         ="IN"
 val  w_MMETER         ="MM"

  // Flow Volume Units
 val  w_MGAL           ="10^6 gal"
 val  w_MLTRS          ="10^6 ltr"
 val  w_GAL            ="gal"
 val  w_LTR            ="ltr"

  // Ponded Depth Units
 val  w_PONDED_FEET    ="Feet"
 val  w_PONDED_METERS  ="Meters"

  // Concentration Units
 val  w_MGperL         ="MG/L"
 val  w_UGperL         ="UG/L"
 val  w_COUNTperL      ="#/L"

  // Mass Units
 val  w_MG             ="MG"
 val  w_UG             ="UG"
 val  w_COUNT          ="#"

  // Load Units
 val  w_LBS            ="lbs"
 val  w_KG             ="kg"
 val  w_LOGN           ="LogN"

  // Pollutant Buildup Functions
 val  w_POW            ="POW"
 val  w_EXP            ="EXP"
 val  w_SAT            ="SAT"
 val  w_EXT            ="EXT"

  // Normalizing Variables for Pollutant Buildup
 val  w_PER_AREA       ="AREA"
 val  w_PER_CURB       ="CURB"

  // Pollutant Washoff Functions
  // (EXP function defined above)
 val  w_RC             ="RC"
 val  w_EMC            ="EMC"

  // Treatment Keywords
 val  w_REMOVAL        ="REMOVAL"
 val  w_RATE           ="RATE"
 val  w_HRT            ="HRT"
 val  w_DT             ="DT"
 val  w_AREA           ="AREA"

  // Curve Types
  //define  w_STORAGE (defined below)
 val  w_DIVERSION      ="DIVERSION"
 //val  w_TIDAL          ="TIDAL"
 val  w_RATING         ="RATING"
 val  w_SHAPE          ="SHAPE"
 val  w_PUMP1          ="PUMP1"
 val  w_PUMP2          ="PUMP2"
 val  w_PUMP3          ="PUMP3"
 val  w_PUMP4          ="PUMP4"

  // Reporting Options
 val  w_INPUT          ="INPUT"
 val  w_CONTINUITY     ="CONTINUITY"
 val  w_FLOWSTATS      ="FLOWSTATS"
 val  w_CONTROLS       ="CONTROL"
 val  w_NODESTATS      ="NODESTATS"

  // Interface File Types
 val  w_RAINFALL       ="RAINFALL"
 val  w_RUNOFF         ="RUNOFF"
 val  w_HOTSTART       ="HOTSTART"
 val  w_RDII           ="RDII"
 val  w_ROUTING        ="ROUTING"
 val  w_INFLOWS        ="INFLOWS"
 val  w_OUTFLOWS       ="OUTFLOWS"

  // Miscellaneous Keywords
 val  w_OFF            ="OFF"
 val  w_ON             ="ON"
 val  w_NO             ="NO"
 val  w_YES            ="YES"
 val  w_NONE           ="NONE"
 val  w_ALL            ="ALL"
 val  w_SCRATCH        ="SCRATCH"
 val  w_USE            ="USE"
 val  w_SAVE           ="SAVE"
 val  w_FULL           ="FULL"
 val  w_PARTIAL        ="PARTIAL"

  // Major Object Types
 val  w_GAGE           ="RAINGAGE"
 val  w_SUBCATCH       ="SUBCATCH"
 val  w_NODE           ="NODE"
 val  w_LINK           ="LINK"
 val  w_POLLUT         ="POLLUTANT"
 val  w_LANDUSE        ="LANDUSE"
 val  w_TSERIES        ="TIME SERIES"
 val  w_TABLE          ="TABLE"
 val  w_UNITHYD        ="HYDROGRAPH"

  // Node Sub-Types
 val  w_JUNCTION       ="JUNCTION"
 val  w_OUTFALL        ="OUTFALL"
 val  w_STORAGE        ="STORAGE"
 val  w_DIVIDER        ="DIVIDER"

  // Link Sub-Types
 val  w_CONDUIT        ="CONDUIT"
 val  w_PUMP           ="PUMP"
 val  w_ORIFICE        ="ORIFICE"
 val  w_WEIR           ="WEIR"

  // Control Rule Keywords
 val  w_RULE           ="RULE"
 val  w_IF             ="IF"
 val  w_AND            ="AND"
 val  w_OR             ="OR"
 val  w_THEN           ="THEN"
 val  w_ELSE           ="ELSE"
 val  w_PRIORITY       ="PRIORITY"

  // External swmm.Inflow Types
 val  w_FLOW           ="FLOW"
 val  w_CONCEN         ="CONCEN"
 val  w_MASS           ="MASS"

  // Variable Units
 val  w_FEET           ="FEET"
 val  w_METERS         ="METERS"
 val  w_FPS            ="FT/SEC"
 val  w_MPS            ="M/SEC"
 val  w_PCNT           ="PERCENT"
 val  w_ACRE           ="acre"
 val  w_HECTARE        ="hectare"

  // Input File Sections
 val  ws_TITLE         ="[TITLE"
 val  ws_OPTION        ="[OPTION"
 val  ws_FILE          ="[FILE"
 val  ws_RAINGAGE      ="[RAINGAGE"
 val  ws_TEMP          ="[TEMPERATURE"
 val  ws_EVAP          ="[EVAP"
 val  ws_SUBCATCH      ="[SUBCATCHMENT"
 val  ws_SUBAREA       ="[SUBAREA"
 val  ws_INFIL         ="[INFIL"
 val  ws_AQUIFER       ="[AQUIFER"
 val  ws_GROUNDWATER   ="[GROUNDWATER"
 val  ws_SNOWMELT      ="[SNOWPACK"
 val  ws_JUNCTION      ="[JUNC"
 val  ws_OUTFALL       ="[OUTFALL"
 val  ws_STORAGE       ="[STORAGE"
 val  ws_DIVIDER       ="[DIVIDER"
 val  ws_CONDUIT       ="[CONDUIT"
 val  ws_PUMP          ="[PUMP"
 val  ws_ORIFICE       ="[ORIFICE"
 val  ws_WEIR          ="[WEIR"
 val  ws_OUTLET        ="[OUTLET"
 val  ws_XSECTION      ="[XSECT"
 val  ws_TRANSECT      ="[TRANSECT"
 val  ws_LOSS          ="[LOSS"
 val  ws_CONTROL       ="[CONTROL"
 val  ws_POLLUTANT     ="[POLLUT"
 val  ws_LANDUSE       ="[LANDUSE"
 val  ws_BUILDUP       ="[BUILDUP"
 val  ws_WASHOFF       ="[WASHOFF"
 val  ws_COVERAGE      ="[COVERAGE"
 val  ws_INFLOW        ="[INFLOW"
 val  ws_DWF           ="[DWF"
 val  ws_PATTERN       ="[PATTERN"
 val  ws_RDII          ="[RDII"
 val  ws_UNITHYD       ="[HYDROGRAPH"
 val  ws_LOADING       ="[LOADING"
 val  ws_TREATMENT     ="[TREATMENT"
 val  ws_CURVE         ="[CURVE"
 val  ws_TIMESERIES    ="[TIMESERIES"
 val  ws_REPORT        ="[REPORT"
 val  ws_MAP           ="[MAP"
 val  ws_COORDINATE    ="[COORDINATE"
 val  ws_VERTICES      ="[VERTICES"
 val  ws_POLYGON       ="[POLYGON"
 val  ws_SYMBOL        ="[SYMBOL"
 val  ws_LABEL         ="[LABEL"
 val  ws_BACKDROP      ="[BACKDROP"
 val  ws_TAG           ="[TAG"
 val  ws_PROFILE       ="[PROFILE"
 val  ws_LID_CONTROL   ="[LID_CONTROL"
 val  ws_LID_USAGE     ="[LID_USAGE"
 val  ws_GW_FLOW       ="[GW_FLOW"     //Deprecated                       //(5.1.007)
 val  ws_GWF           ="[GWF"                                            //(5.1.007)
 val  ws_ADJUST        ="[ADJUSTMENT"                                     //(5.1.007)

}
