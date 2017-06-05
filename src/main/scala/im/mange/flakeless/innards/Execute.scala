package im.mange.flakeless.innards

import im.mange.flakeless.Flakeless

object Execute {
  def apply(flakeless: Option[Flakeless], executable: Executable): Unit = {
    val context = Context()

    try {
      //TODO: need description ...
      executable.execute(context)
      context.remember(true, "")
      flakeless.foreach(_.record(true, "SUCCESS", Some(context)))
    }

    catch {
      case e: Exception => {
        flakeless.foreach(_.record(false, "FAIL", Some(context)))
        throw e
      }

    }
  }
}
