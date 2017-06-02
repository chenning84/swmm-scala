package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class EvaporationSection(n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
  /*
;;Type         Parameters
;;-----------------------
  CONSTANT     0.1
   */
   override var indices = List(0, 2, 15,25)
   override var dataIndice = List(0, 15,25)
}
