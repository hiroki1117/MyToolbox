package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.I18nSupport

@Singleton
class HomeController @Inject()(config: Configuration, cc: ControllerComponents) extends AbstractController(cc) with I18nSupport{
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def login() = Action {implicit request =>
    if(request.method=="POST") {
      import forms.Password._
      passForm.bindFromRequest().fold(
        error => BadRequest(views.html.login()),
        success => if(success.password==config.get[String]("password")) Redirect(routes.HomeController.index()) else BadRequest(views.html.login())
      )
    } else {
      Ok(views.html.login())
    }
  }
}
