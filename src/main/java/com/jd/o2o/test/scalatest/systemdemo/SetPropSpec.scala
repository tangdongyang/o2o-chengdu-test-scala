package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.TableDrivenPropertyChecks

import scala.collection.immutable.{BitSet, TreeSet, HashSet}

/**
 * Created by Administrator on 2015/8/27.
 */
class SetPropSpec extends PropSpec with TableDrivenPropertyChecks with Matchers {

  val examples = Table(
    "set",
    BitSet.empty,
    HashSet.empty[Int],
    TreeSet.empty[Int]
  )

  property("an empty Set should have size 0") {
    forAll(examples) {set =>
      set.size should be (0)
    }
  }

  property("invoking head on an empty Set should produce NoSuchElementException") {
    forAll(examples) {set =>
      a [NoSuchElementException] should be thrownBy {set.head}
    }
  }
}
