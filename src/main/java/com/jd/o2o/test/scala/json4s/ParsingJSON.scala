package com.jd.o2o.test.scala.json4s

/**
  * Created by tangdy2 on 2016/10/25.
  *
  * Any valid json can be parsed into internal AST format.
  */

import org.json4s._
import org.json4s.jackson.JsonMethods._

object ParsingJSON extends App {

  val value1 = parse(""" {"numbers": [1, 2, 3, 4]} """)
  println(value1)

  val value2 = parse(""" {"name": "tdy", "price": 35.35} """, useBigDecimalForDouble = true)
  println(value2)
}
