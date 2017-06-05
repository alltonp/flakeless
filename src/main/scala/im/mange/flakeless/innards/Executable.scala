package im.mange.flakeless.innards

import org.openqa.selenium.{By, WebElement}

trait Executable {
  def execute(context: Context)
  val command: Command
}

//TODO: improve rendering of options and in etc ...
//TODO: args at end? expected's earlier?
case class Command(name: String, in: WebElement, by: By,
                   args: Map[String, String] = Map.empty,
                   expected: Option[String] = None,
                   expectedMany: Option[List[String]] = None) {

  //TODO: this is all well hokey
  def describe = reallyDescribe

  //TODO: ultimately shouldn't need this here, extract formatter
  case class LabelAndValue(label: Option[String], value: String) {
    def describe = label match {
      case Some(l) => s"$l: '$value'"
      case None => value
    }
  }

  //TODO: render expectedMany
  private def reallyDescribe: String = {
    (
      Seq(
        Some(LabelAndValue(None, name)),
        Some(LabelAndValue(Some("by"), by.toString)),
        Some(LabelAndValue(Some("in"), in.toString))
      ) ++
        args.map(kv => Some(LabelAndValue(Some(kv._1), kv._2))) ++
        Seq(
          expected.map(e => LabelAndValue(Some("expected"), e))
        )
      ).flatten.map(_.describe).mkString("\n| ")
  }
}