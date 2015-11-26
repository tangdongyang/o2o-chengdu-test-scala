package com.jd.o2o.test.scala.apply

/**
 * Created by Administrator on 2015/11/26.
 */
class GoodAdder {

  def apply(lhs: Int, rhs: Int) = lhs + rhs
}

class BadAdder(baseNumber: Int) {

  def apply(lhs: Int, rhs: Int) = lhs + rhs * baseNumber
}

object AdderTest extends App {

  val calculator = new GoodAdder
  println(calculator(1, 1))

  val enronAccountant = new BadAdder(50)
  println(enronAccountant(2, 2))
}
