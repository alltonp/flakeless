package im.mange.flakeless.innards

import java.time.LocalDateTime

case class Investigation(flightNumber: Int, suite: String, test: String, success: Boolean,
                         started: Option[LocalDateTime], finished: Option[LocalDateTime],
                         firstInteraction: Option[LocalDateTime],
                         grossDurationMillis: Option[Long], netDurationMillis: Option[Long], dataPointCount: Int)
