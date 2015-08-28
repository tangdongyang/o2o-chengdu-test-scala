package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.FreeSpec

/**
 * Created by Administrator on 2015/8/27.
 */
class SetFreeSpec extends FreeSpec {

  "A Set" - {
    "when empty" - {
      "should have size 0" in {
        assert(Set.empty.size == 0)
      }

      "should produce NoSuchElementException when head is invoked" in {
        intercept[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
