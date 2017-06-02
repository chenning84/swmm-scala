package swmm.runoff.lid

import swmm.runoff.lid.LidH.TLidUnit

/**
 * Created by ning on 11/3/15.
 */
object SLid {
  ////  New error messages added to release 5.1.008.  ////                       //(5.1.008)
  val ERR_PAVE_LAYER =" - check pavement layer parameters"
  val ERR_SOIL_LAYER =" - check soil layer parameters"
  val ERR_STOR_LAYER =" - check storage layer parameters"
  val ERR_SWALE_SURF =" - check swale surface parameters"
  val ERR_GREEN_AMPT =" - check subcatchment Green-Ampt parameters"
  val ERR_DRAIN_OFFSET= " - drain offset exceeds storage height"
  val ERR_SWALE_WIDTH =" - invalid swale width"
  object LidLayerTypes extends Enumeration {
    type LidLayerTypes = Value
    val
    SURF,                    // surface layer
    SOIL,                    // soil layer
    STOR,                    // storage layer
    PAVE,                    // pavement layer
    DRAINMAT,                // drainage mat layer
    DRAIN = Value
  }
  val LidLayerWords =  Array
  ("SURFACE", "SOIL", "STORAGE", "PAVEMENT", "DRAINMAT", "DRAIN")

  val  LidTypeWords =Array
  ("BC",                   //bio-retention cell
    "RG",                   //rain garden
    "GR",                   //green roof
    "IT",                   //infiltration trench
    "PP",                   //porous pavement
    "RB",                   //rain barrel
    "VS",                   //vegetative swale
    "RD"                   //rooftop disconnection
    )
  def  lid_create( lidCount:Int,  subcatchCount:Int):Unit=
  {

  }
  def lid_initState() :Unit=
  {

  }

}
class SLid {
  //-----------------------------------------------------------------------------
  //  Data Structures
  //-----------------------------------------------------------------------------

  // LID List - list of LID units contained in an LID group
  class LidList
    {
      var        lidUnit:TLidUnit   =null  // ptr. to a LID unit
      var  nextLidUnit :LidList=null
    }
  type  TLidList = LidList

  // LID Group - collection of LID units applied to a specific subcatchment
  ////  Re-defined for release 5.1.008. ////                                     //(5.1.008)
  class LidGroup
    {
      var         pervArea:Double =.0      // amount of pervious area in group (ft2)
      var         flowToPerv:Double =.0    // total flow sent to pervious area (cfs)
      var         oldDrainFlow:Double =.0  // total drain flow in previous period (cfs)
      var         newDrainFlow:Double =.0  // total drain flow in current period (cfs)
      var      lidList:TLidList=null       // list of LID units in the group
    }
  type  TLidGroup=LidGroup


}
