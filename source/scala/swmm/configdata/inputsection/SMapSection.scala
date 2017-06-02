package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class SMapSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[MAP]
 DIMENSIONS      2534198.450      427370.210       2545408.550      434812.510
 UNITS           None
    */
   override var indices = List(0, 2,17,34,51,68,200)
   override var dataIndice = List(0,17,34,51,68,200)
}
