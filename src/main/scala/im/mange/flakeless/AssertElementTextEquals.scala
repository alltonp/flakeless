package im.mange.flakeless

import im.mange.flakeless.innards.{Command, Description, WaitForElement}
import org.openqa.selenium.{By, SearchContext}

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


