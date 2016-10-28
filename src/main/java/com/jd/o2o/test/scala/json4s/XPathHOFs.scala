package com.jd.o2o.test.scala.json4s

import org.json4s.JsonAST.{JField, JInt, JString, JValue}

/**
  * Created by tangdy2 on 2016/10/26.
  *
  * Json AST can be queried using XPath like functions.
  * Following REPL session shows the usage of '\', '\\', 'find', 'filter', 'transform', 'remove' and 'values' functions
  */
object XPathHOFs extends App {

  import org.json4s.JsonDSL._
  import org.json4s.jackson.JsonMethods._

  val json: JValue = ("person" ->
      ("name" -> "tdy") ~ ("age" -> 35) ~
        ("spouse" ->
          ("person" ->
            ("name" -> "tdy001") ~ ("age" -> 26))))

  println(pretty(render(json)))

  val spouse = json \\ "spouse"
  println(compact(render(spouse)))

  println(compact(render(json \\ "name")))

  println(compact(render(json \ "person")))
  println(compact(render(json \ "person" \ "name")))

  println(compact(render((json removeField { _ == JField("name", JString("tdy"))}) \\ "name")))

  val findValue = json findField {
    case JField("name", _) => true
    case _ => false
  }
  println(findValue)

  val filterValue = json filterField {
    case JField("name", _) => true
    case _ => false
  }
  println(filterValue)

  val transformValue = json transformField {
    case JField("name", JString(s)) => ("NAME", JString(s.toUpperCase))
  }
  println(transformValue)

  println(json.values)

  // Indexed path expressions work too and values can be unboxed using type expressions
  val json_2 = parse("""
      {"name": "tdy",
        "children": [
          {"name": "tdy001", "age": 1},
          {"name": "tdy002", "age": 2}
        ]
      }
    """)

  println(json_2 \ "name")
  println((json_2 \ "children")(1))
  println((json_2 \ "children")(0) \ "name")

  println(json_2 \\ classOf[JInt])
  println(json_2 \ "children" \\ classOf[JString])
}
