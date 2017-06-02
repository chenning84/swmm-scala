package swmm.configdata.inputsection

/**
 * Created by Ning Chen on 10/22/15.
  *
 */
class SubAreaSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
  /*
[SUBAREAS]
;;Subcatchment     N-Imperv   N-Perv     S-Imperv   S-Perv     PctZero    RouteTo    PctRouted
;;--------------------------------------------------------------------------------------------
  1SB5#1           0.018      0.035      0.1        0.2        25         OUTLET
  2SB5#1           0.018      0.035      0.1        0.2        25         OUTLET
  1SB23#1          0.018      0.035      0.1        0.2        25         OUTLET
  2SB23#1          0.018      0.035      0.1        0.2        25         OUTLET
  4SB23#1          0.018      0.035      0.1        0.2        25         OUTLET
  */
  override var indices = List(0, 2, 19,30,41,52,63,74,84,94)
  override var dataIndice = List(0,19,30,41,52,63,74,84)
}