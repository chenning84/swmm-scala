package swmm.configdata.inputsection

/**
 * Created by Ning on 10/23/15.
  *
 */
class WeirSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
   /*
[WEIRS]
;;                 Inlet            Outlet                        Crest      Disch.     Flap End        End
;;Name             Node             Node             Type         Height     Coeff.     Gate Coeff.     Con.
;;---------------------------------------------------------------------------------------------------------
  WEIR1            1127             1128             TRANSVERSE   0.0        3.32       NO   0          0
  WEIR2            1127             1128             TRANSVERSE   4.0        2.64       NO   0          0
  WEIR3            1127             1128             TRANSVERSE   8.0        2.63       NO   0          0      
    */
   override var indices = List(0, 2,19,36,53,66,77,88,93,104,200)
   override var dataIndice = List(0,19,36,53,66,77,88,93,104,200)
}
