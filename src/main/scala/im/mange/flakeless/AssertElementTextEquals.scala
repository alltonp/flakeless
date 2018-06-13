package im.mange.flakeless

import im.mange.flakeless.innards.{Command, Description, WaitForElement}
import org.openqa.selenium.{By, SearchContext, WebDriver}

object AssertElementTextEquals {
  def apply(flakeless: Flakeless, by: By, expected: String): Unit = {
    apply(flakeless.rawWebDriver, by, expected, Some(flakeless))
  }

  def apply(in: SearchContext, by: By, expected: String, flakeless: Option[Flakeless] = None): Unit = {
    WaitForElement(flakeless,
      Command("AssertElementTextEquals", Some(in), Some(by), expected = Some(expected)),
      description = e => Description(actual = Some((e) => e.getText)).describeActual(e),
      condition = e => e.getText == expected)
  }
}

object AssertUrlEndsWith {
  def apply(flakeless: Flakeless, expected: String): Unit = {
    apply(flakeless.rawWebDriver, expected, Some(flakeless))
  }

  def apply(webDriver: WebDriver, expected: String, flakeless: Option[Flakeless] = None): Unit = {
    //TODO: this seems like a bit of an abuse of WaitForElement, should probably have a WaitForCondition
    //or make WithoutElement have a condition, which can be true for Close, Goto
    WaitForElement(flakeless,
      Command("AssertUrlEndsWith", None, None, expected = Some(expected)),
      description = e => webDriver.getCurrentUrl,
      condition = e => webDriver.getCurrentUrl.endsWith(expected))
  }
}
