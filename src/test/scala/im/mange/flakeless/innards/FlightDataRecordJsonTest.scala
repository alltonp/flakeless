package im.mange.flakeless.innards

import org.joda.time.DateTime
import org.openqa.selenium.By
import org.specs2.mutable.Specification

class FlightDataRecordJsonTest extends Specification {

  "Encode Json " >> {
    val start = DateTime.parse("2018-06-30T01:20+00:00")
    val end = DateTime.parse("2018-06-30T02:20+00:00")

    val command = ReportCommand("command", Some("thing"), List(By.id("id")), Map("A" -> "B"), Some("expected"), Some(List("many?")))
    val context = Context(List("context"), Some(true))
    val flightData = FlightDataRecord("suite", "test", Some(start), Some(end), List(
      DataPoint(1, start, Some("description"), Some(command), context, Some(List("log")))
    ))
    
    FlightDataRecordJson.serialise(flightData) must_== """{
                                                         |  "suite":"suite",
                                                         |  "test":"test",
                                                         |  "started":"2018-06-30T01:20:00.000Z",
                                                         |  "finished":"2018-06-30T02:20:00.000Z",
                                                         |  "dataPoints":[{
                                                         |    "flightNumber":1,
                                                         |    "when":"2018-06-30T01:20:00.000Z",
                                                         |    "description":"description",
                                                         |    "command":{
                                                         |      "name":"command",
                                                         |      "in":"thing",
                                                         |      "bys":[{
                                                         |        "id":"id"
                                                         |      }],
                                                         |      "args":{
                                                         |        "A":"B"
                                                         |      },
                                                         |      "expected":"expected",
                                                         |      "expectedMany":["many?"]
                                                         |    },
                                                         |    "context":{
                                                         |      "failures":["context"],
                                                         |      "success":"true"
                                                         |    },
                                                         |    "log":["log"]
                                                         |  }]
                                                         |}""".stripMargin
  }
}
