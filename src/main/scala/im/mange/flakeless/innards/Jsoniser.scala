package im.mange.flakeless.innards

private object Jsoniser {
  import org.json4s.native.JsonMethods._
  import org.json4s.native.JsonParser
  import org.json4s.native.Serialization._
//  import im.mange.little.json.{LittleJodaSerialisers, LittleSerialisers}
  import org.json4s.{Formats, NoTypeHints}
  import org.json4s.native.Serialization

  private val allFormats: Formats = Serialization.formats(NoTypeHints) ++ JavaTimeSerialisers.all

  def jsonise[A <: AnyRef](r: A): String = {
    implicit val formats = allFormats
    pretty(render(JsonParser.parse(write(r))))
  }
}

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.{TemporalAccessor, TemporalQuery}

import org.json4s._

import scala.util.Try

//TODO: maybe it's time for this to move out into a little-json project
object JavaTimeSerialisers {
//  private val datePattern = DateTimeFormatter.ISO_LOCAL_DATE_TIME
  private val dateTimePattern = DateTimeFormatter.ISO_LOCAL_DATE_TIME

//  val date     = SerialSerialiser[LocalDate](s ⇒ datePattern.print(s), s ⇒ opt(datePattern.parseLocalDate(s)))
  val dateTime = LocalDateTimeSerializer(dateTimePattern)

  def all = Seq(dateTime)

  private def opt[T](v: ⇒T): Option[T] = Try(v).toOption

  //TIP: this is because the json4s DateTimeSerializer parses UTC DateTimes in the users default TimeZone (i.e. London), tsk.
  case class LocalDateTimeSerializer(val format: DateTimeFormatter) extends CustomSerializer[LocalDateTime](_ => (
    {
      case JString(s) => format.parse(s, asQuery(LocalDateTime.from))
      case JNull => null
    },
    {
      case d: LocalDateTime => JString(format.format(d))
    }
  ))

  def asQuery[A](f: TemporalAccessor => A): TemporalQuery[A] =
    new TemporalQuery[A] {
      override  def queryFrom(temporal: TemporalAccessor): A = f(temporal)
    }
}

//object LittleSerialisers {
//  val number        = SerialSerialiser[BigDecimal](_.toString(), s ⇒ opt(BigDecimal(s)))
////  val percentage    = SerialSerialiser[Percentage](_.underlyingValue, s ⇒ opt(Percentage.fromDecimalFraction(s)))
//  val boolean       = SerialSerialiser[Boolean](_.toString, s ⇒ opt(s.toBoolean))
////  val amount        = SerialSerialiser[Amount](_.underlyingValue, s ⇒ opt(Amount(s)))
//
//  def all = Seq(number, boolean)
//
//  private def opt[T](v: ⇒T): Option[T] = Try(v).toOption
//}

//case class SerialSerialiser[T: ClassTag](serialise: T ⇒ String, deserialise: String ⇒ Option[T]) extends Serializer[T] {
//  private val TheClass       = implicitly[ClassTag[T]].runtimeClass
//  private val serialiserName = TheClass.getSimpleName
//
//  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), T] = {
//    case (TypeInfo(TheClass, _), JString(value)) ⇒ deserialise(value).getOrElse(failHard(value, None))
//  }
//
//  private def failHard(value: Any, thrown: Option[Throwable] = None) =
//    throw new MappingException(s"Can't convert [$value] to an instance of $serialiserName.${thrown.fold("")("Wrapped exception: " + _)}")
//
//  def serialize(implicit format: Formats): PartialFunction[Any, JValue] = {
//    case x: T ⇒ JString(serialise(x))
//  }
//}
