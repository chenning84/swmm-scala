package swmm.runoff.infil

import swmm.configdata.jnodes.GlobalContext
import swmm.configdata.jnodes.SwmmEnum._
import swmm.util.Project

/**
  * Created by ning on 11/6/15.
  */
trait InfilT {
//  def     create( subcatchCount:Int,  model:Int) :Unit
// // def      delete(void);
//  def      readParams( model:Int, tok:String, ntoks:Int):Int
//  def     initState( area:Int,  model:Int):Unit
//  def     getState(j:Int,  m:Int,  x:Array[Double]):Unit
//  def     setState( j:Int,  m:Int, x:Array[Double]):Unit
//  def   getInfil( area:Int,  model:Int,  tstep:Double,  rainfall:Double,
//     runon:Double,  depth:Double):Double
val TRUE:Int =1
val FALSE:Int =0
val adjust =Project.getInstance.Adjust
val evap =Project.getInstance.Evap
//  def findEnumInt[T](a:Array[T],e:T):Int =
//  {
//     a.indexWhere(_.equals(e))
//  }
  val RAINFALL = ConversionType.RAINFALL.ordinal()
  val RAINDEPTH = ConversionType.RAINDEPTH.ordinal()

}
