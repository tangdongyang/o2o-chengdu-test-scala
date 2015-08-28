package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.FunSuite

/**
 * Created by Administrator on 2015/8/27.
 */
class SetFunSuite extends FunSuite {

  test("An empty Set should have size 0") {
    assert(Set.empty.size == 0)
  }

  test("Invoking head on an empty Set should produce NoSuchElementException") {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  }
}
