package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class ReportSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[REPORT]
 INPUT     YES
 CONTROLS  NO
    */
   override var indices = List(0, 11,100)
   override var dataIndice = List(0,11,100)
}
