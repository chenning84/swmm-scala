package swmm.configdata.jnodes;

import com.udojava.evalex.Expression;

import org.joda.time.DateTime;

import java.io.File;

/**
 * Created by ning on 10/28/15.
 */
class SObjects {
//-----------------------------------------------------------------------------
//   objects.h
//
//   Project: EPA SWMM5
//   Version: 5.1
//   Date:    03/19/14  (Build 5.1.000)
//            09/15/14  (Build 5.1.007)
//            03/19/15  (Build 5.1.008)
//   Author:  L. Rossman (EPA)
//            M. Tryby (EPA)
//            R. Dickinson (CDM)
//
//   Definitions of data structures.
//
//   Most SWMM 5 "objects" are represented as C data structures.
//
//   The units shown next to each structure's properties are SWMM's
//   internal units and may be different than the units required
//   for the property as it appears in the input file.
//
//   In many structure definitions, a blank line separates the set of
//   input properties from the set of computed output properties.
//
//   Build 5.1.007:
//   - Data structure for monthly adjustments of temperature, evaporation,
//     and rainfall added.
//   - User-supplied equation for deep GW flow added to subcatchment object.
//   - Exfiltration added to storage node object.
//   - Surcharge option added to weir object.
//
//   Build 5.1.008:
//   - Route to subcatchment option added to Outfall data structure.
//   - Hydraulic conductivity added to monthly adjustments data structure.
//   - Total LID drain flow and outfall runon added to Runoff Totals.
//   - Groundwater statistics object added.
//   - Maximum depth for reporting times added to node statistics object.
//
//-----------------------------------------------------------------------------

//    #include "mathexpr.h"
//            #include "infil.h"
//            #include "exfil.h"                                                              //(5.1.007)
//--------------------
// FLOW DIVIDER OBJECT
//--------------------
public class      TDivider
{
    int         link;              // index of link with diverted flow
    int         type;              // divider type code
    double      qMin;              // minimum inflow for diversion (cfs)
    double      qMax;              // flow when weir is full (cfs)
    double      dhMax;             // height of weir (ft)
    double      cWeir;             // weir discharge coeff.
    int         flowCurve;         // index of inflow v. diverted flow curve

    public TDivider(int link, int type, double qMin, double qMax, double dhMax, double cWeir, int flowCurve) {
        this.link = link;
        this.type = type;
        this.qMin = qMin;
        this.qMax = qMax;
        this.dhMax = dhMax;
        this.cWeir = cWeir;
        this.flowCurve = flowCurve;
    }
}


    //-----------------
// FILE INFORMATION
//-----------------
   public class  TFile
    {
        String          name;     // file name
        char          mode;                 // NO_FILE, SCRATCH, USE, or SAVE
        char          state;                // current state (OPENED, CLOSED)
        File            file;                 // FILE structure pointer

        public TFile(String name, char mode, char state, File file) {
            this.name = name;
            this.mode = mode;
            this.state = state;
            this.file = file;
        }
    }

//-----------------------------------------
// LINKED LIST ENTRY FOR TABLES/TIME SERIES
//-----------------------------------------
    public class   TTableEntry
    {
        double  x;
        double  y;
          TTableEntry next;

        public TTableEntry(double x, double y, TTableEntry next) {
            this.x = x;
            this.y = y;
            this.next = next;
        }
    }



//-------------------------
// CURVE/TIME SERIES OBJECT
//-------------------------
    public class  TTable
    {
        String         ID;              // Table/time series ID
        int           curveType;       // type of curve tabulated
        int           refersTo;        // reference to some other object
        double        dxMin;           // smallest x-value interval
        double        lastDate;        // last input date for time series
        double        x1, x2;          // current bracket on x-values
        double        y1, y2;          // current bracket on y-values
        TTableEntry  firstEntry;      // first data point
        TTableEntry  lastEntry;       // last data point
        TTableEntry  thisEntry;       // current data point
        TFile         file;            // external data file

        public TTable(String ID, int curveType, int refersTo, double dxMin, double lastDate,
                      double x1, double x2, double y1, double y2, TTableEntry firstEntry, TTableEntry lastEntry, TTableEntry thisEntry, TFile file) {
            this.ID = ID;
            this.curveType = curveType;
            this.refersTo = refersTo;
            this.dxMin = dxMin;
            this.lastDate = lastDate;
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.firstEntry = firstEntry;
            this.lastEntry = lastEntry;
            this.thisEntry = thisEntry;
            this.file = file;
        }
    }


    //-----------------
    // RAIN GAGE OBJECT
    //-----------------
    public class  TGage
    {
        String         ID;              // raingage name
        int           dataSource;      // data from time series or file
        int           tSeries;         // rainfall data time series index
        String          fname; // name of rainfall data file
        String          staID; // station number
        DateTime      startFileDate;   // starting date of data read from file
        DateTime      endFileDate;     // ending date of data read from file
        int           rainType;        // intensity, volume, cumulative
        int           rainInterval;    // recording time interval (seconds)
        int           rainUnits;       // rain depth units (US or SI)
        double        snowFactor;      // snow catch deficiency correction

        long          startFilePos;    // starting byte position in Rain file
        long          endFilePos;      // ending byte position in Rain file
        long          currentFilePos;  // current byte position in Rain file
        double        rainAccum;       // cumulative rainfall
        double        unitsFactor;     // units conversion factor (to inches or mm)
        DateTime      startDate;       // start date of current rainfall
        DateTime      endDate;         // end date of current rainfall
        DateTime      nextDate;        // next date with recorded rainfall
        double        rainfall;        // current rainfall (in/hr or mm/hr)
        double        nextRainfall;    // next rainfall (in/hr or mm/hr)
        double        reportRainfall;  // rainfall value used for reported results
        int           coGage;          // index of gage with same rain timeseries
        int           isUsed;          // TRUE if gage used by any subcatchment
        int           isCurrent;       // TRUE if gage's rainfall is current

        public TGage(String ID, int dataSource, int tSeries, String fname, String staID, DateTime startFileDate,
                     DateTime endFileDate, int rainType, int rainInterval, int rainUnits, double snowFactor, long startFilePos,
                     long endFilePos, long currentFilePos, double rainAccum, double unitsFactor, DateTime startDate,
                     DateTime endDate, DateTime nextDate, double rainfall, double nextRainfall, double reportRainfall, int coGage, int isUsed, int isCurrent) {
            this.ID = ID;
            this.dataSource = dataSource;
            this.tSeries = tSeries;
            this.fname = fname;
            this.staID = staID;
            this.startFileDate = startFileDate;
            this.endFileDate = endFileDate;
            this.rainType = rainType;
            this.rainInterval = rainInterval;
            this.rainUnits = rainUnits;
            this.snowFactor = snowFactor;
            this.startFilePos = startFilePos;
            this.endFilePos = endFilePos;
            this.currentFilePos = currentFilePos;
            this.rainAccum = rainAccum;
            this.unitsFactor = unitsFactor;
            this.startDate = startDate;
            this.endDate = endDate;
            this.nextDate = nextDate;
            this.rainfall = rainfall;
            this.nextRainfall = nextRainfall;
            this.reportRainfall = reportRainfall;
            this.coGage = coGage;
            this.isUsed = isUsed;
            this.isCurrent = isCurrent;
        }
    }


    //-------------------
// TEMPERATURE OBJECT
//-------------------
    public class  TTemp
    {
        int           dataSource;      // data from time series or file
        int           tSeries;         // temperature data time series index
        DateTime      fileStartDate;   // starting date of data read from file
        double        elev;            // elev. of study area (ft)
        double        anglat;          // latitude (degrees)
        double        dtlong;          // longitude correction (hours)

