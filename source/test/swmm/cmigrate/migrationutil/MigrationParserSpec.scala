package swmm.cmigrate.migrationutil

import org.scalatest.FlatSpec



/**
 * Created by Ning on 10/30/15.
 *
 */
class MigrationParserSpec extends FlatSpec  {
  def fixture =
    new {
      val parser = new MigrateInputParser("test")
      val globalHParser = new ParseGlobalH()
      val content = globalHParser.parseFile("/home/ning/sdcard/code/swmm-scala/scode/globals.h")

    }
//  it should "pars file " in {
//    val content = fixture.globalHParser.parseFile("/home/ning/sdcard/code/swmm-scala/scode/globals.h")
//
//    assert(content!=null)
//    assert(content.startsWith("//"))
//  }
  it should "has block " in {
    val block: List[String] = fixture.globalHParser.parseIntoBlocs(fixture.content,"EXTERN")
    block.foreach(println(_))
    assert(block.nonEmpty)
    assert(block.exists(_.startsWith("//")))
  }
  it should "parse one of line" in
  {
    var line:String ="                  StartDate,                // Starting date"
    var block = fixture.globalHParser.convertNonCommentLine(line,"Int")
    assert(block._1.length>0)
    assert(block._1.startsWith("var"))
    line =" DateTime"
    block = fixture.globalHParser.convertNonCommentLine(line,"")

    assert(block._2.startsWith("DateTime"))
  }

  it should "test2" in
  {
    val f=fixture
//    f.parser.convertOneExtern(null,"EXTERN TOutfall*  Outfall;                  // Array of outfall nodes")
    val longOne ="EXTERN int\n                  Nobjects[MAX_OBJ_TYPES],  // Number of each object type\n" +
      "                  Nnodes[MAX_NODE_TYPES],   // Number of each node sub-type\n" +
      "                  Nlinks[MAX_LINK_TYPES],   // Number of each link sub-type\n" +
      "                  UnitSystem,               // Unit system\n" +
      "                  FlowUnits,                // Flow units\n" +
      "                  InfilModel,               // Infiltration method\n" +
      "                  RouteModel,               // Flow routing method\n" +
      "                  ForceMainEqn,             // Flow equation for force mains\n" +
      "                  LinkOffsets,              // Link offset convention\n" +
      "                  AllowPonding,             // Allow water to pond at nodes\n" +
      "                  InertDamping,             // Degree of inertial damping\n" +
      "                  NormalFlowLtd,            // Normal flow limited\n" +
      "                  SlopeWeighting,           // Use slope weighting"
    val allContent =f.globalHParser.parseIntoBlocs("TOutfall",longOne)
    allContent.foreach(println(_))
  }

}
