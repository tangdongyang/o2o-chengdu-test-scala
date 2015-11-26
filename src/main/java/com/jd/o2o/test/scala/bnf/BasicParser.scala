package com.jd.o2o.test.scala.bnf

import scala.util.parsing.input.Reader

/**
 * Created by Administrator on 2015/11/26.
 */


class BasicParser {

  type Elem
  type Input = Reader[Elem]
  type Parser[T] = Input => ParseResult[T]

  sealed abstract class ParseResult[+T]
  case class Success[T](result: T, in: Input) extends ParseResult[T]
  case class Failure(msg: String, in: Input) extends ParseResult[Nothing]
}
