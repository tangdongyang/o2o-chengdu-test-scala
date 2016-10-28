package com.jd.o2o.test.scala.json4s

/**
  * Created by tangdy2 on 2016/10/25.
  *
  * You can generate json in 2 modes either in DoubleMode or in BigDecimalMode;
  * the former will map all decimal values into a JDouble the latter into a JDecimal.
  */

import org.json4s._
import org.json4s.jackson.JsonMethods._

//import org.json4s.JsonDSL._
import org.json4s.JsonDSL.WithDouble._
//import org.json4s.JsonDSL.WithBigDecimal._

object ProducingJSON extends App {

  // Extending the dsl To extend the dsl with your own classes
  // you must have an implicit conversion in scope of signature
//  type DslConversion = T => JValue

  // Primitive types map to JSON primitives
  // Any seq produces JSON array
  val json_1 = List(1, 2, 3)
  val p_1 = compact(render(json_1))
  println(p_1)

  // Tuple2[String, A] produces field
  val json_2 = ("name" -> "tdy")
  val p_2 = compact(render(json_2))
  println(p_2)

  // ~ operator produces object by combining fields
  val json_3 = ("name" -> "tdy") ~ ("age" -> 26)
  val p_3 = compact(render(json_3))
  println(p_3)

  // Any value can be optional. Field and value is completely removed when it doesn't have a value
  val json_4 = ("name" -> "tdy") ~ ("age" -> Some(26))
  val p_4 = compact(render(json_4))
  println(p_4)

  val json_5 = ("name" -> "tdy") ~ ("age" -> (None: Option[Int]))
  val p_5 = compact(render(json_5))
  println(p_5)
}
