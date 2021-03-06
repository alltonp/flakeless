package im.mange.flakeless

import java.time.LocalDateTime

import im.mange.flakeless.innards._
import org.openqa.selenium.By
import org.scalatest.refspec.RefSpec

import scala.collection.immutable.HashMap

class ReportJsonSpec extends RefSpec {
  def `jsonise FlightDataRecord with no data points`() = {
    val record = FlightDataRecord("suite", "test",
      Some(LocalDateTime.parse("2018-06-13T13:25:56.219")),
      Some(LocalDateTime.parse("2018-06-13T13:25:58.219")),
      Nil)

    val expected = """{
                     |  "suite":"suite",
                     |  "test":"test",
                     |  "started":"2018-06-13T13:25:56.219",
                     |  "finished":"2018-06-13T13:25:58.219",
                     |  "dataPoints":[]
                     |}""".stripMargin

    val actual = FlightDataRecordJson.serialise(record)

    assertResult(expected)(actual)
  }

  def `jsonise FlightDataRecord with data point`() = {
    val record = FlightDataRecord("suite", "test",
      Some(LocalDateTime.parse("2018-06-13T13:25:56.219")),
      Some(LocalDateTime.parse("2018-06-13T13:25:58.219")),
      List(
        DataPoint(0, LocalDateTime.parse("2018-06-13T13:25:57.219"), Some("description"), Some(
            ReportCommand("name", Some("in"), List(By.id("id")), HashMap("key" -> "value"), Some("expected"), Some(List("expectedMany")))),
          Context(List("failure"), Some(false)), None)))

    val expected = """{
                     |  "suite":"suite",
                     |  "test":"test",
                     |  "started":"2018-06-13T13:25:56.219",
                     |  "finished":"2018-06-13T13:25:58.219",
                     |  "dataPoints":[{
                     |    "flightNumber":0,
                     |    "when":"2018-06-13T13:25:57.219",
                     |    "description":"description",
                     |    "command":{
                     |      "name":"name",
                     |      "in":"in",
                     |      "bys":[{
                     |        "id":"id"
                     |      }],
                     |      "args":{
                     |        "key":"value"
                     |      },
                     |      "expected":"expected",
                     |      "expectedMany":["expectedMany"]
                     |    },
                     |    "context":{
                     |      "failures":["failure"],
                     |      "success":false
                     |    }
                     |  }]
                     |}""".stripMargin

    val actual = FlightDataRecordJson.serialise(record)

//    println(s"\n\n$actual\n\n")

    assertResult(expected)(actual)
  }

  def `jsonise Investigation`() = {
    val investigation = Investigation(0, "suite", "test", true,
      Some(LocalDateTime.parse("2018-06-13T13:25:56.219")),
      Some(LocalDateTime.parse("2018-06-13T13:25:58.219")),
      Some(LocalDateTime.parse("2018-06-13T13:25:57.219")),
      Some(4000),Some(2000),1)

    val expected = """[{
                     |  "flightNumber":0,
                     |  "suite":"suite",
                     |  "test":"test",
                     |  "success":true,
                     |  "started":"2018-06-13T13:25:56.219",
                     |  "finished":"2018-06-13T13:25:58.219",
                     |  "firstInteraction":"2018-06-13T13:25:57.219",
                     |  "grossDurationMillis":4000,
                     |  "netDurationMillis":2000,
                     |  "dataPointCount":1
                     |}]""".stripMargin

    val actual = InvestigationJson.serialise(List(investigation))

//    println(s"\n\n$actual\n\n")

    assertResult(expected)(actual)
  }

}
