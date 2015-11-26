package com.jd.o2o.test.scala.calcdsl.test {

import com.jd.o2o.test.scala.calcdsl.{Calc, BinaryOp, UnaryOp, Number}
import org.scalatest.FlatSpec

  class CalcTest extends FlatSpec {

    "Number(n)" should "equal n" in {
      val n1 = Number(5)

      assert(5 == n1.value)
    }

    "UnaryOp(+, Number(5))" should " operator is + and number is 5" in {
      val unaryOp = UnaryOp("+", Number(5))

      assert("+" == unaryOp.operator)
      assert(Number(5) == unaryOp.arg)
    }

    "BinaryOp(+, Number(5), Number(10)) " should "operator is + and left is 5 and right is 10" in {
      val binaryOp = BinaryOp("+", Number(5), Number(10))

      assert("+" == binaryOp.operator)
      assert(Number(5) == binaryOp.left)
      assert(Number(10) == binaryOp.right)
    }

    "Number(5) after parse" should "equal 5" in {
      assert(Number(5) == Calc.parse("5"))
      assert(Number(5) == Calc.parse("5.0"))
      assert(Number(5) == Calc.parse("(5)"))
      assert(BinaryOp("+", Number(5), Number(5)) == Calc.parse("5 + 5"))
      assert(BinaryOp("+", Number(5), Number(5)) == Calc.parse("(5 + 5)"))
      assert(BinaryOp("+", BinaryOp("+", Number(5), Number(5)), Number(5)) == Calc.parse("(5 + 5) + 5"))
      assert(BinaryOp("+", BinaryOp("+", Number(5), Number(5)), BinaryOp("+", Number(5), Number(5)))
        == Calc.parse("(5 + 5) + (5 + 5)"))
    }

    "evaluate(1 + 1)" should "equal 2.0" in {
      assert(Calc.evaluate("1 + 1") == 2.0)
      assert(Calc.evaluate("1 + 2 * 3") == 7.0)
    }

    "evaluate(2 ^ 2)" should "equal 4.0" in {
      assert(BinaryOp("^", Number(2), Number(2)) == Calc.parse("2 ^ 2"))
      assert(BinaryOp("^", Number(2), Number(2)) == Calc.parse("(2 ^ 2)"))
      assert(BinaryOp("^", Number(2), BinaryOp("+", Number(1), Number(1))) == Calc.parse("2 ^ (1 + 1)"))
      assert(BinaryOp("^", Number(2), Number(2)) == Calc.parse("2 ^ (2)"))

      assert(Calc.evaluate("2 ^ 2") == 4.0)
    }
  }
}
