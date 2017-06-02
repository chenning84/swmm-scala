package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class LabelSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
 /*
[LABELS]
;;X-Coord          Y-Coord          swmm.Label
  2537265.360      430245.310       "Inflow2"  ""  "Arial"  10  1  0
  2538982.380      430305.940       "Inflow3"  ""  "Arial"  10  1  0
  2534251.330      432823.110       "Inflow1"  ""  "Arial"  10  1  0
  2543966.250      434804.260       "Outfall"  ""  "Arial"  10  1  0
  */
   override var indices = List(0, 2,19,36,200)
   override var dataIndice = List(0,19,36,200)
}
