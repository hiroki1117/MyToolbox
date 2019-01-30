package controllers

import javax.inject._
import play.api.mvc._
import com.danielasfregola.twitter4s.TwitterRestClient

class ApplicationController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val client = TwitterRestClient()

  def myTweet() = Action{ Ok(views.html.mytweet()) }

  def postTweet() = Action{implicit request =>
    val data = request.body.asFormUrlEncoded.get("tweet")
    client.createTweet(status=data(0))
    Ok(views.html.mytweet())
  }
}
