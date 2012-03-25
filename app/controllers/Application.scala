package controllers

import play.api.mvc._
import play.api.Play.current
import play.api.Configuration
import utils.Logging

object Application extends Controller {

  lazy val config:Option[Configuration] = current.configuration.getConfig("app")
  
  def index = Logging {
    Action {
      Ok(views.html.index("Your new application is ready."))
    }
  }


}