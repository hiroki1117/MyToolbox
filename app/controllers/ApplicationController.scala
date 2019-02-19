package controllers

import javax.inject._
import play.api.mvc._
import com.danielasfregola.twitter4s.TwitterRestClient
import util.LoginConfirmAction
import scala.util.Try

class ApplicationController @Inject()(loginAction: LoginConfirmAction, cc: ControllerComponents) extends AbstractController(cc) {
  val client = TwitterRestClient()

  def myTweet() = loginAction{ Ok(views.html.VueAppTemplate("mytweet", "MyTweet")) }

  def postTweetApi() = Action{implicit request =>
    val data = (request.body.asJson.get \ "tweet").get.toString().replaceAll("\"", "")
    val message = Try(client.createTweet(status=data)).fold(_=>"error", _=>"success")
    Ok(message)
  }
}
