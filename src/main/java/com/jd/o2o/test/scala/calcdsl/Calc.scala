package com.jd.o2o.test.scala.calcdsl {

import scala.util.parsing.combinator.JavaTokenParsers

  private[calcdsl] abstract class Expr
  private[calcdsl] case class Number(value: Double) extends Expr
  private[calcdsl] case class UnaryOp(operator: String, arg: Expr) extends Expr
  private[calcdsl] case class BinaryOp(operator: String, left: Expr, right: Expr) extends Expr

  object Calc {

    def evaluate(text: String): Double = {
      evaluate(parse(text))
    }

    def evaluate(e: Expr): Double = {

      def exponentiate(base: Double, exponent: Double): Double = {
        if(0 == exponent) 1.0
        else base * exponentiate(base, exponent - 1)
      }

      simplify(e) match {
        case Number(x) => x
        case UnaryOp("-", x) => -(evaluate(x))
        case BinaryOp("+", x1, x2) => (evaluate(x1) + evaluate(x2))
        case BinaryOp("-", x1, x2) => (evaluate(x1) - evaluate(x2))
        case BinaryOp("*", x1, x2) => (evaluate(x1) * evaluate(x2))
        case BinaryOp("/", x1, x2) => (evaluate(x1) / evaluate(x2))
        case BinaryOp("^", x1, x2) => exponentiate(evaluate(x1), evaluate(x2))
      }
    }

    def simplify(e: Expr): Expr = {

      val simpSubs = e match {
        case BinaryOp(op, left, right) => BinaryOp(op, simplify(left), simplify(right))
        case UnaryOp(op, operand) => UnaryOp(op, simplify(operand))
        case _ => e
      }

      def simplifyTop(x: Expr): Expr = x match {
        case UnaryOp("-", UnaryOp("-", x)) => x
        case UnaryOp("+", x) => x
        case BinaryOp("*", x, Number(1)) => x
        case BinaryOp("*", Number(1), x) => x
        case BinaryOp("*", x, Number(0)) => Number(0)
        case BinaryOp("*", Number(0), x) => Number(0)
        case BinaryOp("/", x, Number(1)) => x
        case BinaryOp("/", x1, x2) if x1 == x2 => Number(1)
        case BinaryOp("+", x, Number(0)) => x
        case BinaryOp("+", Number(0), x) => x
        case BinaryOp("-", x1, x2) if x1 == x2 => Number(0)
        case _ => e
      }

      simplifyTop(simpSubs)
    }

    object OldAnyParser extends JavaTokenParsers {

      def expr: Parser[Any] = term ~ rep("+"~term | "-"~term)
      def term: Parser[Any] = factor ~ rep("*"~term | "/"~term)
      def factor: Parser[Any] = floatingPointNumber | "("~expr~")"

      def parse(text: String) = {
        parseAll(expr, text)
      }
    }

    object AnyParser extends JavaTokenParsers {

      def expr: Parser[Any] = (term~"+"~term) | (term~"-"~term) | term
      def term: Parser[Any] = (factor~"*"~factor) | (factor~"/"~factor) | factor
      def factor: Parser[Any] = "(" ~> expr <~ ")" | floatingPointNumber

      def parse(text: String) = {
        parseAll(expr, text)
      }
    }

    object ExprParser extends JavaTokenParsers {

      def expr: Parser[Expr] =
          (term~"+"~term) ^^ {case lhs~plus~rhs => BinaryOp("+", lhs, rhs)} |
          (term~"-"~term) ^^ {case lhs~minus~rhs => BinaryOp("-", lhs, rhs)} |
          term

      def term: Parser[Expr] =
          (factor~"*"~factor) ^^ {case lhs~times~rhs => BinaryOp("*", lhs, rhs)} |
          (factor~"/"~factor) ^^ {case lhs~div~rhs => BinaryOp("/", lhs, rhs)} |
          (factor~"^"~factor) ^^ {case lhs~exp~rhs => BinaryOp("^", lhs, rhs)} |
          factor

      def factor: Parser[Expr] =
          "(" ~> expr <~ ")" |
          floatingPointNumber ^^ {x => Number(x.toFloat)}

      def parse(text: String) = {
        parseAll(expr, text)
      }
    }

    def parse(text: String) = {
      val results = ExprParser.parse(text)
//      println("parsed " + text + " as " + results + " which is a type " + results.getClass)
      results.get
    }
  }
}
