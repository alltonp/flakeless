package im.mange.flakeless.innards

import java.time.LocalDateTime

//TODO: pull out the json bit to another thing
//TODO: might be able to drop the whole flight number thing from the json soon

case class FlightDataRecord(suite: String, test: String, started: Option[LocalDateTime], finished: Option[LocalDateTime], dataPoints: Seq[DataPoint])

private [flakeless] case class FlightDataRecorder() {
  private var dataByFlightNumber: scala.collection.concurrent.TrieMap[Int, FlightDataRecord] =
    new scala.collection.concurrent.TrieMap()

  def start(flightNumber: Int, suite: String, name: String) = {
    dataByFlightNumber = new scala.collection.concurrent.TrieMap()
    val record = FlightDataRecord(suite, name, Some(LocalDateTime.now()), None, Seq.empty[DataPoint])
    dataByFlightNumber.update(flightNumber, record)
  }

  def stop(flightNumber: Int)= {
    val record = data(flightNumber).copy(finished = Some(LocalDateTime.now))
    dataByFlightNumber.update(flightNumber, record)
    FlightInvestigator.investigate(flightNumber, this)
  }

  def record(flightNumber: Int, description: String, log: Option[List[String]], isError: Boolean) {
    update(flightNumber, DataPoint(flightNumber, LocalDateTime.now, Some(description), None, Context(Nil, Some(!isError)), log))
  }

  def record(flightNumber: Int, command: Command, context: Context) {
    update(flightNumber, DataPoint(flightNumber, LocalDateTime.now, None, Some(command.report), context, None))
  }

  def jsonData(flight: Int) = FlightDataRecordJson.serialise(data(flight))

  def data(flightNumber: Int): FlightDataRecord = dataByFlightNumber.get(flightNumber).getOrElse(throw new RuntimeException(s"No Record for flightnumber: $flightNumber"))

  private def update(flightNumber: Int, dataPoint: DataPoint): Unit = {
    val current: FlightDataRecord = data(flightNumber)
    dataByFlightNumber.update(flightNumber, current.copy(dataPoints = current.dataPoints :+ dataPoint))
  }
}