package im.mange.flakeless.innards

import im.mange.little.json.{LittleJodaSerialisers, LittleSerialisers}
import org.json4s.{Formats, NoTypeHints}
import org.json4s.native.Serialization

//TODO: this should be in little
private [flakeless] object Serialisers {
  val all: Formats = Serialization.formats(NoTypeHints) ++ LittleSerialisers.all ++ LittleJodaSerialisers.all
}
