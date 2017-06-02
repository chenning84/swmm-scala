package swmm.configdata.inputsection

/**
 * Created by Ning Chen on 10/22/15.
  *
 */

class InfiltrationSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {

  /*
;;Subcatchment     MaxRate    MinRate    Decay      DryTime    MaxInfil
;;-----------------------------------------------------------------------
  1SB5#1           4.8        0.1        4.14       0.02013    0
  2SB5#1           4.8        0.1        4.14       0.02013    0

   */
  override var indices = List(0, 2,19,30,41,52,63,73)
  override var dataIndice = List(0,19,30,41,52,63,73)
}