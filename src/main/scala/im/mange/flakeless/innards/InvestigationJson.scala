package im.mange.flakeless.innards

private [flakeless] object InvestigationJson {
  def serialise(r: Seq[Investigation]) = Jsoniser.jsonise(r)
}
