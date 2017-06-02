package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class StorageSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
/*
[STORAGE]
;;                 Invert   Max.     Init.    Shape      Shape                      Ponded   Evap.
;;Name             Elev.    Depth    Depth    Curve      Parameters                 Area     Frac.
;;------------------------------------------------------------------------------------------------
  1SB1             704.45   35.55    0.0      TABULAR    1SB1                       0        0
  99               682.0    8.0      0.0      TABULAR    99                         0        0


 */
   override var indices = List(0, 2,19,28,37,46,57,84,93,200)
   override var dataIndice = List(0,19,28,37,46,57,84,93,200)
}
