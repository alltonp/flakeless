package im.mange.flakeless.innards

import java.time.Duration

private [flakeless] object FlightInvestigator {
  private val investigationByFlightNumber: scala.collection.concurrent.TrieMap[Int, Investigation] =
    new scala.collection.concurrent.TrieMap()

  def investigate(flightNumber: Int, flightDataRecorder: FlightDataRecorder): Unit = {
    update(flightNumber, createInvestigation(flightNumber, flightDataRecorder))
  }

  def jsonData = InvestigationJson.serialise(investigationByFlightNumber.values.toList)

  private def createInvestigation(flightNumber: Int, flightDataRecorder: FlightDataRecorder) = {
    val flightData: FlightDataRecord = flightDataRecorder.data(flightNumber)

    //TODO: this doesnt work, because we we might have an inflight announcement at the end
    //... so we could maybe check for any Context false, could yield false positives
    //... or maybe filter out command vs announcements ... and only count the commands in data points
    //... furthermore split commands and assertions
    val success = flightData.dataPoints.reverse.headOption.map(_.context.success.getOrElse(false)).getOrElse(false)
    val started = flightData.started
    val firstInteraction = flightData.dataPoints.headOption.map(_.when)
    val finished = flightData.dataPoints.reverse.headOption.map(_.when)

    val grossDuration = (started, finished) match {
      case (Some(start), Some(finish)) => Some(Duration.between(start, finish).toMillis)
      case _ => None
    }

    val netDuration = (firstInteraction, finished) match {
      case (Some(start), Some(finish)) => Some(Duration.between(start, finish).toMillis)
      case _ => None
    }

    Investigation(flightNumber, flightData.suite, flightData.test, success, started, finished, firstInteraction, grossDuration, netDuration, flightData.dataPoints.size)
  }

  private def update(flightNumber: Int, investigation: Investigation): Unit = {
    investigationByFlightNumber.update(flightNumber, investigation)
  }
}
