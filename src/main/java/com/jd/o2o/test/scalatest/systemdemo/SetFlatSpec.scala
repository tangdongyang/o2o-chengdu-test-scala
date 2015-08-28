package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.FlatSpec

/**
 * Created by Administrator on 2015/8/27.
 */
class SetFlatSpec extends FlatSpec {

  "An empty Set" should "have size 0" in {
    assert(Set.empty.size == 0)
  }

  it should "produce NoSuchElementException when head is invoked" in {
    intercept[NoSuchElementException] {
      Set.empty.head
    }
  }
}
