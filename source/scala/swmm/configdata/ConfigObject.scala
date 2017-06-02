package swmm.configdata

import swmm.configdata.jnodes.types._
import swmm.configdata.jnodes.{GlobalContext, InflowNode}
import swmm.configdata.types.TNodeUtil
import swmm.runoff.infil.SInfil
import swmm.runoff.{SSubcatch, SGage}
import swmm.util.Project

/**
 * Created by ning on 10/23/15.
  *
 */
class ConfigObject(val name: String, val hd: List[List[String]], val dat: List[List[String]]) {
  val gInstance = Project.getInstance

  def parseData() = {
    name match {
      case "RAINGAGES" =>
        gInstance.Gage = dat.map(SGage.gage_readParams(_)).toArray
        for (i <- 0 to gInstance.Gage.size - 1) {
          gInstance.Gage(i).pos = i
        }
      case "SUBCATCHMENTS" =>
        gInstance.Subcatch = dat.map(SSubcatch.subcatch_readParams(_)).toArray
        for (i <- 0 to gInstance.Subcatch.size - 1) {
          gInstance.Subcatch(i).pos = i
        }
      case "SUBAREAS" =>
        //gInstance.Subcatch =gInstance.Subcatch++dat.map(SSubcatch.subcatch_readParams(_)).toArray
        dat.map(SSubcatch.subcatch_readSubareaParams(_)).toArray

      case "INFILTRATION" =>
        dat.map(SInfil.infil_readParams(_))
      case "JUNCTIONS" =>
        gInstance.Node= dat.map(TNodeUtil.readNode(name,_).asInstanceOf[TNode]).toArray
        for (i <- 0 to gInstance.Node.size - 1) {
          gInstance.Node(i).pos = i
        }
      case "OUTFALLS" =>
        gInstance.Outfall= dat.map(TNodeUtil.readNode(name,_).asInstanceOf[TOutfall]).toArray
        for (i <- 0 to gInstance.Outfall.size - 1) {
          gInstance.Outfall(i).pos = i
        }
      case "STORAGE" =>
        gInstance.Storage= dat.map(TNodeUtil.readNode(name,_).asInstanceOf[TStorage]).toArray
        for (i <- 0 to gInstance.Storage.size - 1) {
          gInstance.Storage(i).pos = i
        }
      case "DIVIDER" =>
        gInstance.Divider= dat.map(TNodeUtil.readNode(name,_).asInstanceOf[TDivider]).toArray
        for (i <- 0 to gInstance.Divider.size - 1) {
          gInstance.Divider(i).pos = i
        }
      case "CONDUITS" =>
       // gInstance.Conduit= dat.map(TNodeUtil.readNode(name,_).asInstanceOf[TConduit]).toArray
      case "PUMP"=>
      case "ORIFICE" =>
      case "WEIR"=>
      case "OUTLET"=>

      case "WEIRS" =>
      case "XSECTION"=>
      // return link_readXsectParams(Tok, Ntokens);

      case "TRANSECT"=>
   // return transect_readParams(&Mobjects[TRANSECT], Tok, Ntokens);

      case "LOSSES" =>
   // return link_readLossParams(Tok, Ntokens);




      //return readLink(OUTLET);

      case _ =>
        println(name + "  no match ")

    }
  }
}
