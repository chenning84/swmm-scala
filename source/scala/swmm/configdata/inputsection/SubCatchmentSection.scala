package swmm.configdata.inputsection

/**
 * Created by ning on 7/4/15.
  *
 */
class SubCatchmentSection(n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
  override var indices = List(0, 2, 19, 36, 53, 62, 71, 80, 89, 98, 102)
  override  var dataIndice = List(0, 19, 36, 53, 62, 71, 80, 89, 98, 102)

}
