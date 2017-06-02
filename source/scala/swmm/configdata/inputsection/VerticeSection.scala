package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
 */
class VerticeSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[VERTICES]
;;Link             X-Coord          Y-Coord
  55_OVRFLW        2538971.740      430945.280
  775pipe          2544406.160      427942.170
  TEUT_OVF         2543053.430      433203.430
  DEERWPIPE        2543780.710      433879.840
    */
   override var indices = List(0, 2,19,36,200)
   override var dataIndice = List(0,19,36,200)
}
