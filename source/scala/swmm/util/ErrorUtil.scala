package swmm.util

import swmm.configdata.jnodes.Errorh.ErrorType

/**
  * 
  * Created by ning on 11/11/15.
  * Origin from Error.c 
  */
object ErrorUtil {

 val  ERR101:String="\n  ERROR 101: memory allocation error."
 val  ERR103:String="\n  ERROR 103: cannot solve KW equations for Link %s."
 val  ERR105:String="\n  ERROR 105: cannot open ODE solver."
 val  ERR107:String="\n  ERROR 107: cannot compute a valid time step."

 val  ERR108:String="\n  ERROR 108: ambiguous outlet ID name for Subcatchment %s."
 val  ERR109:String="\n  ERROR 109: invalid parameter values for Aquifer %s."
 val  ERR110:String="\n  ERROR 110: ground elevation is below water table for Subcatchment %s."

 val  ERR111:String="\n  ERROR 111: invalid length for Conduit %s."
 val  ERR112:String="\n  ERROR 112: elevation drop exceeds length for Conduit %s."
 val  ERR113:String="\n  ERROR 113: invalid roughness for Conduit %s."
 val  ERR114:String="\n  ERROR 114: invalid number of barrels for Conduit %s."
 val  ERR115:String="\n  ERROR 115: adverse slope for Conduit %s."
 val  ERR117:String="\n  ERROR 117: no cross section defined for Link %s."
 val  ERR119:String="\n  ERROR 119: invalid cross section for Link %s."
 val  ERR121:String="\n  ERROR 121: missing or invalid pump curve assigned to Pump %s."
 val  ERR122:String="\n  ERROR 122: startup depth not higher than shutoff depth for Pump %s."

 val  ERR131:String="\n  ERROR 131: the following links form cyclic loops in the drainage system:"
 val  ERR133:String="\n  ERROR 133: Node %s has more than one outlet link."
 val  ERR134:String="\n  ERROR 134: Node %s has illegal DUMMY link connections."

 val  ERR135:String="\n  ERROR 135: Divider %s does not have two outlet links."
 val  ERR136:String="\n  ERROR 136: Divider %s has invalid diversion link."
 val  ERR137:String="\n  ERROR 137: Weir Divider %s has invalid parameters."
 val  ERR138:String="\n  ERROR 138: Node %s has initial depth greater than maximum depth."
 val  ERR139:String="\n  ERROR 139: Regulator %s is the outlet of a non-storage node."
 val  ERR141:String="\n  ERROR 141: Outfall %s has more than 1 inlet link or an outlet link."
 val  ERR143:String="\n  ERROR 143: Regulator %s has invalid cross-section shape."
 val  ERR145:String="\n  ERROR 145: Drainage system has no acceptable outlet nodes."

 val  ERR151:String="\n  ERROR 151: a Unit Hydrograph in set %s has invalid time base."
 val  ERR153:String="\n  ERROR 153: a Unit Hydrograph in set %s has invalid response ratios."
 val  ERR155:String="\n  ERROR 155: invalid sewer area for RDII at node %s."

 val  ERR156:String="\n  ERROR 156: ambiguous station ID for Rain Gage %s."
 val  ERR157:String="\n  ERROR 157: inconsistent rainfall format for Rain Gage %s."
 val  ERR158:String="\n  ERROR 158: time series for Rain Gage %s is also used by another object."
 val  ERR159:String="\n  ERROR 159: recording interval greater than time series interval for Rain Gage %s."

 val  ERR161:String="\n  ERROR 161: cyclic dependency in treatment functions at node %s."

 val  ERR171:String="\n  ERROR 171: Curve %s has invalid or out of sequence data."
 val  ERR173:String="\n  ERROR 173: Time Series %s has its data out of sequence."

 val  ERR181:String="\n  ERROR 181: invalid Snow Melt Climatology parameters."
 val  ERR182:String="\n  ERROR 182: invalid parameters for Snow Pack %s."

