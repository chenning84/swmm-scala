package swmm.cmigrate.migrationutil

import swmm.util.InputParse

/**
 * Created by ning on 10/25/15.
 * Some Utitily class used to generate scala code from C header or source code
 */
object StructToClass {
  def main(args: Array[String]) {
    val inputPars = new MigrateInputParser("/home/ning/sdcard/code/swmm-scala/scode/objects.h")

    //  val inputPars = new InputParse("/home/trevor/code/aws/swmm-scala/data/zsection/user5.inp")

    val content =inputPars.parseFile()
    val section = inputPars.transfromHeaderToScala(content)
    if(section.length>0)
      section.map(println(_))

  }


}