        double        ta;              // air temperature (deg F)
        double        tmax;            // previous day's max. temp. (deg F)
        double        ea;              // saturation vapor pressure (in Hg)
        double        gamma;           // psychrometric constant
        double        tanAnglat;       // tangent of latitude angle

        public TTemp(int dataSource, int tSeries, DateTime fileStartDate, double elev, double anglat,
                     double dtlong, double ta, double tmax, double ea, double gamma, double tanAnglat) {
            this.dataSource = dataSource;
            this.tSeries = tSeries;
            this.fileStartDate = fileStartDate;
            this.elev = elev;
            this.anglat = anglat;
            this.dtlong = dtlong;
            this.ta = ta;
            this.tmax = tmax;
            this.ea = ea;
            this.gamma = gamma;
            this.tanAnglat = tanAnglat;
        }
    }


    //-----------------
// WINDSPEED OBJECT
//-----------------
    public class  TWind
    {
        int          type;             // monthly or file data
        double[]       aws= new double[12];          // monthly avg. wind speed (mph)

        double        ws;              // wind speed (mph)

        public TWind(int type, double[] aws, double ws) {
            this.type = type;
            this.aws = aws;
            this.ws = ws;
        }
    }


    //------------
// SNOW OBJECT
//------------
public class  TSnow
{
double        snotmp;          // temp. dividing rain from snow (deg F)
double        tipm;            // antecedent temp. index parameter
double        rnm;             // ratio of neg. melt to melt coeff.
double[][]        adc= new double[2][10];      // areal depletion curves (pervious &
// imperv. area curves w/ 10 pts.each)

double        season;          // snowmelt season
double        removed;         // total snow plowed out of system (ft3)

public TSnow(double snotmp, double tipm, double rnm, double[][] adc, double season, double removed) {
    this.snotmp = snotmp;
    this.tipm = tipm;
    this.rnm = rnm;
    this.adc = adc;
    this.season = season;
    this.removed = removed;
}
}


    //-------------------
// EVAPORATION OBJECT
//-------------------
    public class  TEvap
    {
        int          type;            // type of evaporation data
        int          tSeries;         // time series index
        double []      monthlyEvap=new double[12]; // monthly evaporation values
        double []      panCoeff = new double[12];    // monthly pan coeff. values
        int          recoveryPattern; // soil recovery factor pattern
        int          dryOnly;         // true if evaporation only in dry periods

        double       rate;            // current evaporation rate (ft/sec)
        double       recoveryFactor;  // current soil recovery factor

        public TEvap(int type, int tSeries, double[] monthlyEvap, double[] panCoeff, int recoveryPattern, int dryOnly, double rate, double recoveryFactor) {
            this.type = type;
            this.tSeries = tSeries;
            this.monthlyEvap = monthlyEvap;
            this.panCoeff = panCoeff;
            this.recoveryPattern = recoveryPattern;
            this.dryOnly = dryOnly;
            this.rate = rate;
            this.recoveryFactor = recoveryFactor;
        }
    }

    ////  Added for release 5.1.007  ////                                          //(5.1.007)
//-------------------
// ADJUSTMENTS OBJECT
//-------------------
   public class  TAdjust
    {
        double[]       temp=new double[12];        // monthly temperature adjustments (deg F)
        double[]       evap=new double[12];        // monthly evaporation adjustments (ft/s)
        double[]       rain=new double[12];        // monthly rainfall adjustment multipliers
        double[]       hydcon=new double[12];      // hyd. conductivity adjustment multipliers  //(5.1.008)
        double       rainFactor;      // current rainfall adjustment multiplier
        double       hydconFactor;    // current conductivity multiplier           //(5.1.008)

        public TAdjust(double[] temp, double[] evap, double[] rain, double[] hydcon, double rainFactor, double hydconFactor) {
            this.temp = temp;
            this.evap = evap;
            this.rain = rain;
            this.hydcon = hydcon;
            this.rainFactor = rainFactor;
            this.hydconFactor = hydconFactor;
        }
    }


    //-------------------
// AQUIFER OBJECT
//-------------------
    public class      TAquifer
    {
        String       ID;               // aquifer name
        double      porosity;         // soil porosity
        double      wiltingPoint;     // soil wilting point
        double      fieldCapacity;    // soil field capacity
        double      conductivity;     // soil hyd. conductivity (ft/sec)
        double      conductSlope;     // slope of conductivity v. moisture curve
        double      tensionSlope;     // slope of tension v. moisture curve
        double      upperEvapFrac;    // evaporation available in upper zone
        double      lowerEvapDepth;   // evap depth existing in lower zone (ft)
        double      lowerLossCoeff;   // coeff. for losses to deep GW (ft/sec)
        double      bottomElev;       // elevation of bottom of aquifer (ft)
        double      waterTableElev;   // initial water table elevation (ft)
        double      upperMoisture;    // initial moisture content of unsat. zone
        int         upperEvapPat;     // monthly upper evap. adjustment factors

        public TAquifer(String ID, double porosity, double wiltingPoint, double fieldCapacity, double conductivity, double conductSlope,
                        double tensionSlope, double upperEvapFrac, double lowerEvapDepth, double lowerLossCoeff, double bottomElev,
                        double waterTableElev, double upperMoisture, int upperEvapPat) {
            this.ID = ID;
            this.porosity = porosity;
            this.wiltingPoint = wiltingPoint;
            this.fieldCapacity = fieldCapacity;
            this.conductivity = conductivity;
            this.conductSlope = conductSlope;
            this.tensionSlope = tensionSlope;
            this.upperEvapFrac = upperEvapFrac;
            this.lowerEvapDepth = lowerEvapDepth;
            this.lowerLossCoeff = lowerLossCoeff;
            this.bottomElev = bottomElev;
            this.waterTableElev = waterTableElev;
            this.upperMoisture = upperMoisture;
            this.upperEvapPat = upperEvapPat;
        }
    }


    ////  Added to release 5.1.008.  ////                                          //(5.1.008)
//-----------------------
// GROUNDWATER STATISTICS
//-----------------------
    public class      TGWaterStats
    {
        double       infil;           // total infiltration (ft)
        double       evap;            // total evaporation (ft)
        double       latFlow;         // total lateral outflow (ft)
        double       deepFlow;        // total flow to deep aquifer (ft)
        double       avgUpperMoist;   // avg. upper zone moisture
        double       finalUpperMoist; // final upper zone moisture
        double       avgWaterTable;   // avg. water table height (ft)
        double       finalWaterTable; // final water table height (ft)
        double       maxFlow;         // max. lateral outflow (cfs)

        public TGWaterStats(double infil, double evap, double latFlow, double deepFlow, double avgUpperMoist,
                            double finalUpperMoist, double avgWaterTable, double finalWaterTable, double maxFlow) {
            this.infil = infil;
            this.evap = evap;
            this.latFlow = latFlow;
            this.deepFlow = deepFlow;
            this.avgUpperMoist = avgUpperMoist;
            this.finalUpperMoist = finalUpperMoist;
            this.avgWaterTable = avgWaterTable;
            this.finalWaterTable = finalWaterTable;
            this.maxFlow = maxFlow;
        }
    }


    //------------------------
// GROUNDWATER OBJECT
//------------------------
    public class     TGroundwater
    {
        int           aquifer;        // index of associated gw aquifer
        int           node;           // index of node receiving gw flow
        double        surfElev;       // elevation of ground surface (ft)
        double        a1, b1;         // ground water outflow coeff. & exponent
        double        a2, b2;         // surface water outflow coeff. & exponent
        double        a3;             // surf./ground water interaction coeff.
        double        fixedDepth;     // fixed surface water water depth (ft)
        double        nodeElev;       // elevation of receiving node invert (ft)
        double        bottomElev;     // bottom elevation of lower GW zone (ft)
        double        waterTableElev; // initial water table elevation (ft)
        double        upperMoisture;  // initial moisture content of unsat. zone

