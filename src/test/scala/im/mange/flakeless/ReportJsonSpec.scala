package im.mange.flakeless

import im.mange.flakeless.innards._
import org.joda.time.DateTime
import org.openqa.selenium.By
import org.scalatest.refspec.RefSpec

import scala.collection.immutable.HashMap

class ReportJsonSpec extends RefSpec {
  def `jsonise FlightDataRecord with no data points`() = {
    val record = FlightDataRecord("suite", "test",
      Some(DateTime.parse("2018-06-13T13:25:56.219Z")),
      Some(DateTime.parse("2018-06-13T13:25:58.219Z")),
      Nil)

    val expected = """{
                     |  "suite":"suite",
                     |  "test":"test",
                     |  "started":"2018-06-13T13:25:56.219Z",
                     |  "finished":"2018-06-13T13:25:58.219Z",
                     |  "dataPoints":[]
                     |}""".stripMargin

    val actual = FlightDataRecordJson.serialise(record)

    assertResult(expected)(actual)
  }

  def `jsonise FlightDataRecord with data point`() = {
    val record = FlightDataRecord("suite", "test",
      Some(DateTime.parse("2018-06-13T13:25:56.219Z")),
      Some(DateTime.parse("2018-06-13T13:25:58.219Z")),
      List(
        DataPoint(0, DateTime.parse("2018-06-13T13:25:57.219Z"), Some("description"), Some(
            ReportCommand("name", Some("in"), List(By.id("id")), HashMap("key" -> "value"), Some("expected"), Some(List("expectedMany")))),
          Context(List("failure"), Some(false)), None)))

    val expected = """{
                     |  "suite":"suite",
                     |  "test":"test",
                     |  "started":"2018-06-13T13:25:56.219Z",
                     |  "finished":"2018-06-13T13:25:58.219Z",
                     |  "dataPoints":[{
                     |    "flightNumber":0,
                     |    "when":"2018-06-13T13:25:57.219Z",
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
                     |      "success":"false"
                     |    }
                     |  }]
                     |}
                     |""".stripMargin

    val actual = FlightDataRecordJson.serialise(record)

    println(s"\n\n$actual\n\n")

    assertResult(expected)(actual)
  }
}
