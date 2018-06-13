package im.mange.flakeless

import im.mange.flakeless.innards.{Command, WaitForElement}
import org.openqa.selenium.WebDriver

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
