package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
 */
class XSectionSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[XSECTIONS]
;;Link             Type         Geom1      Geom2      Geom3      Geom4      Barrels
;;---------------------------------------------------------------------------------
  583              IRREGULAR    1          0          0          0          1
  591              IRREGULAR    2          0          0          0          1
  592              IRREGULAR    3          0          0          0          1
  593              IRREGULAR    4          0          0          0          1
  594              IRREGULAR    5          0          0          0          1
  595              IRREGULAR    6          0          0          0          1
  596              IRREGULAR    7          0          0          0          1
  597              IRREGULAR    8          0          0          0          1
    */
   override var indices = List(0, 2,19,32,43,54,65,76,200)
   override var dataIndice = List(0,19,32,43,54,65,76,200)
}
