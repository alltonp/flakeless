package im.mange.flakeless

import im.mange.flakeless.innards.{Body, Description, WaitForElement}
import org.openqa.selenium.{By, WebDriver, WebElement}

object AssertElementTextContains {
  def apply(webDriver: WebDriver, by: By, expected: String): Unit = {
    apply(Body(webDriver), by, expected)
  }

  def apply(in: WebElement, by: By, expected: String): Unit = {
    WaitForElement(in, by,

      description = e => Description("AssertElementTextContains", in, by, expected = Some(expected),
        actual = Some((e) => e.getText))
        .describe(e),

      condition = e => e.getText.contains(expected))
  }
}