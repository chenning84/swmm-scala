package swmm.cmigrate.migrationutil

import org.scalatest.FlatSpec
import swmm.configdata.jnodes.SwmmEnum.EvapType
import swmm.util.FileUtils
import swmm.cmigrate.migrationutil.ParserUtils._

/**
 * Created by ning on 10/31/15.
 * Utilitiy program used to parse SObject and generate individual java class
 */
//class ChopToClasses  {
class ChopToClasses extends FlatSpec  {
  def fixture =
    new {
      val globalHParser = new ParseGlobalH()
      val content = globalHParser.parseFile("/home/ning/sdcard/code/swmm-scala/scode/SObjects.java")


    }

  it should "has block " in {

    val eachBlock = fixture.content.split("//-----")
      .filter(!"".equals(_)).drop(3)
      .map(_.replace("--",""))
      .map(_.replace("-\n",""))

    FileUtils.extractClassInfo(eachBlock.toList,"")

    assert(eachBlock.length>0)

  }
  it should "has name" in
  {
     val enumValue =EvapType.DRYONLY
     printf("name ="+enumValue.toString+"\n")
     printf("name ="+EvapType.values()(0)+"\n")

  }
}
