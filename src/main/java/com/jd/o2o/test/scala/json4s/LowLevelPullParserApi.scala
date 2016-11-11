package com.jd.o2o.test.scala.json4s

import org.json4s._
import org.json4s.jackson.JsonMethods._

/**
  * Created by tangdy2 on 2016/11/11.
  */
object LowLevelPullParserApi extends App {

  val json =
    """
      {
        "firstName": "Tang",
        "lastName": "Dongyang",
        "address" {
          "streetAddress": "C12",
          "city": "CD",
          "state": "NY",
          "postalCode": 199005
        },
        "phoneNumbers": [
          {"type": "home", "number": "111-11111"},
          {"type": "fax", "number": "222-222222"}
        ]
      }
    """

//  val parser = (p: JsonParser) => {
//    def parse: BigInt = p.nextToken match {
//      case FieldStart("postalCode") => p.nextToken match {
//        case IntVal(code) => code
//        case _ => p.fail("expected int")
//      }
//      case End => p.fail("no field named 'postalCode'")
//      case _ => parse
//    }
//
//    parse
//  }
//
//  val postalCode = parse(json, parser)
//  println(postalCode)
}
