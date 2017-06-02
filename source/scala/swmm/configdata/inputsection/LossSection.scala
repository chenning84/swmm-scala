package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class LossSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[LOSSES]
;;Link             Inlet      Outlet     Average    Flap Gate
;;-------------------------------------------------------------
  0.06_NAT         0          0          0          YES
  826pipe          0          0          0          YES
  825pipe          0          0          0          YES
  853pipe          0          0          0          YES
    */
   override var indices = List(0, 2,19,30,41,52,63)
   override var dataIndice = List(0,19,30,41,52,63)
}