        double        theta;          // upper zone moisture content
        double        lowerDepth;     // depth of saturated zone (ft)
        double        oldFlow;        // gw outflow from previous time period (cfs)
        double        newFlow;        // gw outflow from current time period (cfs)
        double        evapLoss;       // evaporation loss rate (ft/sec)
        double        maxInfilVol;    // max. infil. upper zone can accept (ft)
        TGWaterStats  stats;          // gw statistics                             //(5.1.008)

        public TGroundwater(int aquifer, int node, double surfElev, double a1, double b1, double a2, double b2, double a3,
                            double fixedDepth, double nodeElev, double bottomElev, double waterTableElev, double upperMoisture,
                            double theta, double lowerDepth, double oldFlow, double newFlow, double evapLoss, double maxInfilVol, TGWaterStats stats) {
            this.aquifer = aquifer;
            this.node = node;
            this.surfElev = surfElev;
            this.a1 = a1;
            this.b1 = b1;
            this.a2 = a2;
            this.b2 = b2;
            this.a3 = a3;
            this.fixedDepth = fixedDepth;
            this.nodeElev = nodeElev;
            this.bottomElev = bottomElev;
            this.waterTableElev = waterTableElev;
            this.upperMoisture = upperMoisture;
            this.theta = theta;
            this.lowerDepth = lowerDepth;
            this.oldFlow = oldFlow;
            this.newFlow = newFlow;
            this.evapLoss = evapLoss;
            this.maxInfilVol = maxInfilVol;
            this.stats = stats;
        }
    }

    //----------------
// SNOWMELT OBJECT
// Snowmelt objects contain parameters that describe the melting
// process of snow packs on 3 different types of surfaces:
//   1 - plowable impervious area
//   2 - non-plowable impervious area
//   3 - pervious area
//----------------
    public class      TSnowmelt
    {
        String         ID;              // snowmelt parameter set name
        double        snn;             // fraction of impervious area plowable
        double[]      si=new double[3];           // snow depth for 100% cover
        double[]        dhmax=new double[3];        // max. melt coeff. for each surface (ft/sec-F)
        double[]        tbase=new double[3];        // base temp. for melting (F)
        double[]        fwfrac=new double[3];       // free water capacity / snow depth
        double []       wsnow=new double[3];        // initial snow depth on each surface (ft)
        double[]        fwnow=new double[3];        // initial free water in snow pack (ft)
        double        weplow;          // depth at which plowing begins (ft)
        double[]        sfrac=new double[5];        // fractions moved to other areas by plowing
        int           toSubcatch;      // index of subcatch receiving plowed snow

        double[]        dhm=new double[3];          // melt coeff. for each surface (ft/sec-F)

        public TSnowmelt(String ID, double snn, double[] si, double[] dhmax, double[] tbase,
                         double[] fwfrac, double[] wsnow, double[] fwnow, double weplow, double[] sfrac, int toSubcatch, double[] dhm) {
            this.ID = ID;
            this.snn = snn;
            this.si = si;
            this.dhmax = dhmax;
            this.tbase = tbase;
            this.fwfrac = fwfrac;
            this.wsnow = wsnow;
            this.fwnow = fwnow;
            this.weplow = weplow;
            this.sfrac = sfrac;
            this.toSubcatch = toSubcatch;
            this.dhm = dhm;
        }
    }


    //----------------
// SNOWPACK OBJECT
// Snowpack objects describe the state of the snow melt process on each
// of 3 types of snow surfaces.
//----------------

    public class      TSnowpack
    {
        int           snowmeltIndex;   // index of snow melt parameter set
        double[]        fArea=new double[3];        // fraction of total area of each surface
        double[]        wsnow=new double[3];        // depth of snow pack (ft)
        double[]        fw=new double[3];           // depth of free water in snow pack (ft)
        double[]        coldc=new double[3];        // cold content of snow pack
        double[]        ati=new double[3];          // antecedent temperature index (deg F)
        double[]        sba=new double[3];          // initial ASC of linear ADC
        double[]        awe=new double[3];          // initial AWESI of linear ADC
        double []       sbws=new double[3];         // final AWESI of linear ADC
        double[]        imelt=new double[3];        // immediate melt (ft)

        public TSnowpack(int snowmeltIndex, double[] fArea, double[] wsnow, double[] fw, double[] coldc,
                         double[] ati, double[] sba, double[] awe, double[] sbws, double[] imelt) {
            this.snowmeltIndex = snowmeltIndex;
            this.fArea = fArea;
            this.wsnow = wsnow;
            this.fw = fw;
            this.coldc = coldc;
            this.ati = ati;
            this.sba = sba;
            this.awe = awe;
            this.sbws = sbws;
            this.imelt = imelt;
        }
    }


    //---------------
// SUBAREA OBJECT
// An array of 3 subarea objects is associated with each subcatchment object.
// They describe the runoff process on 3 types of surfaces:
//   1 - impervious with no depression storage
//   2 - impervious with depression storage
//   3 - pervious
//---------------

    public class      TSubarea
    {
        int           routeTo;         // code indicating where outflow is sent
        double        fOutlet;         // fraction of outflow to outlet
        double        N;               // Manning's n
        double        fArea;           // fraction of total area
        double        dStore;          // depression storage (ft)

        double        alpha;           // overland flow factor
        double        inflow;          // inflow rate (ft/sec)
        double        runoff;          // runoff rate (ft/sec)
        double        depth;           // depth of surface runoff (ft)

        public TSubarea(int routeTo, double fOutlet, double n, double fArea, double dStore, double alpha, double inflow, double runoff, double depth) {
            this.routeTo = routeTo;
            this.fOutlet = fOutlet;
            N = n;
            this.fArea = fArea;
            this.dStore = dStore;
            this.alpha = alpha;
            this.inflow = inflow;
            this.runoff = runoff;
            this.depth = depth;
        }
    }
//-------------------------
// LAND AREA LANDUSE FACTOR
//-------------------------
    public class      TLandFactor
    {
        double        fraction;        // fraction of land area with land use
        double       buildup;         // array of buildups for each pollutant
        DateTime      lastSwept;       // date/time of last street sweeping
    }


    //--------------------
// SUBCATCHMENT OBJECT
//--------------------
    public class      TSubcatch
    {
        String         ID;              // subcatchment name
        char          rptFlag;         // reporting flag
        int           gage;            // raingage index
        int           outNode;         // outlet node index
        int           outSubcatch;     // outlet subcatchment index
        int           infil;           // infiltration object index
        TSubarea[]      subArea=new TSubarea[3];      // sub-area data
        double        width;           // overland flow width (ft)
        double        area;            // area (ft2)
        double        fracImperv;      // fraction impervious
        double        slope;           // slope (ft/ft)
        double        curbLength;      // total curb length (ft)
        double       initBuildup;     // initial pollutant buildup (mass/ft2)
        TLandFactor  landFactor;      // array of land use factors
        TGroundwater groundwater;     // associated groundwater data
        Expression     gwLatFlowExpr;   // user-supplied lateral outflow expression  //(5.1.007)
        Expression    gwDeepFlowExpr;  // user-supplied deep percolation expression //(5.1.007)
        TSnowpack    snowpack;        // associated snow pack data

        double        lidArea;         // area devoted to LIDs (ft2)
        double        rainfall;        // current rainfall (ft/sec)
        double        evapLoss;        // current evap losses (ft/sec)
        double        infilLoss;       // current infil losses (ft/sec)
        double        runon;           // runon from other subcatchments (cfs)
        double        oldRunoff;       // previous runoff (cfs)
        double        newRunoff;       // current runoff (cfs)
        double        oldSnowDepth;    // previous snow depth (ft)
        double        newSnowDepth;    // current snow depth (ft)
        double        oldQual;         // previous runoff quality (mass/L)
        double        newQual;         // current runoff quality (mass/L)
        double        pondedQual;      // ponded surface water quality (mass)
        double        totalLoad;       // total washoff load (lbs or kg)

