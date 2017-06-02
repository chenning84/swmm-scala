package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class CoordinatesSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[COORDINATES]
;;Node             X-Coord          Y-Coord
  20704016         1369320.630      423104.590
  20704017         1369546.250      423063.190
  20704018         1369775.750      423043.000
  20704025         1370004.380      423023.410
  20712009         1369479.630      425264.190
  20712010         1369708.250      425243.590
  20712011         1369896.630      425226.810
  20713008         1369976.750      426643.190
  23701001         1370586.440      427120.900
    */


   override var indices = List(0, 2,19,36,200)
   override var dataIndice = List(0,19,36,200)
}
