package im.mange.flakeless.innards

import org.openqa.selenium.WebElement

//TODO: jsonate me later
//TODO: maybe don't pass actual, but provide a function in describe instead
//TODO: this is starting to look like an Actual
//TODO; so shouldnt take an Intention
case class Description(actual: Option[(WebElement) => String] = None) {

  //TODO: ultimately shouldn't need this here, extract formatter
  case class LabelAndValue(label: Option[String], value: String) {
    def describe = label match {
      case Some(l) => s"""$l: $value"""
      case None => value
    }
  }

  def describeActual(webElement: WebElement): String = {
    try {
      reallyDescribe(Some(webElement))
    }
    catch {
      case e: Exception => s"Exception thrown while describing $this cause:\n" + e.getMessage
    }
  }

  private def reallyDescribe(webElement: Option[WebElement]): String = {
    (
      //TODO: decide if we this extra debug when we json things, might be good for click etc
//      Seq(
//        webElement.map(e => LabelAndValue(Some("displayed"), e.isDisplayed.toString)),
//        webElement.map(e => LabelAndValue(Some("enabled"), e.isEnabled.toString))
//      ) ++
        Seq(
          actual.map(bw => butWasSafely(webElement, bw))
        )
      ).flatten.mkString("\n| ")
  }

  private def butWasSafely(webElement: Option[WebElement], bw: (WebElement) => String): String = {
    try {
      webElement.fold("no element"){bw(_)}
    }
    catch {
      case e: Exception => "Exception thrown while getting actual: " + e.getMessage
    }
  }
}
