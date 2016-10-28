package com.jd.o2o.test.scala.json4s

/**
  * Created by tangdy2 on 2016/10/26.
  *
  * JSON values can be extracted using for-comprehensions
  */
object QueryingJSON extends App {

  import org.json4s._
  import org.json4s.jackson.JsonMethods._

  val json = parse("""{
      "name": "tdy",
      "children": [
          {"name": "tdy001", "age": 1},
          {"name": "tdy002", "age": 2}
      ]
      }""")

  println(json)

  val ages = for {
    JObject(child) <- json
    JField("age", JInt(age)) <- child
  } yield age

  println(ages)

  val nameAges = for {
    JObject(child) <- json
    JField("name", JString(name)) <- child
    JField("age", JInt(age)) <- child
    if age > 1
  } yield (name, age)

  println(nameAges)
}
