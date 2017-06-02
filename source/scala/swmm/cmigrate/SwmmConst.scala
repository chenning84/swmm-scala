package swmm.cmigrate

/**
 * Created by Ning  on 10/23/15.
 *
 */
//-----------------------------------------------------------------------------
//   consts.h
//
//   Project: EPA SWMM5
//   Version: 5.1
//   Date:    03/20/14  (Build 5.1.001)
//   Author:  L. Rossman
//
//   Various Constants
//-----------------------------------------------------------------------------

object SwmmConst {

  //------------------
  // General Constants
  //------------------

val   VERSION      =    51000
val   MAGICNUMBER      =516114522
val   EOFMARK      =    0x1A           // Use 0x04 for UNIX systems
val   MAXTITLE      =   3              // Max. # title lines
val   MAXMSG      =     1024           // Max. # characters in message text
val   MAXLINE     =       1024           // Max. # characters per input line
val   MAXFNAME    =       259            // Max. # characters in file name
val   MAXTOKS     =       40             // Max. items per line of input
val   MAXSTATES   =       10             // Max. # computed hyd. variables
val   MAXODES     =       4              // Max. # ODE's to be solved
val   NA          =       -1             // NOT APPLICABLE code
val   TRUE        =       1              // Value for TRUE state
val   FALSE       =       0              // Value for FALSE state
val   BIG         =     new java.lang.Double("1.E10")          // Generic large value
val   TINY        =     new java.lang.Double("1.E-6")          // Generic small value
val   ZERO        =     new java.lang.Double("1.E-10")         // Effective zero value
val   MISSING     =     new java.lang.Double("-1.E10")         // Missing value code
val   PI          =       3.141592654    // Value of pi
val   GRAVITY     =       32.2           // accel. of gravity in US units
val   SI_GRAVITY  =       9.81           // accel of gravity in SI units
val   MAXFILESIZE =       2147483647L    // largest file size in bytes

  //-----------------------------
  // Units factor in Manning Eqn.
  //-----------------------------
val   PHI = 1.486

  //----------------------------------------------
  // Definition of measureable runoff flow & depth
  //----------------------------------------------
val   MIN_RUNOFF_FLOW   = 0.001          // cfs
val   MIN_EXCESS_DEPTH  = 0.0001         // ft, = 0.03 mm  <NOT USED>
val   MIN_TOTAL_DEPTH   = 0.004167       // ft, = 0.05 inches
val   MIN_RUNOFF        = 2.31481e-8     // ft/sec = 0.001 in/hr

  //----------------------------------------------------------------------
  // Minimum flow, depth & volume used to evaluate steady state conditions
  //----------------------------------------------------------------------
val   FLOW_TOL    =  0.00001  // cfs
val   DEPTH_TOL   =  0.00001  // ft    <NOT USED>
val   VOLUME_TOL  =  0.01     // ft3   <NOT USED>

  //---------------------------------------------------
  // Minimum depth for reporting non-zero water quality
  //---------------------------------------------------
  //#define   MIN_WQ_DEPTH  0.01     // ft (= 3 mm)
  //#define   MIN_WQ_FLOW   0.001    // cfs

  //-----------------------------------------------------
  // Minimum flow depth and area for dynamic wave routing
  //-----------------------------------------------------
val   FUDGE    =0.0001    // ft or ft2

  //---------------------------
  // Various conversion factors
  //---------------------------
val   GPMperCFS  = 448.831
val   AFDperCFS  = 1.9837
val   MGDperCFS  = 0.64632
val   IMGDperCFS = 0.5382
val   LPSperCFS  = 28.317
val   LPMperCFS  = 1699.0
val   CMHperCFS  = 101.94
val   CMDperCFS  = 2446.6
val   MLDperCFS  = 2.4466
val   M3perFT3   = 0.028317
val   LperFT3    = 28.317
val   MperFT     = 0.3048
val   PSIperFT   = 0.4333
val   KPAperPSI  = 6.895
val   KWperHP    = 0.7457
val   SECperDAY  = 86400
val   MSECperDAY = 8.64e7
val   MMperINCH  = 25.40

  //---------------------------
  // Token separator characters
  //--------------------------- 
val   SEPSTR    =" \t\n\r"


}
