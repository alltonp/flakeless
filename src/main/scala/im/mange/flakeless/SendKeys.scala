package im.mange.flakeless

import im.mange.flakeless.innards.{Body, Description, WaitForInteractableElement}
import org.openqa.selenium.{By, WebDriver, WebElement}

object SendKeys {
  def apply(flakeless: Flakeless, by: By, keysToSend: CharSequence*): Unit = {
    apply(Body(flakeless.rawWebDriver), by, keysToSend: _*)
  }

  def apply(in: WebElement, by: By, keysToSend: CharSequence*): Unit = {
    WaitForInteractableElement(in, by,

      description = e => Description("SendKeys", in, by, args = Map("keysToSend" -> keysToSend.toString())).describe(e),

      action = e => e.sendKeys(keysToSend: _*)
    )
  }
}