        public TSubcatch(String ID, char rptFlag, int gage, int outNode, int outSubcatch, int infil,
                         TSubarea[] subArea, double width, double area, double fracImperv, double slope, double curbLength,
                         double initBuildup, TLandFactor landFactor, TGroundwater groundwater, Expression gwLatFlowExpr,
                         Expression gwDeepFlowExpr, TSnowpack snowpack, double lidArea, double rainfall, double evapLoss, double infilLoss, double runon,
                         double oldRunoff, double newRunoff, double oldSnowDepth, double newSnowDepth, double oldQual, double newQual, double pondedQual,
                         double totalLoad) {
            this.ID = ID;
            this.rptFlag = rptFlag;
            this.gage = gage;
            this.outNode = outNode;
            this.outSubcatch = outSubcatch;
            this.infil = infil;
            this.subArea = subArea;
            this.width = width;
            this.area = area;
            this.fracImperv = fracImperv;
            this.slope = slope;
            this.curbLength = curbLength;
            this.initBuildup = initBuildup;
            this.landFactor = landFactor;
            this.groundwater = groundwater;
            this.gwLatFlowExpr = gwLatFlowExpr;
            this.gwDeepFlowExpr = gwDeepFlowExpr;
            this.snowpack = snowpack;
            this.lidArea = lidArea;
            this.rainfall = rainfall;
            this.evapLoss = evapLoss;
            this.infilLoss = infilLoss;
            this.runon = runon;
            this.oldRunoff = oldRunoff;
            this.newRunoff = newRunoff;
            this.oldSnowDepth = oldSnowDepth;
            this.newSnowDepth = newSnowDepth;
            this.oldQual = oldQual;
            this.newQual = newQual;
            this.pondedQual = pondedQual;
            this.totalLoad = totalLoad;
        }
    }



    //-----------------------
// TIME PATTERN DATA
//-----------------------
    public class    TPattern
    {
        String        ID;               // time pattern name
        int          type;             // time pattern type code
        int          count;            // number of factors
        double[]       factor=new double[24];       // time pattern factors

        public TPattern(String ID, int type, int count, double[] factor) {
            this.ID = ID;
            this.type = type;
            this.count = count;
            this.factor = factor;
        }
    }



    //------------------------------
// DIRECT EXTERNAL INFLOW OBJECT
//------------------------------
    public class  TExtInflow
    {
        int            param;         // pollutant index (flow = -1)
        int            type;          // CONCEN or MASS
        int            tSeries;       // index of inflow time series
        int            basePat;       // baseline time pattern
        double         cFactor;       // units conversion factor for mass inflow
        double         baseline;      // constant baseline value
        double         sFactor;       // time series scaling factor
        TExtInflow      next;       // pointer to next inflow data object

        public TExtInflow(int param, int type, int tSeries, int basePat, double cFactor, double baseline, double sFactor, TExtInflow next) {
            this.param = param;
            this.type = type;
            this.tSeries = tSeries;
            this.basePat = basePat;
            this.cFactor = cFactor;
            this.baseline = baseline;
            this.sFactor = sFactor;
            this.next = next;
        }
    }
 //   public class       ExtInflow TExtInflow;


    //-------------------------------
// DRY WEATHER FLOW INFLOW OBJECT
//-------------------------------
    public class  TDwfInflow
    {
        int            param;          // pollutant index (flow = -1)
        double         avgValue;       // average value (cfs or concen.)
        int[]            patterns=new int[4];    // monthly, daily, hourly, weekend time patterns
        TDwfInflow next;        // pointer to next inflow data object

        public TDwfInflow(int param, double avgValue, int[] patterns, TDwfInflow next) {
            this.param = param;
            this.avgValue = avgValue;
            this.patterns = patterns;
            this.next = next;
        }
    };
 //   public class       DwfInflow TDwfInflow;


    //-------------------
// RDII INFLOW OBJECT
//-------------------
    public class  TRdiiInflow
    {
        int           unitHyd;         // index of unit hydrograph
        double        area;            // area of sewershed (ft2)

        public TRdiiInflow(int unitHyd, double area) {
            this.unitHyd = unitHyd;
            this.area = area;
        }
    }


    //-----------------------------
// UNIT HYDROGRAPH GROUP OBJECT
//-----------------------------
    public class      TUnitHyd
    {
        String         ID;              // name of the unit hydrograph object
        int           rainGage;        // index of rain gage
        double[][]        iaMax=new double[12][3];    // max. initial abstraction (IA) (in or mm)
        double[][]        iaRecov=new double[12][3];  // IA recovery rate (in/day or mm/day)
        double[][]        iaInit=new double[12][3];   // starting IA (in or mm)
        double[][]        r=new double[12][3];        // fraction of rainfall becoming I&I
        long[] []         tBase=new long[12][3];    // time base of each UH in each month (sec)
        long[][]          tPeak=new long[12][3];    // time to peak of each UH in each month (sec)

        public TUnitHyd(String ID, int rainGage, double[][] iaMax, double[][] iaRecov, double[][] iaInit, double[][] r, long[][] tBase, long[][] tPeak) {
            this.ID = ID;
            this.rainGage = rainGage;
            this.iaMax = iaMax;
            this.iaRecov = iaRecov;
            this.iaInit = iaInit;
            this.r = r;
            this.tBase = tBase;
            this.tPeak = tPeak;
        }
    }


    //-----------------
// TREATMENT OBJECT
//-----------------
    public class      TTreatment
    {
        int          treatType;       // treatment equation type: REMOVAL/CONCEN
        Expression equation;        // treatment eqn. as tokenized math terms
    } ;


//------------
// NODE OBJECT
//------------
public class TNode
{
    String         ID;              // node ID
    int           type;            // node type code
    int           subIndex;        // index of node's sub-category
    char          rptFlag;         // reporting flag
    double        invertElev;      // invert elevation (ft)
    double        initDepth;       // initial storage level (ft)
    double        fullDepth;       // dist. from invert to surface (ft)
    double        surDepth;        // added depth under surcharge (ft)
    double        pondedArea;      // area filled by ponded water (ft2)
    TExtInflow   extInflow;       // pointer to external inflow data
    TDwfInflow   dwfInflow;       // pointer to dry weather flow inflow data
    TRdiiInflow  rdiiInflow;      // pointer to RDII inflow data
    TTreatment   treatment;       // array of treatment data

    int           degree;          // number of outflow links
    char          updated;         // true if state has been updated
    double        crownElev;       // top of highest connecting conduit (ft)
    double        inflow;          // total inflow (cfs)
    double        outflow;         // total outflow (cfs)
    double        losses;          // evap + exfiltration loss (ft3);           //(5.1.007)
    double        oldVolume;       // previous volume (ft3)
    double        newVolume;       // current volume (ft3)
    double        fullVolume;      // max. storage available (ft3)
    double        overflow;        // overflow rate (cfs)
    double        oldDepth;        // previous water depth (ft)
    double        newDepth;        // current water depth (ft)
    double        oldLatFlow;      // previous lateral inflow (cfs)
    double        newLatFlow;      // current lateral inflow (cfs)
    double        oldQual;         // previous quality state
    double        newQual;         // current quality state
    double        oldFlowInflow;   // previous flow inflow
    double        oldNetInflow;    // previous net inflow