 val  ERR183:String="\n  ERROR 183: no type specified for LID %s."
 val  ERR184:String="\n  ERROR 184: missing layer for LID %s."
 val  ERR185:String="\n  ERROR 185: invalid parameter value for LID %s."
 val  ERR186:String="\n  ERROR 186: invalid parameter value for LID placed in Subcatchment %s."
 val  ERR187:String="\n  ERROR 187: LID area exceeds total area for Subcatchment %s."
 val  ERR188:String="\n  ERROR 188: LID capture area exceeds total impervious area for Subcatchment %s."

 val  ERR191:String="\n  ERROR 191: simulation start date comes after ending date."
 val  ERR193:String="\n  ERROR 193: report start date comes after ending date."
 val  ERR195:String="\n  ERROR 195: reporting time step or duration is less than routing time step."

 val  ERR200:String="\n  ERROR 200: one or more errors in input file."
 val  ERR201:String="\n  ERROR 201: too many characters in input line "
 val  ERR203:String="\n  ERROR 203: too few items "
 val  ERR205:String="\n  ERROR 205: invalid keyword %s "
 val  ERR207:String="\n  ERROR 207: duplicate ID name %s "
 val  ERR209:String="\n  ERROR 209: undefined object %s "
 val  ERR211:String="\n  ERROR 211: invalid number %s "
 val  ERR213:String="\n  ERROR 213: invalid date/time %s "
 val  ERR217:String="\n  ERROR 217: control rule clause invalid or out of sequence "  //(5.1.008)
 val  ERR219:String="\n  ERROR 219: data provided for unidentified transect "
 val  ERR221:String="\n  ERROR 221: transect station out of sequence "
 val  ERR223:String="\n  ERROR 223: Transect %s has too few stations."
 val  ERR225:String="\n  ERROR 225: Transect %s has too many stations."
 val  ERR227:String="\n  ERROR 227: Transect %s has no Manning's N."
 val  ERR229:String="\n  ERROR 229: Transect %s has invalid overbank locations."
 val  ERR231:String="\n  ERROR 231: Transect %s has no depth."
 val  ERR233:String="\n  ERROR 233: invalid treatment function expression "

 val  ERR301:String="\n  ERROR 301: files share same names."
 val  ERR303:String="\n  ERROR 303: cannot open input file."
 val  ERR305:String="\n  ERROR 305: cannot open report file."
 val  ERR307:String="\n  ERROR 307: cannot open binary results file."
 val  ERR309:String="\n  ERROR 309: error writing to binary results file."
 val  ERR311:String="\n  ERROR 311: error reading from binary results file."

 val  ERR313:String="\n  ERROR 313: cannot open scratch rainfall interface file."
 val  ERR315:String="\n  ERROR 315: cannot open rainfall interface file %s."
 val  ERR317:String="\n  ERROR 317: cannot open rainfall data file %s."
 val  ERR318:String="\n  ERROR 318: date out of sequence in rainfall data file %s."
 val  ERR319:String="\n  ERROR 319: unknown format for rainfall data file %s."
 val  ERR320:String="\n  ERROR 320: invalid format for rainfall interface file."
 val  ERR321:String="\n  ERROR 321: no data in rainfall interface file for gage %s."

 val  ERR323:String="\n  ERROR 323: cannot open runoff interface file %s."
 val  ERR325  :String="\n  ERROR 325: incompatible data found in runoff interface file."
 val  ERR327  :String="\n  ERROR 327: attempting to read beyond end of runoff interface file."
 val  ERR329:String="\n  ERROR 329: error in reading from runoff interface file."

 val  ERR330:String="\n  ERROR 330: hotstart interface files have same names."
 val  ERR331:String="\n  ERROR 331: cannot open hotstart interface file %s."
 val  ERR333  :String="\n  ERROR 333: incompatible data found in hotstart interface file."
 val  ERR335:String="\n  ERROR 335: error in reading from hotstart interface file."

 val  ERR336  :String="\n  ERROR 336: no climate file specified for evaporation and/or wind speed."
 val  ERR337:String="\n  ERROR 337: cannot open climate file %s."
 val  ERR338:String="\n  ERROR 338: error in reading from climate file %s."
 val  ERR339  :String="\n  ERROR 339: attempt to read beyond end of climate file %s."

