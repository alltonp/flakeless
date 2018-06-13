package im.mange.flakeless.innards

import org.json4s.native.JsonMethods._
import org.json4s.native.JsonParser
import org.json4s.native.Serialization._

private [flakeless] object FlightDataRecordJson {
  def serialise(r: FlightDataRecord) = {
    implicit val formats = Serialisers.all
    pretty(render(JsonParser.parse(write(r))))
  }
}
