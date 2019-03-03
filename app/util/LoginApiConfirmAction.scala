package util

import javax.inject.Inject
import play.api.mvc.{ActionBuilderImpl, BodyParsers, Request, Result}
import play.api.mvc.ControllerHelpers.Unauthorized

import scala.concurrent.{ExecutionContext, Future}

class LoginApiConfirmAction @Inject()(parser: BodyParsers.Default)(implicit ec: ExecutionContext) extends ActionBuilderImpl(parser){
  override def invokeBlock[A](request: Request[A], block: (Request[A] => Future[Result])): Future[Result] = {
    request.session.get("login").map(_ => block(request)).getOrElse(Future(Unauthorized))
  }
}
