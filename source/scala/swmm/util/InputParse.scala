package swmm.util

import swmm.ConfigCollection
import swmm.configdata.inputsection.DataSection

import scala.io.Source

/**
 * Created by ning on 5/27/15.
  *
 */
class InputParse(val fileName: String) {
  def parseFile(): List[String] = {
    Source.fromFile(fileName).getLines.filter(!"".equals(_)).toList
  }

  def getName(lines: List[String]): String = {
    if (lines.nonEmpty)
      lines.head.replace("[", "").replace("]", "").trim
    else
      ""
  }

  def parseSection(lines: List[String]): DataSection = {
    val name = getName(lines.head)
//    printf(name + "\n")
    //lines takeRight( lines.length -1)
    val headers = lines.tail.takeWhile(_.indexOf(";;") >= 0)
    val data = lines.tail.dropWhile(_.indexOf(";;") >= 0)
    ConfigCollection.parseActualSection(name, headers, data)
  }

  def getName(section: String): String = {
    val lines = section.split(System.getProperty("line.separator"))
    if (lines.nonEmpty)
      lines(0).replace("[", "").replace("]", "").trim
    else
      ""
  }

  def getHeader(lines: List[String]): List[String] = {
    lines.takeWhile(_.indexOf(";;") > 0)
  }


  def divideSection(lines: List[String]): List[List[String]] = lines match {
    case Nil => List(Nil)
    case head :: tail =>
      //  def takeParsingStructSection(lines: List[String]): (List[String], List[String]) = lines match
      //    {
      //
      //  }
      def takeOneInputSection(lines: List[String]): (List[String], List[String]) = lines match {
        case Nil => (Nil, Nil)

        case `head` :: tail =>
          var count = 0

          if (lines.head.contains("["))
            count = 1
          var notDone = true
          while (!lines(count).contains("[") && notDone) {
            count = count + 1
            if (count == lines.length) {
              count = lines.length - 1
              notDone = false
            }

          }
          if (count != lines.length - 1)
            lines.splitAt(count)
          else
            (lines, Nil)


      }
      val (t1, t2) = takeOneInputSection(lines)
      t1 :: divideSection(t2)


  }
}