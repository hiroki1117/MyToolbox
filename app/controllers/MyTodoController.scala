package controllers

import javax.inject.Inject
import model.TodoRepository
import play.api.mvc.{AbstractController, ControllerComponents}
import util.LoginConfirmAction
import util.LoginApiConfirmAction
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

class MyTodoController @Inject()(repo: TodoRepository,
                                 loginAction: LoginConfirmAction,
                                 loginApiAction: LoginApiConfirmAction,
                                 cc: ControllerComponents
                                )(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def myTodo = loginAction{ Ok(views.html.VueAppTemplate("mytodo", "MyTodo"))}

  def getTodo = loginApiAction.async{
    repo.list().map{ todoList =>
      val response = Json.toJson(todoList.map(a => Json.obj("id" -> a.id, "content" -> a.content)))
      Ok(response)
    }
  }

  def createTodo = loginApiAction.async{ implicit request =>
    val json = request.body.asJson.get
    repo.create(json("todo").as[String]).map{ newTodo =>
      val response = Json.toJson(Json.obj("id" -> newTodo.id, "content" -> newTodo.content))
      Ok(response)
    }
  }

  def deleteTodo = loginApiAction.async{ implicit request =>
    val json = request.body.asJson.get
    repo.delete(json("id").as[Long]).map{id=>
      val result = if(id==1) true else false
      Ok(Json.toJson(Json.obj("status" -> result)))
    }
  }
}
