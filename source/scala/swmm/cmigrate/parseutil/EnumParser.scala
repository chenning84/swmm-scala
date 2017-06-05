package swmm.cmigrate.parseutil

import java.io.File

import scala.io.Source

/**
  * Created by ning on 6/4/17.
  */
object EnumParser {
  def getLines( fileName:File):String ={
    println( "{\n\"name\":\""+fileName.getAbsolutePath.replace("testcase/","").replace(".java","").replace("/",".")+"\",")
    val lines =Source.fromFile(fileName.getAbsolutePath).getLines().drop(3).filter(_.trim.startsWith("*"))
    print("\"desc\":\"")
    for (line <- lines) {
      if( !"".equals(line.trim ))
        print(line.replace("/*","").replace("*/","").replace("*","")+"|")
    }
    println("\"\n},")
    ""
  }

  def getListOfFiles(dir: String):List[File] = {
    val fileTypes  = Set(".c",".h")
    val d = new File(dir)
    if(d.exists() && !d.getName().startsWith("."))
    {
      if(d.isDirectory)
      {
        d.listFiles().flatMap(f=> getListOfFiles(f.getAbsolutePath)).toList
      }
      else if (( d.isFile) &&
        (d.getName().endsWith(".c")||(d.getName().endsWith(".h")))
      )
      {
        println(getLines(d))
        List(d)
      }
      else {
        Nil
      }

    }
    else {
      Nil
    }
  }

}
