package im.mange.flakeless

import org.openqa.selenium.By

trait FluentDriver {
  protected val flakeless: Flakeless

  def assertElementAttributeContains(by: By, attribute: String, expected: String): this.type = {
    AssertElementAttributeContains(flakeless, by, attribute, expected); this
  }

  def assertElementAttributeEquals(by: By, attribute: String, expected: String): this.type = {
    AssertElementAttributeEquals(flakeless, by, attribute, expected); this
  }

  def assertElementClassContains(by: By, expected: String): this.type = {
    AssertElementClassContains(flakeless, by, expected); this
  }

  def assertElementClassEquals(by: By, expected: String): this.type = {
    AssertElementClassEquals(flakeless, by, expected); this
  }

  def assertElementDisabled(by: By): this.type = {
    AssertElementDisabled(flakeless, by); this
  }

  def assertElementDisplayed(by: By): this.type = {
    AssertElementDisplayed(flakeless, by); this
  }

  def assertElementEmpty(by: By): this.type = {
    AssertElementEmpty(flakeless, by); this
  }

  def assertElementEnabled(by: By): this.type = {
    AssertElementEnabled(flakeless, by); this
  }

  def assertElementHidden(by: By): this.type = {
    AssertElementHidden(flakeless, by); this
  }

  def assertElementListCountEquals(by: By, expected: Int): this.type = {
    AssertElementListCountEquals(flakeless, by, expected); this
  }

  def assertElementListTextContains(by: By, expected: String): this.type = {
    AssertElementListTextContains(flakeless, by, expected); this
  }

  def assertElementListTextEquals(by: By, expected: List[String]): this.type = {
    AssertElementListTextEquals(flakeless, by, expected); this
  }

  def assertElementSelected(by: By): this.type = {
    AssertElementSelected(flakeless, by); this
  }

  def assertElementSetTextEquals(by: By, expected: Set[String]): this.type = {
    AssertElementSetTextEquals(flakeless, by, expected); this
  }

  def assertElementTextContains(by: By, expected: String): this.type = {
    AssertElementTextContains(flakeless, by, expected); this
  }

  def assertElementTextEquals(by: By, expected: String): this.type = {
    AssertElementTextEquals(flakeless, by, expected); this
  }

  def assertElementUnselected(by: By): this.type = {
    AssertElementUnselected(flakeless, by); this
  }

  def assertUrlEndsWith(expected: String): this.type = {
    AssertUrlEndsWith(flakeless, expected); this
  }

  def click(by: By): this.type = {
    Click(flakeless, by); this
  }

  def goto(url: String): this.type = {
    Goto(flakeless, url); this
  }

  def sendKeys(by: By, keysToSend: CharSequence, clear: Boolean = true): this.type = {
    SendKeys(flakeless, by, clear = clear, keysToSend); this
  }

  def uploadFile(by: By, filename: String): this.type = {
    UploadFile(flakeless, by, filename); this
  }

  def announce(description: String): this.type = {
    flakeless.inflightAnnouncement(description); this
  }
}
