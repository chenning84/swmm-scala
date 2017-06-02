package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class SSymbolSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[SYMBOLS]
;;Gage             X-Coord          Y-Coord

    */
   override var indices = List(0, 2,19,36,200)
   override var dataIndice = List(0,19,36,200)
}