    public TNode(String ID, int type, int subIndex, char rptFlag, double invertElev, double initDepth, double fullDepth, double surDepth, double pondedArea, TExtInflow extInflow, TDwfInflow dwfInflow, TRdiiInflow rdiiInflow, TTreatment treatment, int degree, char updated, double crownElev, double inflow, double outflow, double losses, double oldVolume, double newVolume, double fullVolume, double overflow, double oldDepth, double newDepth,
                 double oldLatFlow, double newLatFlow, double oldQual, double newQual, double oldFlowInflow, double oldNetInflow) {
        this.ID = ID;
        this.type = type;
        this.subIndex = subIndex;
        this.rptFlag = rptFlag;
        this.invertElev = invertElev;
        this.initDepth = initDepth;
        this.fullDepth = fullDepth;
        this.surDepth = surDepth;
        this.pondedArea = pondedArea;
        this.extInflow = extInflow;
        this.dwfInflow = dwfInflow;
        this.rdiiInflow = rdiiInflow;
        this.treatment = treatment;
        this.degree = degree;
        this.updated = updated;
        this.crownElev = crownElev;
        this.inflow = inflow;
        this.outflow = outflow;
        this.losses = losses;
        this.oldVolume = oldVolume;
        this.newVolume = newVolume;
        this.fullVolume = fullVolume;
        this.overflow = overflow;
        this.oldDepth = oldDepth;
        this.newDepth = newDepth;
        this.oldLatFlow = oldLatFlow;
        this.newLatFlow = newLatFlow;
        this.oldQual = oldQual;
        this.newQual = newQual;
        this.oldFlowInflow = oldFlowInflow;
        this.oldNetInflow = oldNetInflow;
    }
}


//---------------
// OUTFALL OBJECT
//---------------
public class      TOutfall
{
    int        type;               // outfall type code
    char       hasFlapGate;        // true if contains flap gate
    double     fixedStage;         // fixed outfall stage (ft)
    int        tideCurve;          // index of tidal stage curve
    int        stageSeries;        // index of outfall stage time series
    int        routeTo;            // subcatchment index routed onto            //(5.1.008)
    double     vRouted;            // flow volume routed (ft3)                  //(5.1.008)
    double     wRouted;            // pollutant load routed (mass)              //(5.1.008)

    public TOutfall(int type, char hasFlapGate, double fixedStage, int tideCurve, int stageSeries, int routeTo, double vRouted, double wRouted) {
        this.type = type;
        this.hasFlapGate = hasFlapGate;
        this.fixedStage = fixedStage;
        this.tideCurve = tideCurve;
        this.stageSeries = stageSeries;
        this.routeTo = routeTo;
        this.vRouted = vRouted;
        this.wRouted = wRouted;
    }
}


    //--------------------
// STORAGE UNIT OBJECT
//--------------------
    public class      TStorage
    {
        double      fEvap;             // fraction of evaporation realized
        double      aConst;            // surface area at zero height (ft2)
        double      aCoeff;            // coeff. of area v. height curve
        double      aExpon;            // exponent of area v. height curve
        int         aCurve;            // index of tabulated area v. height curve
        TExfil     exfil;             // ptr. to exfiltration object               //(5.1.007)

        double      hrt;               // hydraulic residence time (sec)
        double      evapLoss;          // evaporation loss (ft3)
        double      exfilLoss;         // exfiltration loss (ft3)                   //(5.1.007)

        public TStorage(double fEvap, double aConst, double aCoeff, double aExpon, int aCurve, TExfil exfil, double hrt, double evapLoss, double exfilLoss) {
            this.fEvap = fEvap;
            this.aConst = aConst;
            this.aCoeff = aCoeff;
            this.aExpon = aExpon;
            this.aCurve = aCurve;
            this.exfil = exfil;
            this.hrt = hrt;
            this.evapLoss = evapLoss;
            this.exfilLoss = exfilLoss;
        }
    }



    //-----------------------------
// CROSS SECTION DATA STRUCTURE
//-----------------------------
    public class      TXsect
    {
        int           type;            // type code of cross section shape
        int           culvertCode;     // type of culvert (if any)
        int           transect;        // index of transect/shape (if applicable)
        double        yFull;           // depth when full (ft)
        double        wMax;            // width at widest point (ft)
        double        ywMax;           // depth at widest point (ft)
        double        aFull;           // area when full (ft2)
        double        rFull;           // hyd. radius when full (ft)
        double        sFull;           // section factor when full (ft^4/3)
        double        sMax;            // section factor at max. flow (ft^4/3)

        // These variables have different meanings depending on section shape
        double        yBot;            // depth of bottom section
        double        aBot;            // area of bottom section
        double        sBot;            // slope of bottom section
        double        rBot;            // radius of bottom section

        public TXsect(int type, int culvertCode, int transect, double yFull,
                      double wMax, double ywMax, double aFull, double rFull, double sFull,
                      double sMax, double yBot, double aBot, double sBot, double rBot) {
            this.type = type;
            this.culvertCode = culvertCode;
            this.transect = transect;
            this.yFull = yFull;
            this.wMax = wMax;
            this.ywMax = ywMax;
            this.aFull = aFull;
            this.rFull = rFull;
            this.sFull = sFull;
            this.sMax = sMax;
            this.yBot = yBot;
            this.aBot = aBot;
            this.sBot = sBot;
            this.rBot = rBot;
        }
    }


//--------------------------------------
// CROSS SECTION TRANSECT DATA STRUCTURE
// int  N_TRANSECT_TBL = 51;       // size of transect geometry tables
//--------------------------------------

    public class   TTransect
    {
        String        ID;                        // section ID
        double       yFull;                     // depth when full (ft)
        double       aFull;                     // area when full (ft2)
        double       rFull;                     // hyd. radius when full (ft)
        double       wMax;                      // width at widest point (ft)
        double       ywMax;                     // depth at max width (ft)
        double       sMax;                      // section factor at max. flow (ft^4/3)
        double       aMax;                      // area at max. flow (ft2)
        double       lengthFactor;              // floodplain / channel length

        double       roughness;                 // Manning's n
        double[]       areaTbl=new double[N_TRANSECT_TBL];   // table of area v. depth
        double[]       hradTbl=new double[N_TRANSECT_TBL];   // table of hyd. radius v. depth
        double[]       widthTbl=new double[N_TRANSECT_TBL];  // table of top width v. depth
        int          nTbl;                      // size of geometry tables

        public TTransect(String ID, double yFull, double aFull, double rFull, double wMax,
                         double ywMax, double sMax, double aMax, double lengthFactor, double roughness,
                         double[] areaTbl, double[] hradTbl, double[] widthTbl, int nTbl) {
            this.ID = ID;
            this.yFull = yFull;
            this.aFull = aFull;
            this.rFull = rFull;
            this.wMax = wMax;
            this.ywMax = ywMax;
            this.sMax = sMax;
            this.aMax = aMax;
            this.lengthFactor = lengthFactor;
            this.roughness = roughness;
            this.areaTbl = areaTbl;
            this.hradTbl = hradTbl;
            this.widthTbl = widthTbl;
            this.nTbl = nTbl;
        }
    }


//-------------------------------------
// CUSTOM CROSS SECTION SHAPE STRUCTURE
//public static final int N_SHAPE_TBL = 51  ;
// size of shape geometry tables
//-------------------------------------

    public class      TShape
    {
        int          curve;                     // index of shape curve
        int          nTbl;                      // size of geometry tables
        double       aFull;                     // area when full
        double       rFull;                     // hyd. radius when full
        double       wMax;                      // max. width
        double       sMax;                      // max. section factor
        double       aMax;                      // area at max. section factor
        double[]       areaTbl=new double[N_SHAPE_TBL];      // table of area v. depth
        double[]       hradTbl=new double[N_SHAPE_TBL];      // table of hyd. radius v. depth
        double[]       widthTbl=new double[N_SHAPE_TBL];     // table of top width v. depth

