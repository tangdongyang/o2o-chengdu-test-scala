package com.jd.o2o.test.scala.json4s

import org.json4s._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{write, read}

/**
  * Created by tangdy2 on 2016/11/11.
  */
object SerializingNonSupportedTypes extends App {

  implicit val formats = Serialization.formats(NoTypeHints) + new IntervalSerializer

  val ser = write(new Interval(1, 9))
  println(ser)

  println(read[Interval](ser).startTime)
}

class Interval(start: Long, end: Long) {
  val startTime = start
  val endTime = end
}

class IntervalSerializer extends CustomSerializer[Interval](format => (
  {
    case JObject(JField("start", JInt(s)) :: JField("end", JInt(e)) :: Nil) =>
    new Interval(s.longValue(), e.longValue())
  }, {
    case x: Interval =>
    JObject(JField("start", JInt(BigInt(x.startTime))) :: JField("end", JInt(BigInt(x.endTime))) :: Nil)
  }
  ))
