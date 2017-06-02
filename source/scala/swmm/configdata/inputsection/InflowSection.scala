package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class InflowSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[INFLOWS]
;;                                                   Concen   Conversion
;;Node             Parameter        Time Series      /Mass    Factor
;;----------------------------------------------------------------------
  102              FLOW             INFLOW@102
  1SB3_MIL         FLOW             INFLOW@1SB3_MIL
  1SB1             FLOW             INFLOW@1SB1
    */
   override var indices = List(0, 2,19,36,53,62,73)
   override var dataIndice = List(0,19,36,53,62,73)
}