        public TShape(int curve, int nTbl, double aFull, double rFull, double wMax, double sMax, double aMax,
                      double[] areaTbl, double[] hradTbl, double[] widthTbl) {
            this.curve = curve;
            this.nTbl = nTbl;
            this.aFull = aFull;
            this.rFull = rFull;
            this.wMax = wMax;
            this.sMax = sMax;
            this.aMax = aMax;
            this.areaTbl = areaTbl;
            this.hradTbl = hradTbl;
            this.widthTbl = widthTbl;
        }
    }


    //------------
// LINK OBJECT
//------------
    public class      TLink
    {
        String         ID;              // link ID
        int           type;            // link type code
        int           subIndex;        // index of link's sub-category
        char          rptFlag;         // reporting flag
        int           node1;           // start node index
        int           node2;           // end node index
        double        offset1;         // ht. above start node invert (ft)
        double        offset2;         // ht. above end node invert (ft)
        TXsect        xsect;           // cross section data
        double        q0;              // initial flow (cfs)
        double        qLimit;          // constraint on max. flow (cfs)
        double        cLossInlet;      // inlet loss coeff.
        double        cLossOutlet;     // outlet loss coeff.
        double        cLossAvg;        // avg. loss coeff.
        double        seepRate;        // seepage rate (ft/sec)
        int           hasFlapGate;     // true if flap gate present

        double        oldFlow;         // previous flow rate (cfs)
        double        newFlow;         // current flow rate (cfs)
        double        oldDepth;        // previous flow depth (ft)
        double        newDepth;        // current flow depth (ft)
        double        oldVolume;       // previous flow volume (ft3)
        double        newVolume;       // current flow volume (ft3)
        double        surfArea1;       // upstream surface area (ft2)
        double        surfArea2;       // downstream surface area (ft2)
        double        qFull;           // flow when full (cfs)
        double        setting;         // current control setting
        double        targetSetting;   // target control setting
        double        froude;          // Froude number
        double        oldQual;         // previous quality state
        double        newQual;         // current quality state
        double        totalLoad;       // total quality mass loading
        int           flowclass ;       // flow public  class ification
        double        dqdh;            // change in flow w.r.t. head (ft2/sec)
         char   direction;       // flow direction flag
        char          bypassed;        // bypass dynwave calc. flag
        char          normalFlow;      // normal flow limited flag
        char          inletControl;    // culvert inlet control flag

        public TLink(String ID, int type, int subIndex, char rptFlag, int node1, int node2, double offset1, double offset2,
                     TXsect xsect, double q0, double qLimit, double cLossInlet, double cLossOutlet, double cLossAvg, double seepRate,
                     int hasFlapGate, double oldFlow, double newFlow, double oldDepth, double newDepth, double oldVolume, double newVolume,
                     double surfArea1, double surfArea2, double qFull, double setting, double targetSetting, double froude, double oldQual, double newQual,
                     double totalLoad, int flowclass, double dqdh, char direction, char bypassed, char normalFlow, char inletControl) {
            this.ID = ID;
            this.type = type;
            this.subIndex = subIndex;
            this.rptFlag = rptFlag;
            this.node1 = node1;
            this.node2 = node2;
            this.offset1 = offset1;
            this.offset2 = offset2;
            this.xsect = xsect;
            this.q0 = q0;
            this.qLimit = qLimit;
            this.cLossInlet = cLossInlet;
            this.cLossOutlet = cLossOutlet;
            this.cLossAvg = cLossAvg;
            this.seepRate = seepRate;
            this.hasFlapGate = hasFlapGate;
            this.oldFlow = oldFlow;
            this.newFlow = newFlow;
            this.oldDepth = oldDepth;
            this.newDepth = newDepth;
            this.oldVolume = oldVolume;
            this.newVolume = newVolume;
            this.surfArea1 = surfArea1;
            this.surfArea2 = surfArea2;
            this.qFull = qFull;
            this.setting = setting;
            this.targetSetting = targetSetting;
            this.froude = froude;
            this.oldQual = oldQual;
            this.newQual = newQual;
            this.totalLoad = totalLoad;
            this.flowclass = flowclass;
            this.dqdh = dqdh;
            this.direction = direction;
            this.bypassed = bypassed;
            this.normalFlow = normalFlow;
            this.inletControl = inletControl;
        }
    }


    //---------------
// CONDUIT OBJECT
//---------------
    public class      TConduit
    {
        double        length;          // conduit length (ft)
        double        roughness;       // Manning's n
        char          barrels;         // number of barrels

        double        modLength;       // modified conduit length (ft)
        double        roughFactor;     // roughness factor for DW routing
        double        slope;           // slope
        double        beta;            // discharge factor
        double        qMax;            // max. flow (cfs)
        double        a1, a2;          // upstream & downstream areas (ft2)
        double        q1, q2;          // upstream & downstream flows per barrel (cfs)
// double        q1Old, q2Old;    // (deprecated)                              //(5.1.008)
        double        evapLossRate;    // evaporation rate (cfs)
        double        seepLossRate;    // seepage rate (cfs)
        char          capacityLimited; // capacity limited flag
        char          superCritical;   // super-critical flow flag
        char          hasLosses;       // local losses flag
        char          fullState;       // determines if either or both ends full    //(5.1.008)

        public TConduit(double length, double roughness, char barrels, double modLength, double roughFactor, double slope,
                        double beta, double qMax, double a1, double a2, double q1, double q2, double evapLossRate, double seepLossRate,
                        char capacityLimited, char superCritical, char hasLosses, char fullState) {
            this.length = length;
            this.roughness = roughness;
            this.barrels = barrels;
            this.modLength = modLength;
            this.roughFactor = roughFactor;
            this.slope = slope;
            this.beta = beta;
            this.qMax = qMax;
            this.a1 = a1;
            this.a2 = a2;
            this.q1 = q1;
            this.q2 = q2;
            this.evapLossRate = evapLossRate;
            this.seepLossRate = seepLossRate;
            this.capacityLimited = capacityLimited;
            this.superCritical = superCritical;
            this.hasLosses = hasLosses;
            this.fullState = fullState;
        }
    }


    //------------
// PUMP OBJECT
//------------
    public class      TPump
    {
        int           type;            // pump type
        int           pumpCurve;       // pump curve table index
        double        initSetting;     // initial speed setting
        double        yOn;             // startup depth (ft)
        double        yOff;            // shutoff depth (ft)
        double        xMin;            // minimum pt. on pump curve
        double        xMax;            // maximum pt. on pump curve

        public TPump(int type, int pumpCurve, double initSetting, double yOn, double yOff, double xMin, double xMax) {
            this.type = type;
            this.pumpCurve = pumpCurve;
            this.initSetting = initSetting;
            this.yOn = yOn;
            this.yOff = yOff;
            this.xMin = xMin;
            this.xMax = xMax;
        }
    }


    //---------------
// ORIFICE OBJECT
//---------------
    public class      TOrifice
    {
        int           type;            // orifice type code
        int           shape;           // orifice shape code
        double        cDisch;          // discharge coeff.
        double        orate;           // time to open/close (sec)

        double        cOrif;           // coeff. for orifice flow (ft^2.5/sec)
        double        hCrit;           // inlet depth where weir flow begins (ft)
        double        cWeir;           // coeff. for weir flow (cfs)
        double        length;          // equivalent length (ft)
        double        surfArea;        // equivalent surface area (ft2)

        public TOrifice(int type, int shape, double cDisch, double orate, double cOrif, double hCrit, double cWeir, double length, double surfArea) {
            this.type = type;
            this.shape = shape;
            this.cDisch = cDisch;
            this.orate = orate;
            this.cOrif = cOrif;
            this.hCrit = hCrit;
            this.cWeir = cWeir;
            this.length = length;
            this.surfArea = surfArea;
        }
    }


