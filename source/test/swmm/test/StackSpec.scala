package swmm.test

/**
 * Created by Ning on 10/30/15.
 */

import org.joda.time.format.DateTimeFormat
import swmm.configdata.jnodes.SwmmEnum.InputOptionType

import scala.collection.mutable.{ListBuffer, Stack}
import org.scalatest._

class StackSpec extends FlatSpec {
  def fixture =
    new {
      val builder = new StringBuilder("ScalaTest is ")
      val buffer = new ListBuffer[String]
    }

  "Testing" should "be easy" in {
    val f = fixture
    f.builder.append("easy!")
    printf("first value :"+InputOptionType.FLOW_UNITS.ordinal())
    printf("next value :"+InputOptionType.SWEEP_START.ordinal())
    val  formatter1 = DateTimeFormat.forPattern("dd/MM/yyyy")
    val  formatter2 = DateTimeFormat.forPattern("HH:mm:ss")
    //val  formatter1 = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")
    val s1 ="08/11/1998"
    val s2 ="01:20:01"
    val date = formatter1.parseDateTime(s1)
    val time = formatter2.parseLocalTime(s2)
    println("\n hour=" + time.getHourOfDay +" minutes =" +time.getMinuteOfHour+ " seconds =" + time.getSecondOfMinute)

    assert(f.builder.toString == "ScalaTest is easy!")
    assert(f.buffer.isEmpty)
    f.buffer += "sweet"

  }

  it should "be fun" in {
    val f = fixture
    f.builder.append("fun!")
    assert(f.builder.toString == "ScalaTest is fun!")
    assert(f.buffer.isEmpty)
  }

//  "A Stack" should "pop values in last-in-first-out order" in {
//    val stack = new Stack[Int]
//    stack.push(1)
//    stack.push(2)
//    assert(stack.pop() === 2)
//    assert(stack.pop() === 8)
//
//  }
//
//  it should "throw NoSuchElementException if an empty stack is popped" in {
//    val emptyStack = new Stack[String]
//    intercept[NoSuchElementException] {
//      emptyStack.pop()
//    }
//  }
//
}