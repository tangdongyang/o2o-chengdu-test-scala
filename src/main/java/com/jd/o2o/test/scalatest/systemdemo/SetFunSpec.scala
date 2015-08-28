package com.jd.o2o.test.scalatest.systemdemo

import org.scalatest.FunSpec

/**
 * Created by Administrator on 2015/8/27.
 */
class SetFunSpec extends FunSpec {

  describe("A Set") {
    describe("when empty") {
      it("should have size 0") {
        assert(Set.empty.size == 0)
      }

      it("should produce NoSuchElementException when head is invoked") {
        intercept[NoSuchElementException] {
          Set.empty.head
        }
      }
    }
  }
}
