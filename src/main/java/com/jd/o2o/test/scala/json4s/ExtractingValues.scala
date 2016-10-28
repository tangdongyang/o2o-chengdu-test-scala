package com.jd.o2o.test.scala.json4s

import java.text.SimpleDateFormat

/**
  * Created by tangdy2 on 2016/10/27.
  */
object ExtractingValues extends App {

  import org.json4s._
  import org.json4s.jackson.JsonMethods._

//  implicit val formats = DefaultFormats
  implicit val formats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  }

  case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
  case class Address(street: String, city: String)
  case class Person(name: String, address: Address, children: List[Child])

  val json = parse("""
      {
        "name": "tdy",
        "address": {"street": "C12", "city": "ChengDu"},
        "children": [{"name": "tdy001", "age": 1, "birthdate": "1990-05-26 13:14:52"},
                     {"name": "tdy002", "age": 2}]
      }""")

  println(json.extract[Person])
  println(json \ "name")

  // By default the constructor parameter names must match json field names.
  // However, sometimes json field names contain characters which are not allowed characters in Scala identifiers.
  // There's two solutions for this:

  val json_2 = parse("""{"first-name": "Tang", "last-name": "Dongyang", "age": 26}""")

  // Use back ticks
  case class Person_2(`first-name`: String, `last-name`: String, age: Int)
  println(json_2.extract[Person_2])

  // Use transform function to postprocess AST
  case class Person_3(firstname: String, lastname: String, age: Int)
  val json_3 = json_2 transformField {
    case ("first-name", x) => ("firstname", x)
    case ("last-name", x) => ("lastname", x)
  }
  println(json_3.extract[Person_3])

  case class Bike(make: String, price: Int) {
    def this(price: Int) = this("tdy", price)
    def this(make: String) = this(make, 250)
  }
  println(parse(""" {"price": 120} """).extract[Bike])
  println(parse(""" {"make": "China"} """).extract[Bike])

  println((json \ "name").extract[String])
  println(((json \ "children")(0) \ "birthdate").extract[java.util.Date])

  // JSON object can be extracted to Map[String, _] too.
  // Each field becomes a key value pair in result Map.
  val json_4 = parse("""{
        "name": "tdy",
        "addresses": {
            "address1": {"street": "C12", "city": "CD"},
            "address2": {"street": "c11", "city": "cd"}
        }
      }""")

  case class PersonWithAddresses(name: String, addresses: Map[String, Address])
  println(json_4.extract[PersonWithAddresses])
}
