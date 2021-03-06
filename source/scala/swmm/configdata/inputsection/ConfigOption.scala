package swmm.configdata.inputsection

import swmm.configdata.ConfigObject

/**
 * Created by ning on 5/29/15.
  *
 */

class ConfigOption  (  n:String ,  hd:List[String],  dat:List[String]) extends DataSection(n, hd, dat){

  /*

[OPTIONS]
 FLOW_UNITS            CFS
 INFILTRATION          HORTON
 FLOW_ROUTING          DYNWAVE
 START_DATE            08/11/1998
 START_TIME            00:00:00
 REPORT_START_DATE     08/11/1998
 REPORT_START_TIME     00:00:00
 END_DATE              08/11/1998
 END_TIME              04:00:00
 SWEEP_START           01/01
 SWEEP_END             12/31
 DRY_DAYS              0
 REPORT_STEP           00:01:00
 WET_STEP              00:02:30
 DRY_STEP              00:10:00
 ROUTING_STEP          0.5
 ALLOW_PONDING         NO
 INERTIAL_DAMPING      NONE
 VARIABLE_STEP         0.00
 LENGTHENING_STEP      0
 MIN_SURFAREA          0
 NORMAL_FLOW_LIMITED   NO
 SKIP_STEADY_STATE     NO
  */
  override var indices = List(0, 2,23,222)
  override var dataIndice = List(0,23,222)
//  override  def initSectionData :ConfigObject=
//  {
//
//
//    val dataMap = buildMap(data)
//    val FLOW_ROUTING = dataMap.get("FLOW_ROUTING")
//    val FLOW_UNITS = dataMap.get("FLOW_UNITS")
//    new ConfigObject(name, Nil, Nil)
//   // val test = dataMap.get("test")
//   // print("inside kakakak")
//  }
}
