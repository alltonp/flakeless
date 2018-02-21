package im.mange.flakeless.innards

import im.mange.little.counter.AtomicIntCounter

//TODO: in Config, have option to forget previous flight data when calling newFlight
//TODO: store summary on reset for later ...
private [flakeless] object FlightNumber {
  private val currentFlightNumberCounter = AtomicIntCounter()

  def next: Int = currentFlightNumberCounter.next
}
