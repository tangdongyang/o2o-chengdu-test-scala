package com.jd.o2o.test.scalatest.systemdemo

import java.io.File
import java.util

import org.scalatest.matchers.{MatchResult, BeMatcher, BePropertyMatchResult, BePropertyMatcher}
import org.scalatest.{Matchers, FlatSpec}
import org.scalactic.StringNormalizations._
import org.scalatest._
import Inspectors._

/**
 * Created by Administrator on 2015/9/7.
 */
class UsingMatchers extends FlatSpec with Matchers with CustomBePropertyMatcher with CustomBeMatcher {

  // Checking equality with matchers
  val result = 3

  result should equal (3)
  result should === (3)
  result should be (3)
  result shouldEqual 3
  result shouldBe 3

  Array(1, 2) should equal (Array(1, 2))

  // Checking size and length
  val result2 = "HelloWorld"
  result2 should have length 10

  val list = List("Hello", "World")
  list should have size 2

  // Checking strings
  val str = "hello, my world"
  str should startWith ("hello")
  str should endWith ("world")
  str should include ("my")

  str should startWith regex "he.*o"
  str should endWith regex "wor.d"
  str should include regex "m."

  "-132.120759119" should fullyMatch regex """(-)?(\d+)(\.\d*)?"""

  "abbccxx" should startWith regex ("a(b*)(c*)" withGroups ("bb", "cc"))
  "xxxabbcc" should endWith regex ("a(b*)(c*)" withGroups ("bb", "cc"))
  "xxxabbccxxx" should include regex ("a(b*)(c*)" withGroups ("bb", "cc"))
  "abbcc" should fullyMatch regex ("a(b*)(c*)" withGroups ("bb", "cc"))

  // Greater and less than
  7 should be > 5
  5 should be >= 5
  7 should be < 9
  9 should be <= 9

  // Checking Boolean properties with be
  val tdy = new Tester()
  tdy should not be 'tester
  // tdy.setTester(true)
  tdy setTester true
  tdy should be a 'tester

  "A temp file" should "be a file, not a directory" in {

    val tempFile = File.createTempFile("delete", "me")

    try {
      tempFile should be a (file)
      tempFile should not be a (directory)
    } finally {
      tempFile.delete()
    }
  }

  // Using custom BeMatchers
  2 should be (even)
  4 shouldBe even
  1 should be (odd)
  3 should not be even

  // Checking object identity
  val tdy002 = new Tester
  val tdy003 = tdy002
  tdy003 should be theSameInstanceAs tdy002

  // Checking an object's class
  tdy002 shouldBe a [Tester]
  tdy002 should not be a [CustomBeMatcher]

  val list_str = List("tdy001", "tdy002", "tdy003")
  list_str shouldBe a [List[_]]

  // Checking numbers against a range
  7.1 should equal (6.9 +- 0.3)
  7.1 should === (6.9 +- 0.3)
  7.1 should be (6.9 +- 0.3)
  7.1 shouldBe 6.9 +- 0.3
  7.1 shouldEqual 6.9 +- 0.3

  // Checking for emptiness
  List.empty shouldBe empty
  None should be (empty)
  Some(1) should not be empty
  "" shouldBe empty
  new util.HashMap[Int, Int] shouldBe empty
  new { def isEmpty = true} shouldBe empty
  Array(1, 2, 3) should not be empty

  // Working with "containers"
  List(1, 2, 3) should contain (2)
  Map('a' -> 1, 'b' -> 2, 'c' -> 3) should contain ('b' -> 2)
  Set(1, 2, 3) should contain (2)
  Array(1, 2, 3) should contain (2)
  "123" should contain ('2')
  Some(2) should contain (2)

  (List("Hi", "Ho", "hi") should contain ("ho")) (after being lowerCased)

  List(1, 2, 3, 4, 5) should contain oneOf (5, 7, 9)
  Some(7) should contain oneOf (5, 7, 9)
  "howdy" should contain oneOf ('a', 'b', 'c', 'd')

  // List(1, 2, 3, 4, 5) should contain oneOf (4, 5, 6)

  (Array("Doe", "Ray", "Me") should contain oneOf ("X", "ray", "BEAM")) (after being upperCased)

  List(1, 2, 3, 4, 5) should contain noneOf (7, 8, 9)
  Some(2) should contain noneOf (3, 4, 5)
  "12345" should contain noneOf ('7', '8', '9')

