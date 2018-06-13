package im.mange.flakeless

import im.mange.flakeless.innards.{FlightDataRecord, FlightDataRecordJson}
import org.joda.time.DateTime
import org.scalatest.refspec.RefSpec

class ReportJsonSpec extends RefSpec {
  def `jsonise FlightDataRecord`() = {
    val record = FlightDataRecord("suite", "test",
      Some(DateTime.parse("2018-06-13T13:25:56.219Z")),
      Some(DateTime.parse("2018-06-13T13:25:56.219Z")),
      Nil)

    val expected = """{
                     |  "suite":"suite",
                     |  "test":"test",
                     |  "started":"2018-06-13T13:25:56.219Z",
                     |  "finished":"2018-06-13T13:25:56.219Z",
                     |  "dataPoints":[]
                     |}""".stripMargin

    val actual = FlightDataRecordJson.serialise(record)

    assertResult(expected)(actual)
  }
}
