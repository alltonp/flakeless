package im.mange.flakeless.innards

private [flakeless] object FlightDataRecordJson {
  def serialise(r: FlightDataRecord): String = Jsoniser.jsonise(r)
}
