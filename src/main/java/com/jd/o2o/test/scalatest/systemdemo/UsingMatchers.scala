package com.jd.o2o.test.scalatest.systemdemo

import java.io.File
import java.util

import org.scalatest.matchers._
import org.scalatest.{Matchers, FlatSpec}
import org.scalactic.StringNormalizations._
import org.scalatest._
import Inspectors._
import CustomEndWithExtensionMatchers._
import MatcherProducers._

/**
 * Created by Administrator on 2015/9/7.
 */
class UsingMatchers extends FlatSpec
                    with Matchers with CustomBePropertyMatcher with CustomBeMatcher
                    with CustomHavePropertyMatcher with Inside {

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
  7.0 should be (7.0)
  false should be (false)
  true should be (true)
  Nil should be (Nil)
  None should be (None)
  Some(1) should be (Some(1))

  Array(1, 2) should be (Array(1, 2))

  // Being negative
  List(1) should not be (null)
  14 should not be <= (10)
  List(1) should not be (List(2))
  "HaHa" should not startWith ("Hello")

  // Checking that a snippet of code does not compile
  "val a: String = 1" shouldNot compile
  "val a: String = l" shouldNot typeCheck
  "val a: Int = 1" should compile

  // Logical expressions with and and or
  // The other difference with Boolean operators is that although && has a higher precedence than ||,
  // and and or have the same precedence.
  // Thus although the Boolean expression (a || b && c) will evaluate the && expression before the || expression, like (a || (b && c))
  Map("one" -> 1, "two" -> 2) should (contain key ("two") and not contain value (7))
  4 should (be > (0) and be <= (10))
  None should (equal (Some(List(1, 2, 3))) or be (None))
  "fum" should (
    equal ("fee") or
    equal ("fie") or
    equal ("fum")
    )
  "yellow" should (equal ("yellow") or equal {println("Hello, World!"); "yellow"})

  // Working with Options
  None shouldEqual None
  None shouldBe None
  None should === (None)
  None shouldBe empty

  Some("hi") shouldEqual Some("hi")
  Some("hi") shouldBe Some("hi")
  Some("hi") should === (Some("hi"))

  Some("hi") shouldBe defined

  Some(2) should contain (2)
  Some(2) should contain oneOf (2, 3, 4)
  Some(2) should contain noneOf (5, 6, 7)

  // Checking arbitrary properties with have
  tdy002 should have (
    'tester (false),
    'age (25)
  )

  val book = new Book("ScalaTest", "tdy002", 220)
  book should have (
    title ("ScalaTest"),
    author ("tdy002")
  )

  // Using length and size with HavePropertyMatchers
  book should have (
    title ("ScalaTest"),
    author ("tdy002"),
    length (220)
  )

  // Checking that an expression matches a pattern
  val name = new Name("Jane", "Q", "Scala")
  inside(name) { case Name(first, _, _) =>
    first should startWith ("J")
  }
  name should matchPattern { case Name("Jane", _, _) => }

  val record = Record(
    Name("tdy001", "tdy002", "tdy003"),
    Address("street", "city", "state", "zip"),
    25
  )
  inside(record) { case Record(name, address, age) =>
    inside(name) { case Name(first, middle, last) =>
      first should be ("tdy001")
      middle should be ("tdy002")
      last should be ("tdy003")
    }

    inside(address) { case Address(street, city, state, zip) =>
      street should startWith ("str")
      city should endWith ("ty")
      state should equal ("state")
      zip should be ("zip")
    }

    age should be < 99
  }

  // Using custom matchers
  val file_txt = File.createTempFile("hello", "txt")
  file_txt.getName should endWith ("txt")

  file_txt should endWithExtension ("txt")
  // file_txt should not endWithExtension "ppt"
  // file_txt should (exist and endWithExtension ("txt"))

  // Creating dynamic matchers

  // Creating matchers using logical operators
  val beWithinTolerance = be >= 0 and be <= 10
  7 should beWithinTolerance

  val beOdd_1 = new Matcher[Int] {

    def apply(left: Int) = MatchResult(
      left % 2 == 1,
      left + " was not odd",
      left + " was odd"
    )
  }

  val beOdd = Matcher { (left: Int) =>
    MatchResult(
      left % 2 == 1,
      left + " was not odd",
      left + " was odd"
    )
  }

  3 should beOdd
  4 should not (beOdd)

  // Composing matchers
  def endWithExtension(ext: String) = endWith(ext) compose { (file: File) => file.getPath}
  new File("output.txt") should endWithExtension("txt")

  val f = be > (_: Int)
  val g = (_: String).toInt

  val beAsIntsGreaterThan = (f compose g) andThen (_ compose g)
  "8" should beAsIntsGreaterThan ("7")

  val beAsIntsGreaterThan2 = f composeTwice g // means: (f compose g) andThen (_ compose g)
  "8" should beAsIntsGreaterThan2 ("7")

  val beAsIntsGreaterThan3 = f composeTwice g mapResult { mr =>
    mr.copy(
      failureMessageArgs = mr.failureMessageArgs.map(LazyArg(_) {"\"" + _.toString + "\".toInt"}),
      negatedFailureMessageArgs = mr.negatedFailureMessageArgs.map(LazyArg(_) {"\"" + _.toString + "\".toInt"}),
      midSentenceFailureMessageArgs = mr.midSentenceFailureMessage.map(LazyArg(_) {"\"" + _.toString + "\".toInt"}),
      midSentenceNegatedFailureMessageArgs = mr.midSentenceNegatedFailureMessage.map(LazyArg(_) {"\"" + _.toString + "\".toInt"})
    )}

  "8" should beAsIntsGreaterThan3 ("7")

  // Checking for expected exceptions
  an [IndexOutOfBoundsException] should be thrownBy "HelloWorld".charAt(-1)

  val thrown = the [IndexOutOfBoundsException] thrownBy "HelloWorld".charAt(-1)
  thrown.getMessage should equal ("String index out of range: -1")

  the [ArithmeticException] thrownBy 1 / 0 should have message "/ by zero"
  the [IndexOutOfBoundsException] thrownBy {
    "HelloWorld".charAt(-1)
  } should have message "String index out of range: -1"

  noException should be thrownBy 0 / 1

  // Those pesky parens
  // Although you don't always need them, you can choose to always put parentheses around right-hand values, such as the 7 in num should equal (7):
  // Except for length and size, you must always put parentheses around the list of one or more property values following a have
  // You must always put parentheses around and and or expressions, as in
}

