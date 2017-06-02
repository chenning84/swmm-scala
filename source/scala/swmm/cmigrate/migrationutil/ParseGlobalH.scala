package swmm.cmigrate.migrationutil

import scala.io.Source
import ParserUtils._

/**
 * Created by ning on 10/31/15.
  *
 */
class ParseGlobalH {
  def parseFile(fileName:String): String = {
    Source.fromFile(fileName).mkString
  }
  def parseIntoBlocs(content:String,delimit:String):List[String ] ={
    
    val blocks = content.split(delimit).filter(!"".equals(_)).map(_.trim)
    blocks.toList.flatMap(convertBlock)
  }
  def convertBlock(bloc:String) :List[String] ={
    val lines = bloc.split("\n").filter(!"".equals(_))
    convertLineInBlock(lines.toList,"")
  }
  def convertLineInBlock(lines:List[String],sType:String):List[String] = lines match{
    case Nil =>Nil
    case head::Nil=>parseOneLine(head,sType)._1::Nil
    case head::tail =>
      val abc = parseOneLine(head,sType)
      abc._1::convertLineInBlock(tail,abc._2)
  }
  def parseOneLine(line:String,sType:String) :(String,String) =
  {
    if(isCommentLine(line))
      (line,"")
    else
      convertNonCommentLine(line,sType)
  }
  def convertNonCommentLine(line:String,sType:String):(String,String) ={
    val units = line.split(";|,")
    val nonTrivial = units(0)
    val trival = if(units.length>1) units(1) else ""
    if(nonTrivial!=null)
      {
        val words =nonTrivial.split(" ").filter(!"".equals(_)).map(_.replace("*",""))

        if(words.length==1)
        {
          if(sType.equals(""))
            ( "", words(0))
          else
            ( "var " + words(0) +":" +sType +"  "+trival,sType)
        }
        else if(words.length ==2)
        {
          ("var " + words(1) +":" +words(0)+"  "+trival,words(0))
        }
        else{
          (line,sType)
        }

      }
    else
      {
        (line,sType)
      }
  }

}
