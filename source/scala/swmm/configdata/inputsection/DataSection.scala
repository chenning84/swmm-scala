package swmm.configdata.inputsection

import swmm.configdata.ConfigObject

/**
 * Created by ning on 7/4/15.
 */
/*
;;                                                   Total    Pcnt.             Pcnt.    Curb     Snow
;;Name             Raingage         Outlet           Area     Imperv   Width    Slope    Length   Pack
;;----------------------------------------------------------------------------------------------------

 */
abstract class DataSection(n: String, hd: List[String], dat: List[String]) extends ConfigCollection(n,hd,dat) {
  var indices :List[Int]
  var  dataIndice :List[Int]

  override def initSectionData  : ConfigObject = {

    val headers = header.
      // Remove line breaker line
      filter(!_.contains("------"))
      // Break the string into string list and trim
      .map(lSplit(indices, _) map (_.trim))
      // Remove Leading ;;
      .map(_.tail)
    //    val combined = headers(0) zip headers(1)

    val realData = dataSec.map(lSplit(dataIndice, _) map (_.trim))

     new ConfigObject(name,headers,realData)
  }
//  def getIndices ():List[Int]
//  def getDataIndice() :List[Int]
  def toMTree(): Unit = {

  }
  /*
 Split a string into list base on indices
 lSplit( List(0,4,6,8), "20131103")
List[String] = List(2013, 11, 03)
 */
  def lSplit(indices: List[Int], s: String) = {
    //print(s+"\n")
    (indices zip (indices.tail)) map { case (a, b) =>
      if(s.length>=b)
        s.substring(a, b)
      else if(s.length > a)
        s.substring(a, s.length)
      else
        s.substring(s.length, s.length)
    }
  }

  def lsplit(pos: List[Int], str: String): List[String] = {
    val (rest, result) = pos.foldRight((str, List[String]())) {
      case (curr, (s, res)) =>
        val (rest, split) = s.splitAt(curr)
        (rest, split :: res)
    }
    rest :: result
  }

}