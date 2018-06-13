package im.mange.flakeless.innards

import java.time.LocalDateTime

case class DataPoint(flightNumber: Int, when: LocalDateTime, description: Option[String],
                     command: Option[ReportCommand], context: Context, log: Option[List[String]])
