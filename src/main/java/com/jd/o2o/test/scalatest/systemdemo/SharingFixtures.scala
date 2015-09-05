package com.jd.o2o.test.scalatest.systemdemo

import java.io.{FileWriter, File}
import java.util.concurrent.ConcurrentHashMap

import com.jd.o2o.test.scalatest.systemdemo.DbServer._
import org.scalatest._

import scala.collection.mutable.ListBuffer

import java.util.UUID.randomUUID

/**
 * Created by Administrator on 2015/9/3.
 */

// Calling get-fixture methods
class GetFixtureMethods extends FlatSpec {

  def fixture = new {
    val builder = new StringBuilder("ScalaTest is ")
    val buffer = new ListBuffer[String]
  }

  "Testing" should "be easy" in {
    val f = fixture

    import f._

    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)

    f.buffer += "sweet"
  }

  it should "be fun" in {
    val f = fixture

    f.builder.append("fun!")
    assert(f.builder.toString === "ScalaTest is fun!")
    assert(f.buffer.isEmpty)
  }
}

// Instantiating fixture-context objects
class GetFixtureContext extends FlatSpec {

  trait Builder {
    val builder = new StringBuilder("ScalaTest is ")
  }

  trait Buffer {
    val buffer = ListBuffer("ScalaTest", "is")
  }

  "Testing" should "be productive" in new Builder {
    builder.append("productive!")
    assert(builder.toString === "ScalaTest is productive!")
  }

  "Test code" should "be readable" in new Buffer {
    buffer += ("readable!")
    assert(buffer === List("ScalaTest", "is", "readable!"))
  }

  it should "be clear and concise" in new Builder with Buffer {
    builder.append("clear!")
    buffer += ("concise!")

    assert(builder.toString === "ScalaTest is clear!")
    assert(buffer === List("ScalaTest", "is", "concise!"))
  }
}

// Overriding withFixture(NoArgTest)
class WithFixtureNoArgTest extends FlatSpec {

  override def withFixture(test: NoArgTest) = {

    super.withFixture(test) match {
      case failed: Failed =>
        val currDir = new File(".")
        val fileNames = currDir.list()
        info("Dir snapshot: " + fileNames.mkString(", "))
        failed
      case other => other
    }
  }

  "This test" should "succeed" in {
    assert(1 + 1 === 2)
  }

  it should "fail" in {
    assert(1 + 1 === 3)
  }
}

// Calling loan-fixture methods
object DbServer {
  type Db = StringBuffer
  private val databases = new ConcurrentHashMap[String, Db]()

  def createDb(name: String): Db = {
    val db = new StringBuffer
    databases.put(name, db)
    db
  }

  def removeDb(name: String): Unit = {
    databases.remove(name)
  }
}

class LoanFixtureMethods extends FlatSpec {

  def withDatabase(testCode: Db => Any): Unit = {
    val dbName = randomUUID.toString
    val db = createDb(dbName) // create the fixture

    try {
      db.append("ScalaTest is ") // perform setup
      testCode(db) // "loan" the fixture to the test
    } finally {
      removeDb(dbName) // clean up the fixture
    }
  }

  def withFile(testCode: (File, FileWriter) => Any): Unit = {
    val file = File.createTempFile("hello", "world") // create the fixture
    val writer = new FileWriter(file)
    try {
      writer.write("ScalaTest is ") // set up the fixture
      testCode(file, writer) // "loan" the fixture to the test
    } finally {
      writer.close() // clean up the fixture
    }
  }

  "Testing " should "be productive" in withFile { (file, writer) =>
    writer.write("productive!")
    writer.flush()
    assert(file.length === 24)
  }

  "Test code" should "be readable" in withDatabase { db =>
    db.append("readable!")
    assert(db.toString === "ScalaTest is readable!")
  }

  it should "be clear and concise" in withDatabase { db =>
    withFile {(file, writer) =>
      db.append("clear!")
      writer.write("concise!")
      writer.flush()

      assert(db.toString === "ScalaTest is clear!")
      assert(file.length === 21)
    }
  }
}

// Overriding withFixture(OneArgTest)
class WithFixtureOneArgTest extends fixture.FlatSpec {

  case class FixtureParam(file: File, writer: FileWriter)

