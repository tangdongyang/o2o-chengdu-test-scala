package com.jd.o2o.test.scalatest.systemdemo

/**
 * Created by Administrator on 2015/9/30.
 */

import org.scalacheck.Gen
import org.scalatest.{Matchers, FlatSpec}
import org.scalatest.prop.TableDrivenPropertyChecks._

class FractionTest extends FlatSpec with Matchers {

  val fractions = Table(
    ("n", "d"), // first tuple defines column names
    (1, 2), // subsequent tuples define the data
    (-1, 2),
    (1, -2),
    (-1, -2),
    (3, 1),
    (-3, 1),
    (-3, 0),
    (3, -1),
    (3, Integer.MIN_VALUE),
    (Integer.MIN_VALUE, 3),
    (-3, -1)
  )

  forAll (fractions) { (n: Int, d: Int) =>

    whenever(d != 0 && d != Integer.MIN_VALUE && n != Integer.MIN_VALUE) {

      val f = new Fraction(n, d)

      if(n < 0 && d < 0 || n > 0 && d > 0) {
        f.numer should be > 0
      } else if(n != 0) {
        f.numer should be < 0
      } else {
        f.numer shouldEqual (0)
      }

      f.denom should be > 0
    }
  }

  val invalidCombos = Table(
    ("n", "d"),
    (Integer.MIN_VALUE, Integer.MIN_VALUE),
    (1, Integer.MIN_VALUE),
    (Integer.MIN_VALUE, 1),
    (Integer.MIN_VALUE, 0),
    (1, 0)
  )

  forAll (invalidCombos) { (n: Int, d: Int) =>

    an [IllegalArgumentException] should be thrownBy {
      new Fraction(n, d)
    }
  }

  // Testing mutable objects
  val stateTransitions = Table(
    ("action", "expectedCount"),
    (Start, 0),
    (Click, 1),
    (Click, 2),
    (Click, 3),
    (Enter(5), 5),
    (Click, 6),
    (Enter(1), 1),
    (Click, 2),
    (Click, 3)
  )

  val counter = new Counter
  forAll (stateTransitions) { (action, expectedCount) =>
    action match {
      case Start => counter.reset()
      case Click => counter.click()
      case Enter(n) => counter.enter(n)
    }

    counter.count should equal (expectedCount)
  }

  // Supplying generators
  val evenInts = for(n <- Gen.choose(-1000, 1000)) yield 2 * n
  org.scalatest.prop.GeneratorDrivenPropertyChecks.forAll (evenInts) { (n) =>
    n % 2 should equal (0)
  }

  val validNumers = for (n <- Gen.choose(Integer.MIN_VALUE + 1, Integer.MAX_VALUE)) yield n
  val validDenoms = for (d <- validNumers if d != 0) yield d

  org.scalatest.prop.GeneratorDrivenPropertyChecks.forAll (validNumers, validDenoms) { (n: Int, d: Int) =>

    org.scalatest.prop.GeneratorDrivenPropertyChecks.whenever(d != 0 && d != Integer.MIN_VALUE && n != Integer.MIN_VALUE) {

      val f = new Fraction(n, d)

      if(n < 0 && d < 0 || n > 0 && d > 0) {
        f.numer should be > 0
      } else if(n != 0) {
        f.numer should be < 0
      } else {
        f.numer shouldBe (0)
      }

      f.denom should be > 0
    }
  }

  org.scalatest.prop.GeneratorDrivenPropertyChecks.forAll ((validNumers, "n"), (validDenoms, "d")) { (n: Int, d: Int) =>

    org.scalatest.prop.GeneratorDrivenPropertyChecks.whenever(d != 0 && d != Integer.MIN_VALUE && n != Integer.MIN_VALUE) {

      val f = new Fraction(n, d)

      if(n < 0 && d < 0 || n > 0 && d > 0) {
        f.numer should be > 0
      } else if(n != 0) {
        f.numer should be < 0
      } else {
        f.numer shouldBe (0)
      }

      f.denom should be > 0
    }
  }
}

class Fraction(n: Int, d: Int) {

  require(d != 0)
  require(d != Integer.MIN_VALUE)
  require(n != Integer.MIN_VALUE)

  val numer = if (d < 0) -1 * n else n
  val denom = d.abs

  override def toString = numer + " / " + denom
}


abstract class Action
case object Start extends Action
case object Click extends Action
case class Enter(n: Int) extends Action

class Counter {
  private var c = 0
  def reset() { c = 0}
  def click() { c += 1}
  def enter(n: Int) {c = n}
  def count = c
}
