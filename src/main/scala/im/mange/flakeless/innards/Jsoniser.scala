package im.mange.flakeless.innards

private object Jsoniser {
  import org.json4s.native.JsonMethods._
  import org.json4s.native.JsonParser
  import org.json4s.native.Serialization._
  import org.json4s.{Formats, NoTypeHints}
  import org.json4s.native.Serialization

  private val allFormats: Formats = Serialization.formats(NoTypeHints) ++ JavaTimeSerialisers.all

  def jsonise[A <: AnyRef](r: A): String = {
    implicit val formats = allFormats
    pretty(render(JsonParser.parse(write(r))))
  }
}


private object JavaTimeSerialisers {
  import java.time.LocalDateTime
  import java.time.format.DateTimeFormatter
  import java.time.temporal.{TemporalAccessor, TemporalQuery}

  import org.json4s._

  private val dateTime = LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

  def all = Seq(dateTime)

  case class LocalDateTimeSerializer(val format: DateTimeFormatter) extends CustomSerializer[LocalDateTime](_ => (
    {
      case JString(s) => format.parse(s, asQuery(LocalDateTime.from))
      case JNull => null
    },
    {
      case d: LocalDateTime => JString(format.format(d))
    }
  ))

  private def asQuery[A](f: TemporalAccessor => A): TemporalQuery[A] =
    new TemporalQuery[A] {
      override  def queryFrom(temporal: TemporalAccessor): A = f(temporal)
    }
}
