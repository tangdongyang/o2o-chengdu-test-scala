package com.jd.o2o.test.scala.json4s

/**
  * Created by tangdy2 on 2016/10/25.
  */
object MergingDiffing extends App {

  import org.json4s._
  import org.json4s.jackson.JsonMethods._

  val lotto_1 = parse("""{
      "lotto": {
         "lotto-id": 5,
         "winning-numbers": [2, 45, 34, 23, 7, 5, 3],
         "winners": [{
             "winner-id": 23,
             "numbers": [2, 45, 34, 23, 3, 5]
         }]
      }
      }""")

  val lotto_2 = parse("""{
      "lotto": {
         "winners": [{
             "winner-id": 54,
             "numbers": [52, 3, 12, 11, 18, 22]
         }]
      }
      }""")

  val mergedLotto = lotto_1 merge lotto_2
  println(pretty(render(mergedLotto)))

  val Diff(changed, added, deleted) = mergedLotto diff lotto_1
  println("changed: " + changed)
  println("added: " + added)
  println("deleted: " + deleted)
}