  // Working with "aggregations"
  List(1, 2, 3) should contain atLeastOneOf (2, 3, 4)
  Array(1, 2, 3) should contain atLeastOneOf (2, 3, 4)
  "abc" should contain atLeastOneOf ('c', 'a', 't')

  (Vector(" A", "  B ") should contain atLeastOneOf (" a", "b ", " c ")) (after being lowerCased and trimmed)
  List(1, 2, 3, 4, 5) should contain atMostOneOf (5, 6, 7, 8)
  List(1, 2, 3, 4, 5) should contain allOf (2, 3, 5)
  List(1, 2, 3) should contain only (1, 2, 3)
  List(1, 2, 2, 3, 3, 3) should contain theSameElementsAs Vector(3, 2, 3, 1, 2, 3)
  List(1, 2, 2, 3, 3, 3) should contain theSameElementsInOrderAs Vector(1, 2, 2, 3, 3, 3)

  // Working with "sequences"
  List(1, 2, 2, 3, 3, 3) should contain inOrderOnly (1, 2, 3)
  List(3, 0, 1, 2, 2, 99, 3, 3, 3, 5) should contain inOrder (1, 2, 3)
  List(1, 2, 3) should contain theSameElementsInOrderAs collection.mutable.TreeSet(3, 2, 1)

  // Working with "sortables"
  List(1, 2, 3) shouldBe sorted

  // Working with iterators
  val iter = List(1, 2, 3).iterator
  iter.toStream should contain (2)

  // Inspector shorthands
  val yss = List(List(1, 2, 3), List(1, 2, 3), List(1, 2, 3))
  forAll (yss) { ys =>
    forAll (ys) { y =>
      y should be > 0
    }
  }

  val xs = List(1, 2, 3, 4, 5)
  forAll (xs) { x =>
    x should be > 0
  }

  all (xs) should be < 10
  atMost(2, xs) should be >= 4
  atLeast(3, xs) should be < 5
  between(2, 3, xs) should (be > 1 and be < 5)
  every (xs) should be > 0
  exactly (2, xs) should be <= 2
  exactly (1, xs) shouldEqual 2

  // Single-element collections
  // val set = Set(10)
  // set.loneElement should be <= 10

  // Java collections and maps
  // javaCollection should be ('empty)
  // javaMap should be ('empty)

  // javaList should have length 9

  // javaMap should have size 20
  // javaSet should have size 90

  // javaCollection should contain ("five")

  // javaMap should contain (Entry(2, 3))
  // javaMap should contain oneOf (Entry(2, 3), Entry(3, 4))

  // javaMap should contain key 1
  // javaMap should contain value "Howdy"

  // Strings and Arrays as collections
  atLeast (2, Array(1, 2, 3)) should be > 1
  atMost (2, "hello") shouldBe 'l'
  Array(1, 2, 3) shouldBe sorted
  "abcdefg" shouldBe sorted
  Array(1, 2, 3) should contain atMostOneOf (3, 4, 5)
  "abc" should contain atLeastOneOf ('c', 'a', 't')

  // be as an equality comparison
  // result should equal (null)  // the same as: result should be (null)

}

trait CustomBeMatcher {

  class OddMatcher extends BeMatcher[Int] {

    def apply(left: Int) = MatchResult(
      left % 2 == 1,
      left.toString + " was even",
      left.toString + " was odd"
    )
  }

  class EvenMatcher extends BeMatcher[Int] {

    def apply(left: Int) = MatchResult(
      left % 2 == 0,
      left.toString + " was odd",
      left.toString + " was even"
    )
  }

  val odd = new OddMatcher
  // val even = not (odd)
  val even = new EvenMatcher
}

trait CustomBePropertyMatcher {

  class FileBePropertyMatcher extends BePropertyMatcher[File] {
    def apply(left: File) = BePropertyMatchResult(left.isFile, "file")
  }

  class DirectoryBePropertyMatcher extends BePropertyMatcher[File] {
    def apply(left: File) = BePropertyMatchResult(left.isDirectory, "directory")
  }

  val file = new FileBePropertyMatcher
  val directory = new DirectoryBePropertyMatcher
}

class Tester {
  var tester: Boolean = false

  def isTester(): Boolean = {
    tester
  }

  def setTester(tester: Boolean): Unit = {
    this.tester = tester
  }
}
