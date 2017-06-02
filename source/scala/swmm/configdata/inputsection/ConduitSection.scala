package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
 */
class ConduitSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
  /*
[CONDUITS]
;;                 Inlet            Outlet                      Manning    Inlet      Outlet     Init.      Maximum
;;Name             Node             Node             Length     N          Height     Height     Flow       Flow
;;------------------------------------------------------------------------------------------------------------------------
  583              19SB6            18SB6            1000.0     0.0        0.0        7.8        0.0        0
  591              26SB6            25SB6            300.0      0.0        0.0        0.0        0.0        0
*/
  override var indices = List(0, 2,19,36,53,64,75,86,97,108,122)
  override var dataIndice = List(0,19,36,53,64,75,86,97,108,122)
}
