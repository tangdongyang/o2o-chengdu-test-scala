package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.{Tag, FlatSpec}

import scala.collection.mutable

/**
 * Created by Administrator on 2015/9/3.
 */
class StackFlatSpec extends FlatSpec {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new mutable.Stack[Int]
    stack.push(1)
    stack.push(2)

    assert(stack.pop() == 2)
    assert(stack.pop() == 1)
  }

  ignore should "throw NoSuchElementException if an empty Stack is poped" in {
    val emptyStack = new mutable.Stack[String]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
  }
}

object SlowTest extends Tag("com.jd.o2o.test.scalatest.systemdemo.SlowTest")
object DbTest extends Tag("com.jd.o2o.test.scalatest.systemdemo.DbTet")

class ExampleFlatSpec extends FlatSpec {

  "The Scala language" must "add correctly" taggedAs(SlowTest) in {
    val sum = 1 + 1
    assert(sum === 2)
  }

  it must "subtract correctly" taggedAs(SlowTest, DbTest) in {
    val diff = 4 - 1
    assert(diff === 3)
  }
}
