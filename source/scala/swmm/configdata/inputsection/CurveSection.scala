package swmm.configdata.inputsection

/**
 * Created by Ning  on 10/23/15.
  *
 */
class CurveSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
  /*
[CURVES]
;;Name             Type       X-Value    Y-Value
;;-------------------------------------------------
  1SB1             STORAGE    0.0        209088.0
  1SB1                        4.65       274428.0

  99               STORAGE    0.0        43.56
  99                          0.53       32670.0
  99                          1.53       111949.2
  */
  override var indices = List(0, 2,19,30,41,51)
  override var dataIndice = List(0,19,30,41,51)
}