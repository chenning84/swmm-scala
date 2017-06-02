package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class OutfallSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
  /*
[OUTFALLS]
;;                 Invert     Outfall    Stage/Table      Tide
;;Name             Elev.      Type       Time Series      Gate
;;------------------------------------------------------------
  O.O_RM           637.8      FIXED      650 YES

   */
  override var indices = List(0, 2,19,30,41,58,62)
  override var dataIndice = List(0,19,30,41,58,62)
}