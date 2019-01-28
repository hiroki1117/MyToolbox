package forms

import play.api.data._
import play.api.data.Forms._

case class Password(password: String)

object Password {
  val passForm = Form( mapping("password" -> nonEmptyText)(Password.apply)(Password.unapply))
}