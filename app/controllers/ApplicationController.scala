package controllers

import javax.inject._
import play.api.mvc._
import com.danielasfregola.twitter4s.TwitterRestClient
import util.LoginConfirmAction
import scala.util.Try

class ApplicationController @Inject()(loginAction: LoginConfirmAction, cc: ControllerComponents) extends AbstractController(cc) {
  val client = TwitterRestClient()

  def myTweet() = loginAction{ Ok(views.html.mytweet()) }

  def postTweet() = loginAction{implicit request =>
    val data = request.body.asFormUrlEncoded.get("tweet")
    val message = Try(client.createTweet(status=data(0))).fold(fa=>"error", fb=>"success")
    Ok(views.html.mytweet(message))
  }
}
