package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.I18nSupport

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport{
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def login() = Action {implicit request =>
    if(request.method=="POST") {
      Redirect(routes.HomeController.index())
    } else {
      Ok(views.html.login())
    }
  }
}
