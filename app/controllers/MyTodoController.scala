package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import util.LoginConfirmAction
import play.api.libs.json._

class MyTodoController @Inject()(loginAction: LoginConfirmAction, cc: ControllerComponents) extends AbstractController(cc) {

  def myTodo = loginAction{ Ok(views.html.VueAppTemplate("mytodo", "MyTodo"))}

  def getTodo = Action{
    val response = Json.toJson(List("from", "play", "framework"))
    Ok(response)
  }
}
