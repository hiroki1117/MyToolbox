package util

import javax.inject._
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import play.api.mvc.ControllerHelpers.Redirect

class LoginConfirmAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser) {
  override def invokeBlock[A](request: Request[A], block: (Request[A] => Future[Result])): Future[Result] = {
    request.session.get("login").map(_ => block(request)).getOrElse(Future(Redirect("login")))
  }
}


