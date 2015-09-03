package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.FlatSpec
import org.scalatest.Assertions._

/**
 * Created by Administrator on 2015/9/3.
 */
class UsingAssertions extends FlatSpec {

  // The assert macro
  val left = 2
  val right = 2
  assert(left == right, "left is " + left + ", right is " + right)

  // Expected results
  val a = 5
  val b = 2
  assertResult(3) {
    a - b
  }

  // Forcing failures
  // fail()
  // fail("I've got a bad feeling about this")

  // Intercepted exceptions
  val s = "hi"

  try {
    s.charAt(-1)
    fail()
  } catch {
    case _: IndexOutOfBoundsException => // Expected, so continue
  }

  intercept[IndexOutOfBoundsException] {
    s.charAt(-1)
  }

  // Assumptions
  assume(true)
  assume(true, "The database was down again")

  // Forcing cancelations
  // cancel()
  // cancel("Can't run the test because no internet connection was found")

  // Getting a clue
  // assert(1 + 1 === 3, "this is a clue")
  // assertResult(3, "this is a clue") {
  //  1 + 1
  // }

  withClue("this is a clue") {
    intercept[IndexOutOfBoundsException] {
      s.charAt(-1)
    }
  }
}