  def withFixture(test: OneArgTest) = {
    val file = File.createTempFile("hello", "world") // create the fixture
    val writer = new FileWriter(file)
    val theFixture = FixtureParam(file, writer)

    try {
      writer.write("ScalaTest is ") // set up the fixture
      withFixture(test.toNoArgTest(theFixture)) // "loan" the fixture to the test
    } finally {
      writer.close() // clean up the fixture
    }
  }

  "Testing" should "be easy" in { f =>
    f.writer.write("easy!")
    f.writer.flush()

    assert(f.file.length === 18)
  }

  it should "be fun" in { f =>
    f.writer.write("fun!")
    f.writer.flush

    assert(f.file.length === 17)
  }
}

// Mixing in BeforeAndAfter
class MixinBeforeAndAfter extends FlatSpec with BeforeAndAfter {

  val builder = new StringBuilder
  val buffer = new ListBuffer[String]

  before {
    println("before")
    builder.append("ScalaTest is ")
  }

  after {
    println("after")
    builder.clear()
    buffer.clear()
  }

  "Testing" should "be easy" in {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)

    buffer += "sweet"
  }

  it should "be fun" in {
    builder.append("fun!")
    assert(builder.toString === "ScalaTest is fun!")
    assert(buffer.isEmpty)
  }
}

// Composing fixtures by stacking traits
trait Builder extends SuiteMixin { this: Suite =>

  val builder = new StringBuilder

  abstract override def withFixture(test: NoArgTest) = {
    builder.append("ScalaTest is ")
    try {
      super.withFixture(test)
    } finally {
      builder.clear
    }
  }
}

trait Buffer extends SuiteMixin { this: Suite =>

  val buffer = new ListBuffer[String]

  abstract override def withFixture(test: NoArgTest) = {
    try {
      super.withFixture(test)
    } finally {
      buffer.clear
    }
  }
}

class ComposeFixtureByStackTrait extends FlatSpec with Builder with Buffer {

  "Testing" should "be easy" in {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)
    buffer += "sweet"
  }

  it should "be fun" in {
    builder.append("fun!")
    assert(builder.toString === "ScalaTest is fun!")
    assert(buffer.isEmpty)
  }
}

// BeforeAndAfterEach
// BeforeAndAfterEach has a beforeEach method that will be run before each test (like JUnit's setUp),
// and an afterEach method that will be run after (like JUnit's tearDown).
trait Builder_2 extends BeforeAndAfterEach { this: Suite =>

  val builder = new StringBuilder

  override def beforeEach(): Unit = {
    println("beforeEach")
    builder.append("ScalaTest is ")
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    try {
      println("afterEach")
      super.afterEach()
    } finally {
      builder.clear()
    }
  }
}

trait Buffer_2 extends BeforeAndAfterEach { this: Suite =>

  val buffer = new ListBuffer[String]

  override def afterEach(): Unit = {
    try {
      println("afterEach of buffer")
      super.afterEach()
    } finally {
      buffer.clear()
    }
  }
}

class MixinBeforeAndAfterEach extends FlatSpec with Builder_2 with Buffer_2 {

  "Testing" should "be easy" in {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)
    buffer += "sweet"
  }

  it should "be fun" in {
    builder.append("fun!")
    assert(builder.toString === "ScalaTest is fun!")
    assert(buffer.isEmpty)
  }
}

// BeforeAndAfterAll
// BeforeAndAfterAll has a beforeAll method that will be run before all tests,
// and an afterAll method that will be run after all tests.
trait Builder_3 extends BeforeAndAfterAll { this: Suite =>

  val builder = new StringBuilder

  override def beforeAll(): Unit = {
    println("beforeAll of builder")
    builder.append("ScalaTest is ")
    super.beforeAll()
  }

  override def afterAll(): Unit = {
    try {
      println("afterAll of builder")
      super.afterAll()
    } finally {
      builder.clear()
    }
  }
}

trait Buffer_3 extends BeforeAndAfterAll { this: Suite =>

  val buffer = new ListBuffer[String]

  override def afterAll(): Unit = {
    try {
      println("afterAll of buffer")
      super.afterAll()
    } finally {
      buffer.clear()
    }
  }
}

class MixinBeforeAndAfterAll extends FlatSpec with Builder_3 with Buffer_3 {

  "Testing" should "be easy" in {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)
    buffer += "sweet"
  }

  it should "be fun" in {
    builder.append(" fun!")
    assert(builder.toString === "ScalaTest is easy! fun!")
    assert(buffer === List("sweet"))
  }
}