package swmm.test

/**
 * Created by ning on 6/3/15.
 *
 */
class PascalTriangle {
  def forOneList (i:Int) : List[Int] =
    {
      if(i==1) List(1)

       else  1::agregateAdd(forOneList(i-1)):::List(1)
  }
  def agregateAdd(list:List[Int]):List[Int]= list match
  {
    case head::Nil => List()
    case head::tail =>head +tail.head ::agregateAdd(tail)
    case _ => List()
  }
  def counting(target:String):Boolean =
  {
    var tList = target.toList
    def charToInt(l:Char): Int = l match {
      case '(' => 1;
      case ')' => -1;
      case _ => 0
    }
    val binList =tList.map(charToInt)

    var sum =0
    (0 to binList.size).toStream.takeWhile(_ => sum >=0).foreach(i => sum+=binList(i))
    if( sum ==0) true else false

  }
  def assing3week1(amount:Int,domomitations:List[Int]) :List[List[Int]] =
  {
      domomitations.flatMap( elem =>  combineHead(amount ,domomitations.drop(domomitations.indexOf(elem))))
  }
  def combineHead(amount:Int ,demom :List[Int]):List[List[Int]]= demom match
  {
    case head::tail =>

        var i: Int = amount - head
      if(i >0) {

          //val result =demom.flatMap(elem => combineHead( amount-head ,demom.drop(demom.indexOf(elem))))
          val result =assing3week1(i,demom)
          result.map(head::_)
        }
        else if (i <0)
          {
            Nil
//            combineHead(amount , tail)
          }
        else
          {
            List(List(head))
          }


    case _ => Nil
  }

//  def countChange(amount:Int,domo:List[Int]):List[List[Int]] =
//  {
//   // amount , domomitations.drop(domomitations.indexOf(elem))).filter(_._1 ==0)
//  }
}

//Reference to Scala package object