 val  ERR341:String="\n  ERROR 341: cannot open scratch RDII interface file."
 val  ERR343:String="\n  ERROR 343: cannot open RDII interface file %s."
 val  ERR345:String="\n  ERROR 345: invalid format for RDII interface file."

 val  ERR351:String="\n  ERROR 351: cannot open routing interface file %s."
 val  ERR353:String="\n  ERROR 353: invalid format for routing interface file %s."
 val  ERR355:String="\n  ERROR 355: mis-matched names in routing interface file %s."
 val  ERR357:String="\n  ERROR 357: inflows and outflows interface files have same name."

 val  ERR361:String="\n  ERROR 361: could not open external file used for Time Series %s."
 val  ERR363:String="\n  ERROR 363: invalid data in external file used for Time Series %s."

 val  ERR401:String="\n  ERROR 401: general system error."
 val  ERR402  :String="\n  ERROR 402: cannot open new project while current project still open."
 val  ERR403:String="\n  ERROR 403: project not open or last run not ended."
 val  ERR405  :String="\n  ERROR 405: amount of output produced will exceed maximum file size" +
    "\n             either reduce Ending Date or increase Reporting Time Step."

  ////////////////////////////////////////////////////////////////////////////
  //  NOTE: Need to update ErrorMsgs[], ErrorCodes[], and ErrorType
  //        (in error.h) whenever a new error message is added.
  /////////////////////////////////////////////////////////////////////////////

  val ErrorMsgs: Array.type = Array
  (   ERR101, ERR103, ERR105, ERR107, ERR108, ERR109, ERR110, ERR111, ERR112,
      ERR113, ERR114, ERR115, ERR117, ERR119, ERR121, ERR122, ERR131,// ERR133,
 //     ERR134, ERR135, ERR136, ERR137, ERR138, ERR139, ERR141, ERR143,
//      ERR145, ERR151, ERR153, ERR155, ERR156, ERR157, ERR158, ERR159, ERR161,
//      ERR171, ERR173, ERR181, ERR182, ERR183, ERR184, ERR185, ERR186, ERR187,
//      ERR188, ERR191, ERR193, ERR195, ERR200, ERR201, ERR203, ERR205, ERR207,
//      ERR209, ERR211, ERR213, ERR217, ERR219, ERR221, ERR223, ERR225, ERR227,
   //   ERR229, ERR231, ERR233, ERR301, ERR303, ERR305, ERR307, ERR309, ERR311,
   //   ERR313, ERR315, ERR317, ERR318, ERR319, ERR320, ERR321, ERR323, ERR325,
   //   ERR327, ERR329, ERR330, ERR331, ERR333, ERR335, ERR336, ERR337, ERR338,
  //    ERR339, ERR341, ERR343, ERR345, ERR351, ERR353, ERR355, ERR357, ERR361,
      ERR363, ERR401, ERR402, ERR403, ERR405)

  val ErrorCodes = Set(
   0,      101,    103,    105,    107,    108,    109,    110,    111,
    112,    113,    114,    115,    117,    119,    121,    122,    131,
    133,    134,    135,    136,    137,    138,    139,    141,    143,
    145,    151,    153,    155,    156,    157,    158,    159,    161,
    171,    173,    181,    182,    183,    184,    185,    186,    187,
    188,    191,    193,    195,    200,    201,    203,    205,    207,
    209,    211,    213,    217,    219,    221,    223,    225,    227,
    229,    231,    233,    301,    303,    305,    307,    309,    311,
    313,    315,    317,    318,    319,    320,    321,    323,    325,
    327,    329,    330,    331,    333,    335,    336,    337,    338,
    339,    341,    343,    345,    351,    353,    355,    357,    361,
    363,    401,    402,    403,    405)

  

  def error_getMsg(i:Int)=
  {
    ErrorMsgs(i)
  }

  def  error_getCode( i:Int):Unit =
  {
    ErrorCodes(i)
  }
  /*
  Dummy function useless now 
   */
  def  error_setInpError( errcode:ErrorType,  t:String):Int =
  {
    
     errcode.ordinal()
  }

}
