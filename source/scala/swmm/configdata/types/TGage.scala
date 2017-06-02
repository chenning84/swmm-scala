package swmm.configdata.types

import org.joda.time.DateTime
import swmm.configdata.jnodes.types.TBase

/**
 * Created by Ning on 11/13/15.
  *
 */
class TGage extends TBase {


//  var ID: String = null
  var dataSource: String = ""
  var tSeries: Int = 0
  var fname: String = null
  var staID: String = null
  var startFileDate: DateTime = null
  var endFileDate: DateTime = null
  var rainType: Int = 0
  var rainInterval: Int = 0
  var rainUnits: Int = 0
  var snowFactor: Double = .0
  var startFilePos: Long = 0L
  var endFilePos: Long = 0L
  var currentFilePos: Long = 0L
  var rainAccum: Double = .0
  var unitsFactor: Double = .0
  var startDate: DateTime = null
  var endDate: DateTime = null
  var nextDate: DateTime = null
  var rainfall: Double = .0
  var nextRainfall: Double = .0
  var reportRainfall: Double = .0
  var coGage: Int = 0
  var isUsed: Boolean=false
  var isCurrent: Int = 0

}
