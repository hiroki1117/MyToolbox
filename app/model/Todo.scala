package model

import java.sql.Date

final case class Todo(id: Long, content: String, created: Date)
