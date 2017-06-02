package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class TransectSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
/*
[TRANSECTS]
;;--------------------------------------------------------------------------------------------

 NC 0.021    0.035    0.023
 X1 1                 11       70       100      0.0      0.0      0.0      0        0
 GR 20       0        11.5     0.1      10       40       8        49       9.5      52
 GR 10       70       9.5      88       8        91       10       100      11.5     139.9
 GR 20       140

 NC 0.021    0.035    0.023
 X1 2                 11       70       100      0.0      0.0      0.0      0        0
 GR 20       0        11.5     0.1      10       40       8        49       9.5      52
 GR 10       70       9.5      88       8        91       10       100      11.5     139.9
 GR 20       140

 NC 0.021    0.035    0.023
 X1 3                 11       70       100      0.0      0.0      0.0      0        0
 GR 20       0        11.5     0.1      10       40       8        49       9.5      52
 GR 10       70       9.5      88       8        91       10       100      11.5     139.9
 GR 20       140
    */
   override var indices = List(0, 2,5,13,31,40,49,58,67,76,85,200)
   override var dataIndice = List(0,5,13,31,40,49,58,67,76,85,200)
}
