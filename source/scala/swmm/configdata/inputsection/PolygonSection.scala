package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
  *
 */
class PolygonSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
/*
[Polygons]
;;Subcatchment     X-Coord          Y-Coord
  SubCatchTW01060  119343.800       47675.800
  SubCatchTW01060  120580.800       47675.800
  SubCatchTW01060  120580.800       45923.800
  SubCatchTW01060  119343.800       45923.800
  SubCatchTW01100  121765.000       49481.800
  SubCatchTW01100  122700.800       49481.800
  SubCatchTW01100  122700.800       47675.800
  SubCatchTW01100  121765.000       47675.800
  SubCatchTW01160  123662.600       51554.600
    */
   override var indices = List(0, 2,19,36,200)
   override var dataIndice = List(0,19,36,200)
}
