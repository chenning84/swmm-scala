package swmm.util

import java.io._

import swmm.ConfigCollection
import swmm.configdata.inputsection.DataSection
import swmm.cmigrate.migrationutil.ParserUtils._
import scala.io.Source

/**
 * Created by ning on 10/31/15.
  *
 */
object FileUtils {
 def saveFile(fileName:String, content:String ) =
 {
   val file = new File(fileName)
 //  file.createNewFile();
   val bw = new BufferedWriter(new FileWriter(file))
   bw.write(content)
   bw.close()
 }
  def parseFile(fileName:String): List[String] = {
    Source.fromFile(fileName).getLines.toList
  }
  def extractFileNameContent(content:String ,comment:String) =
  {
    val index = content.indexOf("{")
    val fileName = content.take(index-1).replace("public class","").trim
    FileUtils.saveFile("/tmp/"+fileName+".java","package swmm.configdata.jnodes.types;\n\n"+comment+"\n  "+content.replace("public class  ","class  "))
  }
  def extractClassInfo(lines:List[String],commentHeader:String):Unit = lines match
  {
    case Nil => Nil
    case head::tail => {
      var newHead =head
      if(head.startsWith("\n"))
         newHead=head.drop(1)
      if(isCommentLine(newHead))
        extractClassInfo(tail,commentHeader+head)
      else if(head.startsWith("public static final"))
      {
        extractClassInfo(tail,""+commentHeader+"//"+head)
      }
      else{
        extractFileNameContent(head,commentHeader)
        extractClassInfo(tail,"")

      }
    }
  }
}