    //------------
// WEIR OBJECT
//------------
    public class      TWeir
    {
        int           type;            // weir type code
        int           shape;           // weir shape code
        double        cDisch1;         // discharge coeff.
        double        cDisch2;         // discharge coeff. for ends
        double        endCon;          // end contractions
        int           canSurcharge;    // true if weir can surcharge                //(5.1.007)

        double        cSurcharge;      // orifice coeff. for surcharge              //(5.1.007)
        double        length;          // equivalent length (ft)
        double        slope;           // slope for Vnotch & Trapezoidal weirs
        double        surfArea;        // equivalent surface area (ft2)

        public TWeir(int type, int shape, double cDisch1, double cDisch2, double endCon, int canSurcharge,
                     double cSurcharge, double length, double slope, double surfArea) {
            this.type = type;
            this.shape = shape;
            this.cDisch1 = cDisch1;
            this.cDisch2 = cDisch2;
            this.endCon = endCon;
            this.canSurcharge = canSurcharge;
            this.cSurcharge = cSurcharge;
            this.length = length;
            this.slope = slope;
            this.surfArea = surfArea;
        }
    }


    //---------------------
// OUTLET DEVICE OBJECT
//---------------------
    public class      TOutlet
    {
        double       qCoeff;          // discharge coeff.
        double       qExpon;          // discharge exponent
        int          qCurve;          // index of discharge rating curve
        int          curveType;       // rating curve type

        public TOutlet(double qCoeff, double qExpon, int qCurve, int curveType) {
            this.qCoeff = qCoeff;
            this.qExpon = qExpon;
            this.qCurve = qCurve;
            this.curveType = curveType;
        }
    }


    //-----------------
// POLLUTANT OBJECT
//-----------------
    public class      TPollut
    {
        String         ID;              // Pollutant ID
        int           units;           // units
        double        mcf;             // mass conversion factor
        double        dwfConcen;       // dry weather sanitary flow concen.
        double        pptConcen;       // precip. concen.
        double        gwConcen;        // groundwater concen.
        double        rdiiConcen;      // RDII concen.
        double        initConcen;      // initial concen. in conveyance network
        double        kDecay;          // decay constant (1/sec)
        int           coPollut;        // co-pollutant index
        double        coFraction;      // co-pollutant fraction
        int           snowOnly;        // TRUE if buildup occurs only under snow

        public TPollut(String ID, int units, double mcf, double dwfConcen, double pptConcen,
                       double gwConcen, double rdiiConcen, double initConcen, double kDecay, int coPollut, double coFraction, int snowOnly) {
            this.ID = ID;
            this.units = units;
            this.mcf = mcf;
            this.dwfConcen = dwfConcen;
            this.pptConcen = pptConcen;
            this.gwConcen = gwConcen;
            this.rdiiConcen = rdiiConcen;
            this.initConcen = initConcen;
            this.kDecay = kDecay;
            this.coPollut = coPollut;
            this.coFraction = coFraction;
            this.snowOnly = snowOnly;
        }
    }


    //------------------------
// BUILDUP FUNCTION OBJECT
//------------------------
    public class      TBuildup
    {
        int           normalizer;      // normalizer code (area or curb length)
        int           funcType;        // buildup function type code
        double[]        coeff=new double[3];        // coeffs. of buildup function
        double        maxDays;         // time to reach max. buildup (days)

        public TBuildup(int normalizer, int funcType, double[] coeff, double maxDays) {
            this.normalizer = normalizer;
            this.funcType = funcType;
            this.coeff = coeff;
            this.maxDays = maxDays;
        }
    }


    //------------------------
// WASHOFF FUNCTION OBJECT
//------------------------
    public class      TWashoff
    {
        int           funcType;        // washoff function type code
        double        coeff;           // function coeff.
        double        expon;           // function exponent
        double        sweepEffic;      // street sweeping fractional removal
        double        bmpEffic;        // best mgt. practice fractional removal

        public TWashoff(int funcType, double coeff, double expon, double sweepEffic, double bmpEffic) {
            this.funcType = funcType;
            this.coeff = coeff;
            this.expon = expon;
            this.sweepEffic = sweepEffic;
            this.bmpEffic = bmpEffic;
        }
    }


    //---------------
    // LANDUSE OBJECT
    //---------------
    public class      TLanduse
    {
        String         ID;              // landuse name
        double        sweepInterval;   // street sweeping interval (days)
        double        sweepRemoval;    // fraction of buildup available for sweeping
        double        sweepDays0;      // days since last sweeping at start
        TBuildup     buildupFunc;     // array of buildup functions for pollutants
        TWashoff     washoffFunc;     // array of washoff functions for pollutants

        public TLanduse(String ID, double sweepInterval, double sweepRemoval, double sweepDays0, TBuildup buildupFunc, TWashoff washoffFunc) {
            this.ID = ID;
            this.sweepInterval = sweepInterval;
            this.sweepRemoval = sweepRemoval;
            this.sweepDays0 = sweepDays0;
            this.buildupFunc = buildupFunc;
            this.washoffFunc = washoffFunc;
        }
    }


    //--------------------------
// REPORTING FLAGS STRUCTURE
//--------------------------
    public class      TRptFlags
    {
        char          report;          // TRUE if results report generated
        char          input;           // TRUE if input summary included
        char          subcatchments;   // TRUE if subcatchment results reported
        char          nodes;           // TRUE if node results reported
        char          links;           // TRUE if link results reported
        char          continuity;      // TRUE if continuity errors reported
        char          flowStats;       // TRUE if routing link flow stats. reported
        char          nodeStats;       // TRUE if routing node depth stats. reported
        char          controls;        // TRUE if control actions reported
        int           linesPerPage;    // number of lines printed per page

        public TRptFlags(char report, char input, char subcatchments, char nodes, char links, char continuity, char flowStats, char nodeStats, char controls, int linesPerPage) {
            this.report = report;
            this.input = input;
            this.subcatchments = subcatchments;
            this.nodes = nodes;
            this.links = links;
            this.continuity = continuity;
            this.flowStats = flowStats;
            this.nodeStats = nodeStats;
            this.controls = controls;
            this.linesPerPage = linesPerPage;
        }
    }


    //-------------------------------
    // CUMULATIVE RUNOFF TOTALS
    //-------------------------------
    public class      TRunoffTotals
    {                                 // All volume totals are in ft3.             //(5.1.008)
        double        rainfall;        // rainfall volume
        double        evap;            // evaporation loss
        double        infil;           // infiltration loss
        double        runoff;          // runoff volume
        double        drains;          // LID drains                                //(5.1.008)
        double        runon;           // runon from outfalls                       //(5.1.008)
        double        initStorage;     // inital surface storage
        double        finalStorage;    // final surface storage
        double        initSnowCover;   // initial snow cover
        double        finalSnowCover;  // final snow cover
        double        snowRemoved;     // snow removal
        double        pctError;        // continuity error (%)

        public TRunoffTotals(double rainfall, double evap, double infil, double runoff, double drains,
                             double runon, double initStorage, double finalStorage, double initSnowCover,
                             double finalSnowCover, double snowRemoved, double pctError) {
            this.rainfall = rainfall;
            this.evap = evap;
            this.infil = infil;
            this.runoff = runoff;
            this.drains = drains;
            this.runon = runon;
            this.initStorage = initStorage;
            this.finalStorage = finalStorage;
            this.initSnowCover = initSnowCover;
            this.finalSnowCover = finalSnowCover;
            this.snowRemoved = snowRemoved;
            this.pctError = pctError;
        }
    }


    //--------------------------
    // CUMULATIVE LOADING TOTALS
    //--------------------------
    public class      TLoadingTotals
    {                                 // All loading totals are in lbs.
        double        initLoad;        // initial loading
        double        buildup;         // loading added from buildup
        double        deposition;      // loading added from wet deposition
        double        sweeping;        // loading removed by street sweeping
        double        bmpRemoval;      // loading removed by BMPs
        double        infil;           // loading removed by infiltration
        double        runoff;          // loading removed by runoff
        double        finalLoad;       // final loading
        double        pctError;        // continuity error (%)

