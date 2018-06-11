package im.mange.flakeless.innards

import org.joda.time.DateTime
import org.specs2.mutable.Specification

class InvestigationJsonTest extends Specification {

  "Encode Json (empty object)" >> {
    InvestigationJson.serialise(List()) must_== "[]"
  }

  "Encode Json " >> {
    val start = DateTime.parse("2018-06-30T01:20+00:00")
    val end = DateTime.parse("2018-06-30T02:20+00:00")

    InvestigationJson.serialise(List(
      Investigation(1, "suite", "test", false, Some(start), Some(end), None, Some(1000), Some(2000), 2)
    )) must_== """|[{
                  |  "flightNumber":1,
                  |  "suite":"suite",
                  |  "test":"test",
                  |  "success":"false",
                  |  "started":"2018-06-30T01:20:00.000Z",
                  |  "finished":"2018-06-30T02:20:00.000Z",
                  |  "grossDurationMillis":1000,
                  |  "netDurationMillis":2000,
                  |  "dataPointCount":2
                  |}]""".stripMargin
  }
}
