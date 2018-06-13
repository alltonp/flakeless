package im.mange.flakeless.innards

import im.mange.flakeless.{Config, Flakeless}
import org.openqa.selenium.{By, WebElement}

//TODO: model Condition
//TODO: work out when description is an Actual and when its something else (like not found)
//... Actual sounds assertion based
//... other sounds like an Action ...
//... in which case arr we in Action & Check extends Command land?

//TODO: should this be WaitForCondition
/* deliberately not private [flakeless] */ object WaitForElement {
  def apply(flakeless: Option[Flakeless], command: Command,
            description: (WebElement) => String,
            condition: (WebElement) => Boolean) = {

    Execute(flakeless, new WaitForElement(command, description, condition))
  }
}

private class WaitForElement(val command: Command,
                             description: (WebElement) => String,
                             condition: (WebElement) => Boolean) extends Executable {

  override def execute(context: Context, config: Config) {
    (command.in, command.by) match {
      case (Some(in), Some(by)) =>
        //TODO: we should ensure there is only one element - make configurable
        Wait.waitUpTo(config).forCondition(command,
          {
            val element = in.findElement(by)
            val result = condition(element)
            if (result) context.succeeded()
            else context.failed(description(element))
            result
          },
          description(in.findElement(by))
        )
      case _ => throw new RuntimeException("cannot wait without in and by")
    }
  }

}
