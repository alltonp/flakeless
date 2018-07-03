package im.mange.flakeless.examples

import im.mange.flakeless._
import org.openqa.selenium.{By, WebDriver, WebElement}

object NewProjectGuide extends App {
  //(1) add sbt dependencies

  //"im.mange" %% "flakeless" % "latest-version" % "test"
  //"org.seleniumhq.selenium" %% "selenium-java" % "latest-version" % "test"


  //(2) create webdriver browser and wrap it in a flakeless

  val webDriver: WebDriver = ??? // e.g. new ChromeDriver()
  val config = Config(/* override any defaults here */)
  val flakeless = Flakeless(webDriver, config)


  //(3) use flakeless primitives to control the browser, passing in your flakeless instance

  Goto(flakeless, "http://foo.com/")
  SendKeys(flakeless, By.id("search-terms"), clear = true, "foo")
  Click(flakeless, By.id("search"))
  AssertElementTextEquals(flakeless, By.id("result-1"), "bar")
  AssertElementTextEquals(flakeless, By.id("result-2"), "baz")
  //all primitives are in the im.mange.flakeless package
  //if you need one that isn't supported please create an github issue


  //(4) always prefer element finding by 'id',
  //the only exception is asserting counts which should use 'class'

  AssertElementListCountEquals(flakeless, By.className("result"), 2)


  //(5) if you have nested components use a 'Path' to locate them to avoid holding on to stale elements
  // DO NOT use webdriver.findElement(), holding on to elements is the root of all evil

  val parentPath = Path(By.id("parent"))
  Click(flakeless, parentPath.extend(By.id("child")))
  //or
  Click(flakeless, Path(By.id("parent"), By.id("child")))


  //(6) use the Page Object pattern (https://martinfowler.com/bliki/PageObject.html) to evolve a test API
  //extend FluentDriver to assist this - i.e. (3) and (4) above can be re-written as:

  case class FooDriver(flakeless: Flakeless) extends FluentDriver {
    def open() = goto("http://foo.com/")
    def enterTerms(value: String) = sendKeys(By.id("search-terms"), value, clear = true)
    def clickSearch() = click(By.id("search"))

    def assertResults(expected: List[ExpectedResult]) = {
      assertElementListCountEquals(By.className("result"), expected.size)
      expected.foreach(e => assertElementTextEquals(By.id(s"result-${e.id}"), e.value))
    }
  }

  case class ExpectedResult(id: String, value: String)

  FooDriver(flakeless)
    .open()
    .enterTerms("hello")
    .clickSearch()
    .assertResults(List(
      ExpectedResult("1", "bar"),
      ExpectedResult("2", "baz")
    ))


  //(7) use flakeless reports to fix test failures and identify test performance bottlenecks
  // see: Reporting Guide


  //(8) if using scalatest, consider using https://github.com/alltonp/flakeless-scalatest
  // this helps configure flakeless for running tests in parallel with multiple applications and browsers
  // see: https://github.com/alltonp/flakeless-scalatest/blob/master/src/example/scala/im/mange/flakeless/scalatest/Example.scala


  //(9) That's it!

}
