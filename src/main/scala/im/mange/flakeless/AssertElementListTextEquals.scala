package im.mange.flakeless

import im.mange.flakeless.innards.{Body, WaitForElements}
import org.openqa.selenium.{By, WebDriver, WebElement}

object AssertElementListTextEquals {
  def apply(webDriver: WebDriver, by: By, expected: List[String]): Unit = {
    apply(Body(webDriver), by, expected)
  }

  //TODO: I need to be converted to a Description, just not possible yet..
  def apply(in: WebElement, by: By, expected: List[String]): Unit = {
    WaitForElements(in, by,

      description = es => s"AssertElementListTextEquals\n| in: $in\n| $by\n| expected: '$expected'\n| but was: '${es.map(t => s"'${t.getText}'").mkString(", ")}'",

      condition = es => es.map(_.getText) == expected)
  }
}