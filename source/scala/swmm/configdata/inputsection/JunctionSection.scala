package swmm.configdata.inputsection

/**
 * Created by ning on 10/22/15.
  *
 */
class JunctionSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
  /*
;;                 Invert     Max.       Init.      Surcharge  Ponded
;;Name             Elev.      Depth      Depth      Depth      Area
;;------------------------------------------------------------------------
  0.10_RM          640.60     59.40      .00        0          0
  0.10_UP          640.70     59.30      .00        0          0
  0.15_RM          643.90     56.10      .00        0          0

  */
  override var indices = List(0, 2,19,30,41,52,63,74)
  override var dataIndice = List(0,19,30,41,52,63,74)
}