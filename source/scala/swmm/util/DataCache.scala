package swmm.util

import swmm.configdata.jnodes.types.TBase

import scala.collection.parallel.mutable
import scala.collection.mutable.Map

/**
  * Created by ning on 11/29/15.
  */
object DataCache{
  def getInstance: DataCache = {
    if (singletonInstance == null) {
      singletonInstance = new DataCache
    }
    singletonInstance
  }

  private var singletonInstance: DataCache = null
}
class DataCache {
  var cache:Map[String,Map[String,TBase]]=null
//  var cache1:Map[String,Set[String]]=null

  def findObject(oType:String,key:String) :Option[TBase]= cache match
  {
    case null =>
      //      val entry = Map(key->value)
      //      cache ++ Map(oType ->entry)
      None
    case _ =>
      if((cache.get(oType)!=null)&&(cache.get(oType).exists(_.contains(key))))
        Option(cache.get(oType).get(key))
      else
        None
  }
  def findId(oType:String,key:String) :Option[String]= cache match
  {
    case null =>
//      val entry = Map(key->value)
//      cache ++ Map(oType ->entry)
      None
    case _ =>
      val subMap =cache.get(oType)
      if(subMap!=null)
        {
          println("foundit :" +key +" contains="+subMap.exists(_.contains(key))+ " \n")

          if(subMap.exists(_.contains(key)))
            {
              Option(key)

            }
          else
            None

        }
      else
        None

  }
  def insert(oType:String, key:String, bObject:TBase )=
  {
    def insertInternal(oType:String, key:String, bObject:TBase ):Map[String,Map[String,TBase]]= cache match
    {
      case null =>
        val entry = Map(key->bObject)
         Map(oType ->entry)
      //      Option(null)
      case _ =>
        if(cache.get(oType)!=null)
        {
          //  val entry = Map(key->bObject)
          val subMap = cache.get(oType)
          val filtered = subMap.filter(!_.contains(key)).map(_+(key->bObject))
          cache =cache + (oType -> filtered.get)

        }
        cache

    }
    cache =insertInternal(oType,key,bObject)
  }

//  def insert1(oType:String,key:String )= cache1 match
//  {
//    case null =>
//      val entry = Set(key)
//      cache1 = Map(oType ->entry)
//    //      Option(null)
//    case _ =>
//      if(cache1.get(oType)!=null)
//      {
//        val entry = Set(key)
//        cache1 =cache1 ++ Map(oType ->entry)
//        if(!cache1.get(oType).contains(key))
//          cache1.get(oType) == cache1.get(oType) ++ Set(key )
//      }
//
//
//  }
}
