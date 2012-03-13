package controllers

import play.api._
import play.api.mvc._
import utils.Logging

object Application extends Controller {

  def index = Logging {
    Action {
      Ok(views.html.index("Your new application is ready."))
    }
  }

  def portfolio = Logging {
    Action{
      Ok(views.html.portfolio())
    }
  }
}