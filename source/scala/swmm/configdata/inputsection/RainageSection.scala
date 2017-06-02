package swmm.configdata.inputsection

import org.joda.time.DateTime
import swmm.cmigrate.{SwmmConst, Keywords}
import swmm.configdata.jnodes.Errorh.ErrorType
import swmm.configdata.jnodes.GlobalContext
import swmm.configdata.jnodes.SwmmEnum.GageDataType
import swmm.configdata.types.TGage
import swmm.util.ErrorUtil

/**
 * Created by Ning on 10/22/15.
  *
 */
class RainageSection( n: String, hd: List[String], dat: List[String]) extends DataSection(n, hd, dat) {
  /*
;;                 Rain      Recd.  Snow   Data       Source           Station    Rain
;;Name             Type      Freq.  Catch  Source     Name             ID         Units
;;-------------------------------------------------------------------------------------
  GAGE1            VOLUME    0.05000 1.0    TIMESERIES RainSeries1
  */
  override var indices = List(0, 2, 19,29,36,43,54,71,82,87)
  override var dataIndice = List(0, 19,29,36,43,54,71)



}