trait CustomEndWithExtensionMatchers {

  class FileEndsWithExtensionMatcher(expectedExtension: String) extends Matcher[File] {

    def apply(left: File) = {
      val name = left.getName
      MatchResult(
        name.endsWith(expectedExtension),
        s"""File $name did not end with extension "$expectedExtension"""",
        s"""File $name ended with extension "$expectedExtension""""
      )
    }
  }

  def endWithExtension(expectedExtension: String) = new FileEndsWithExtensionMatcher(expectedExtension)
}

object CustomEndWithExtensionMatchers extends CustomEndWithExtensionMatchers

case class Name(val first: String, val middle: String, val last: String)
case class Address(val street: String, val city: String, val state: String, val zip: String)
case class Record(val name: Name, val address: Address, val age: Int)

case class Book(val title: String, val author: String, val length: Int)

trait CustomHavePropertyMatcher {

  def title(expectedValue: String) = new HavePropertyMatcher[Book, String] {
    def apply(book: Book) = HavePropertyMatchResult(
      book.title == expectedValue,
      "title",
      expectedValue,
      book.title
    )
  }

  def author(expectedValue: String) = new HavePropertyMatcher[Book, String] {
    def apply(book: Book) = HavePropertyMatchResult(
      book.author == expectedValue,
      "author",
      expectedValue,
      book.author
    )
  }

  def length(expectedValue: Int) = new HavePropertyMatcher[Book, Int] {
    def apply(book: Book) = HavePropertyMatchResult(
      book.length == expectedValue,
      "length",
      expectedValue,
      book.length
    )
  }
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
  val age: Int = 25

  def isTester(): Boolean = {
    tester
  }

  def setTester(tester: Boolean): Unit = {
    this.tester = tester
  }
}
