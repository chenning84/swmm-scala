package swmm.configdata.inputsection

import swmm.configdata.ConfigObject

/**
 * Created by ning on 7/4/15.
 */
abstract class ConfigCollection(val name: String,  val header: List[String],  val dataSec: List[String]) {

  def buildMap(lines: List[String]): Map[String, String] = {
    def breakup(l: String): (String, String) = {
      val twoRow = l.split(" ").filter(!_.equals(""))
      if (twoRow.length == 2)
        (twoRow(0), twoRow(1))
      else
        ("", "")
    }
    // val pairMap =Map[String,String]
    //lines map(l=> {val pairs = breakup(l);pairMap(pairs._1) =pairs._2} )
    //   val pairMap = new scala.collection.mutable.Map[String,String]

    lines map {
      breakup(_)
    } toMap
  }

  def  initSectionData: ConfigObject



}