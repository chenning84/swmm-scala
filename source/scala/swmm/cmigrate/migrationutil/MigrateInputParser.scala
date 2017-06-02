package swmm.cmigrate.migrationutil

import swmm.ConfigCollection
import swmm.configdata.inputsection.DataSection

import scala.io.Source

/**
  * Created by ning on 5/27/15.
  */
object MigrateInputParser{
  val primitiveTypes =
    Map("int" ->"Int",
      "double"->"Double",
      "char"->"Char",
      "char*"->"String"
    )
  val stringType=Map("char*"->"String")
  val complexType =
     Map("DateTime"->"DateTime")
}
class MigrateInputParser(val fileName: String) {
   def parseFile(): List[String] = {
     Source.fromFile(fileName).getLines.toList
   }



//  def convertExternToDeclare(initType:String,lines:List[String]):List[String] = lines match
//  {
//    case Nil => Nil
//    case head::Nil =>{
//      val result =convertOneExtern(initType,head)
//      result._2 ::Nil
//
//    }
//    case head::tail =>{
//      val result =convertOneExternBlock(initType,head)
//      if(initType !=result._1)
//       result._2 ::convertExternToDeclare(result._1,tail)
//      else
//        result._2 ::convertExternToDeclare(result._1,tail)
//    }
//
//  }
  /*
EXTERN TOutfall*  Outfall;                  // Array of outfall nodes
 */
  def convertOneExtern(initType:String , line:String) :(String,String)=
  {
    if(line !=null)
    {
      //Split by EXTERN make them into a list
      val declaration =line.split("EXTERN").filter(!"".equals(_)).map(_.trim)
      val singleLine =line.split(" ").filter(!"".equals(_)).map(_.trim)
      val statWithComment = declaration.map(_.split(";|,").map(_.trim))
      if(declaration.length>0)
        {
           val dType = declaration(0).takeWhile(!"".equals(_))
           val nextPar = declaration(0).dropWhile(!"".equals(_))
          (initType,line)        }
      (initType,line)
    }
    else
      (initType,line)
  }

  /**
   * Find the who program body of typedef struct
   * @param line
   * @return
   */
  def hasStructDef(line:String): Boolean =
  {
    if((line!=null) &&(line.startsWith("typedef struct")) )
      true
    else
      false
  }
  def hasStruct(line:String): Boolean =
  {
    if((line!=null) &&(line.startsWith("struct")) )
      true
    else
      false
  }
  def findStructEnd(lines : List[String]):Int= lines match
  {
    case Nil => -1
    case head::tail =>{
      if ((head.indexOf("}"))<0){
        1+findStructEnd(tail)
      }
      else {
        0
      }

    }

  }
  def parseStructSection(lines: List[String]):(List[String],List[String]) =
    {
      val endLine = findStructEnd(lines)
      val structBody = lines.take(endLine+1)
      val rest = lines.drop(endLine+1)
      (structBody,rest)
    }

   def transfromHeaderToScala(lines: List[String]): List[String] = lines match {
     case Nil => Nil
     case head :: tail => {


       if (hasStructDef(head)) {
         val (classBody,restCode) = parseStructSection(tail)

         //Find the existing Struct Name
         val className =classBody.last.replace("}","").replace(";","\n").trim
         //Add class declaration header
         val classDef = "class  "+className
         // Find the last line and remote struct name
         val classEnd = classBody.takeRight(1).map(_.replace(className,"").replace(";","\n"))
         val scalaBody = classDef ::parseStructBody(classBody).dropRight(1):::classEnd
         scalaBody ::: transfromHeaderToScala(restCode)
       }
       else if (hasStruct(head)) {
         val result = parseStructSection(tail)
         result._1 ::: transfromHeaderToScala(result._2)
       }
       else {
         head :: transfromHeaderToScala(tail)
       }
     }
   }
   def parseStructBody (lines:List[String]):List[String] =
   {
      lines.map(transfromVarToScala(_))
   }
   def transfromVarToScala (line :String ):String ={
     val newLines = line.split(";")
     if(newLines.length>1){
       val firstPart = newLines.head
       val secondLines = firstPart.split(" ").filter(!_.trim.equals(""))
       if(secondLines.length>1){
         "var "+secondLines(1) +" " +secondLines(0)
       }
       else {
         firstPart
       }
     }
     else{
       line
     }
   }
 }