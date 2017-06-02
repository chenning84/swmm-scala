package swmm.runoff.lid

import java.io.File


import swmm.runoff.infil.TGrnAmpt

/**
  * Created by ning on 11/8/15.
  */
object LidH {

  object LidTypes extends Enumeration {
    type lidTypeValues  = Value
    val
        BIO_CELL,                // bio-retention cell
        RAIN_GARDEN,             // rain garden
        GREEN_ROOF,              // green roof
        INFIL_TRENCH,            // infiltration trench
        POROUS_PAVEMENT,         // porous pavement
        RAIN_BARREL,             // rain barrel
        VEG_SWALE,               // vegetative swale
        ROOF_DISCON            // roof disconnection                             //(5.1.008)
    =Value
  }
  object TimePeriod extends Enumeration {
    type timePeriod  = Value
    val
      PREVIOUS,                // previous time period                           //(5.1.008)
      CURRENT               // current time period                            //(5.1.008)
    =Value
  }
  val MAX_LAYERS= 4

  // LID Surface Layer
   class TSurfaceLayer
    {
      var    thickness:Double =.0          // depression storage or berm ht. (ft)
      var    voidFrac:Double =.0           // available fraction of storage volume
      var    roughness:Double =.0          // surface Mannings n 
      var    surfSlope:Double =.0          // land surface slope (fraction)
      var    sideSlope:Double =.0          // swale side slope (run/rise)
      var    alpha:Double =.0              // slope/roughness term in Manning eqn.
      var      canOverflow:Int =0         // 1 if immediate outflow of excess water
    }  

  // LID Pavement Layer
   class TPavementLayer
    {
      var   thickness:Double =.0           // layer thickness (ft)
      var   voidFrac:Double =.0            // void volume / total volume
      var   impervFrac:Double =.0          // impervious area fraction
      var   kSat:Double =.0                // permeability (ft/sec)
      var   clogFactor:Double =.0          // clogging factor
    }  

  // LID Soil Layer
   class TSoilLayer
    {
      var    thickness:Double =.0          // layer thickness (ft)
      var    porosity:Double =.0           // void volume / total volume
      var    fieldCap:Double =.0           // field capacity
      var    wiltPoint:Double =.0          // wilting point
      var    suction:Double =.0            // suction head at wetting front (ft)
      var    kSat:Double =.0               // saturated hydraulic conductivity (ft/sec)
      var    kSlope:Double =.0             // slope of log(K) v. moisture content curve
    }  

  // LID Storage Layer
   class TStorageLayer
    {
      var    thickness:Double =.0          // layer thickness (ft)
      var    voidFrac:Double =.0           // void volume / total volume
      var    kSat:Double =.0               // saturated hydraulic conductivity (ft/sec)
      var    clogFactor:Double =.0         // clogging factor
    }  

  // Underdrain System (part of Storage Layer)
   class TDrainLayer
    {
      var    coeff:Double =.0              // underdrain flow coeff. (in/hr or mm/hr)
      var    expon:Double =.0              // underdrain head exponent (for in or mm)
      var    offset:Double =.0             // offset height of underdrain (ft)
      var    delay:Double =.0              // rain barrel drain delay time (sec)
    }  

  // Drainage Mat Layer (for green roofs)
   class TDrainMatLayer
    {
      var    thickness:Double =.0          // layer thickness (ft)
      var    voidFrac:Double =.0           // void volume / total volume
      var    roughness:Double =.0          // Mannings n for green roof drainage mats
      var    alpha:Double =.0              // slope/roughness term in Manning equation
    }  

  // LID Process - generic LID design per unit of area
   class TLidProc
    {
      var ID: String = "" 
      // identifying name
      var lidType: Int = 0
      // type of LID
      var surface: TSurfaceLayer = null
      // surface layer parameters
      var pavement: TPavementLayer = null
      // pavement layer parameters
      var soil: TSoilLayer = null
      // soil layer parameters
      var storage: TStorageLayer = null
      // storage layer parameters
      var drain: TDrainLayer = null
      // underdrain system parameters
      var drainMat: TDrainMatLayer = null // drainage mat layer
    }  

  // Water Balance Statistics
   class TWaterBalance
    {
      var         inflow:Double =.0        // total inflow (ft)
      var         evap:Double =.0          // total evaporation (ft)
      var         infil:Double =.0         // total infiltration (ft)
      var         surfFlow:Double =.0      // total surface runoff (ft)
      var         drainFlow:Double =.0     // total underdrain flow (ft)
      var         initVol:Double =.0       // initial stored volume (ft)
      var         finalVol:Double =.0      // final stored volume (ft)
    }  

  // LID Report File
   class TLidRptFile
    {
      var     file:File=null                // file pointer
      var       wasDry:Int =0             // true if LID was dry                       //(5.1.008)
      var      results:String=""     // results for current time period           //(5.1.008)
    }   

  // LID Unit - specific LID process applied over a given area
   class TLidUnit
    {
      var      lidIndex:Int =0       // index of LID process
      var      number:Int =0         // number of replicate units
      var   area:Double =.0           // area of single replicate unit (ft2)
      var   fullWidth:Double =.0      // full top width of single unit (ft)
      var   botWidth:Double =.0       // bottom width of single unit (ft)
      var   initSat:Double =.0        // initial saturation of soil & storage layers
      var   fromImperv:Double =.0     // fraction of impervious area runoff treated
      var      toPerv:Int =0         // 1 if outflow sent to pervious area:Int =0 0 if not
      var      drainSubcatch:Int =0  // subcatchment receiving drain flow              //(5.1.008)
      var      drainNode:Int =0      // node receiving drain flow                      //(5.1.008)
      var rptFile:TLidRptFile =null     // pointer to detailed report file

      var soilInfil:TGrnAmpt=null      // infil. object for biocell soil layer 
      var   surfaceDepth:Double =.0   // depth of ponded water on surface layer (ft)
      var   paveMoisture:Double =.0   // moisture content of porous pavement layer      //(5.1.008)
      var   soilMoisture:Double =.0   // moisture content of biocell soil layer
      var   storageDepth:Double =.0   // depth of water in storage layer (ft)

      // net inflow - outflow from previous time step for each LID layer (ft/s)
      var   oldFluxRates:Array[Double]= null// [MAX_LAYERS]

      var   dryTime:Double =.0        // time since last rainfall (sec)
      var   oldDrainFlow:Double =.0   // previous drain flow (cfs)                      //(5.1.008)
      var   newDrainFlow:Double =.0   // current drain flow (cfs)                       //(5.1.008)
      var  waterBalance:TWaterBalance =null     // water balance quantites
    }  

}
