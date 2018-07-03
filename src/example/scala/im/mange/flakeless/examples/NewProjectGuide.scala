package im.mange.flakeless.examples

import im.mange.flakeless._
import org.openqa.selenium.{By, WebDriver, WebElement}

object NewProjectGuide extends App {
  //(1) in sbt - add dependencies

  //"im.mange" %% "flakeless" % "latest-version" % "test"
  //"org.seleniumhq.selenium" %% "selenium-java" % "latest-version" % "test"

  //(2) create your browser instance and wrap it in a flakless
  val webDriver: WebDriver = ??? // e.g. new ChromeDriver()
  val config = Config(/* override any defaults here */)
  val flakeless = Flakeless(webDriver, config)

  //given
  val element: WebElement = ???


  //(1) Replace side effecting actions on webElements e.g. .click(), .sendKeys() with corresponding primitive Click(), SendKeys()

    //unsafe
    element.findElement(By.id("container")).click()
    //safe
    Click(element, By.id("container"))

    //unsafe
    element.findElement(By.id("container")).sendKeys("foo")
    //safe
    SendKeys(element, By.id("container"), clear = true, "foo")


  //(2) Replace all state querying on webElements with corresponding assertions

    //unsafe
    element.findElement(By.id("value")).getText == "expected"
    //safe
    AssertElementTextEquals(element, By.id("value"), "expected")

    //unsafe
    element.findElement(By.id("value")).getText.contains("contains")
    //safe
    AssertElementTextContains(element, By.id("value"), "contains")


  //(3) Repeat (1) and (2) until there are no usages of .findElement and .findElements

  //(4) Stop holding onto webElements they could be stale, always start from webDriver, if nesting is required use a Path

    //unsafe
    val parentElement = element.findElement(By.id("parent"))
    val childElement = parentElement.findElement(By.id("child"))
    childElement.click()
    //safe
    val parentPath = Path(By.id("parent"))
    Click(webDriver, parentPath.extend(By.id("child")))
    //or
    Click(webDriver, Path(By.id("parent"), By.id("child")))


  //(5) That's it!

}