        public TLoadingTotals(double initLoad, double buildup, double deposition, double sweeping, double bmpRemoval,
                              double infil, double runoff, double finalLoad, double pctError) {
            this.initLoad = initLoad;
            this.buildup = buildup;
            this.deposition = deposition;
            this.sweeping = sweeping;
            this.bmpRemoval = bmpRemoval;
            this.infil = infil;
            this.runoff = runoff;
            this.finalLoad = finalLoad;
            this.pctError = pctError;
        }
    }


    //------------------------------
// CUMULATIVE GROUNDWATER TOTALS
//------------------------------
    public class      TGwaterTotals
    {                                 // All GW flux totals are in feet.
        double        infil;           // surface infiltration
        double        upperEvap;       // upper zone evaporation loss
        double        lowerEvap;       // lower zone evaporation loss
        double        lowerPerc;       // percolation out of lower zone
        double        gwater;          // groundwater flow
        double        initStorage;     // initial groundwater storage
        double        finalStorage;    // final groundwater storage
        double        pctError;        // continuity error (%)

        public TGwaterTotals(double infil, double upperEvap, double lowerEvap, double lowerPerc, double gwater,
                             double initStorage, double finalStorage, double pctError) {
            this.infil = infil;
            this.upperEvap = upperEvap;
            this.lowerEvap = lowerEvap;
            this.lowerPerc = lowerPerc;
            this.gwater = gwater;
            this.initStorage = initStorage;
            this.finalStorage = finalStorage;
            this.pctError = pctError;
        }
    }


    //----------------------------
// CUMULATIVE ROUTING TOTALS
//----------------------------
    public class      TRoutingTotals
    {                                  // All routing totals are in ft3.
        double        dwInflow;         // dry weather inflow
        double        wwInflow;         // wet weather inflow
        double        gwInflow;         // groundwater inflow
        double        iiInflow;         // RDII inflow
        double        exInflow;         // direct inflow
        double        flooding;         // internal flooding
        double        outflow;          // external outflow
        double        evapLoss;         // evaporation loss
        double        seepLoss;         // seepage loss
        double        reacted;          // reaction losses
        double        initStorage;      // initial storage volume
        double        finalStorage;     // final storage volume
        double        pctError;         // continuity error

        public TRoutingTotals(double dwInflow, double wwInflow, double gwInflow,
                              double iiInflow, double exInflow, double flooding,
                              double outflow, double evapLoss, double seepLoss,
                              double reacted, double initStorage, double finalStorage, double pctError) {
            this.dwInflow = dwInflow;
            this.wwInflow = wwInflow;
            this.gwInflow = gwInflow;
            this.iiInflow = iiInflow;
            this.exInflow = exInflow;
            this.flooding = flooding;
            this.outflow = outflow;
            this.evapLoss = evapLoss;
            this.seepLoss = seepLoss;
            this.reacted = reacted;
            this.initStorage = initStorage;
            this.finalStorage = finalStorage;
            this.pctError = pctError;
        }
    }


    //-----------------------
// SYSTEM-WIDE STATISTICS
//-----------------------
    public class      TSysStats
    {
        double        minTimeStep;
        double        maxTimeStep;
        double        avgTimeStep;
        double        avgStepCount;
        double        steadyStateCount;

        public TSysStats(double minTimeStep, double maxTimeStep, double avgTimeStep, double avgStepCount, double steadyStateCount) {
            this.minTimeStep = minTimeStep;
            this.maxTimeStep = maxTimeStep;
            this.avgTimeStep = avgTimeStep;
            this.avgStepCount = avgStepCount;
            this.steadyStateCount = steadyStateCount;
        }
    }


    //--------------------
// RAINFALL STATISTICS
//--------------------
    public class      TRainStats
    {
        DateTime    startDate;
        DateTime    endDate;
        long        periodsRain;
        long        periodsMissing;
        long        periodsMalfunc;

        public TRainStats(DateTime startDate, DateTime endDate, long periodsRain, long periodsMissing, long periodsMalfunc) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.periodsRain = periodsRain;
            this.periodsMissing = periodsMissing;
            this.periodsMalfunc = periodsMalfunc;
        }
    }


    //------------------------
// SUBCATCHMENT STATISTICS
//------------------------
    public class      TSubcatchStats
    {
        double       precip;
        double       runon;
        double       evap;
        double       infil;
        double       runoff;
        double       maxFlow;

        public TSubcatchStats(double precip, double runon, double evap, double infil, double runoff, double maxFlow) {
            this.precip = precip;
            this.runon = runon;
            this.evap = evap;
            this.infil = infil;
            this.runoff = runoff;
            this.maxFlow = maxFlow;
        }
    }

//----------------
// NODE STATISTICS
//----------------
public class      TNodeStats
{
    double        avgDepth;
    double        maxDepth;
    DateTime      maxDepthDate;
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


    //-------------------
// STORAGE STATISTICS
//-------------------
    public class      TStorageStats
    {
        double        initVol;
        double        avgVol;
        double        maxVol;
        double        maxFlow;
        double        evapLosses;
        double        exfilLosses;
        DateTime      maxVolDate;

        public TStorageStats(double initVol, double avgVol, double maxVol, double maxFlow, double evapLosses, double exfilLosses, DateTime maxVolDate) {
            this.initVol = initVol;
            this.avgVol = avgVol;
            this.maxVol = maxVol;
            this.maxFlow = maxFlow;
            this.evapLosses = evapLosses;
            this.exfilLosses = exfilLosses;
            this.maxVolDate = maxVolDate;
        }
    }


    //-------------------
// OUTFALL STATISTICS
//-------------------
    public class     TOutfallStats
    {
        double       avgFlow;
        double       maxFlow;
        double      totalLoad;
        int          totalPeriods;

        public TOutfallStats(double avgFlow, double maxFlow, double totalLoad, int totalPeriods) {
            this.avgFlow = avgFlow;
            this.maxFlow = maxFlow;
            this.totalLoad = totalLoad;
            this.totalPeriods = totalPeriods;
        }
    }


    //----------------
// PUMP STATISTICS
//----------------
    public class    TPumpStats
    {
        double       utilized;
        double       minFlow;
        double       avgFlow;
        double       maxFlow;
        double       volume;
        double       energy;
        double       offCurveLow;
        double       offCurveHigh;
        int          startUps;
        int          totalPeriods;

        public TPumpStats(double utilized, double minFlow, double avgFlow, double maxFlow,
                          double volume, double energy, double offCurveLow, double offCurveHigh, int startUps, int totalPeriods) {
            this.utilized = utilized;
            this.minFlow = minFlow;
            this.avgFlow = avgFlow;
            this.maxFlow = maxFlow;
            this.volume = volume;
            this.energy = energy;
            this.offCurveLow = offCurveLow;
            this.offCurveHigh = offCurveHigh;
            this.startUps = startUps;
            this.totalPeriods = totalPeriods;
        }
    }


    //----------------
// LINK STATISTICS
//----------------
    public class      TLinkStats
    {
        double        maxFlow;
        DateTime      maxFlowDate;
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


    //-------------------------
// MAXIMUM VALUE STATISTICS
//-------------------------
    public class      TMaxStats
    {
        int           objType;         // either NODE or LINK
        int           index;           // node or link index
        double        value;           // value of node or link statistic
    }  ;


//------------------
// REPORT FIELD INFO
//------------------
    public class  TRptField {
        String Name;        // name of reported variable
        String Units;       // units of reported variable
        char Enabled;         // TRUE if appears in report table
        int Precision;       // number of decimal places when reported
    }

}
