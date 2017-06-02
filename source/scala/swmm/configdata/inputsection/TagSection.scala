package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class TagSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   override var indices = List(0, 2)
   override var dataIndice = List(0,94)
}
