package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current

import utils.Logging
import utils._
import models._
import views._

object Application extends Controller {

  lazy val config: Option[Configuration] = current.configuration.getConfig("app")

  lazy val applicationName = current.configuration.getConfig("application").get.getString("name").getOrElse { config.get.reportError("application.name", "Missing value") }

  def index = Logging {
    Action { implicit request =>
      Ok(views.html.index("Your new application is ready."))
    }
  }

  // -- Authentication
  /**
    * Login form
    */
  val loginForm = Form(
    tuple(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText)
      verifying ("Invalid username or password", result => result match {
        case (email, password) => User.authenticate(email, password).isDefined
      }))

  /**
    * Login page.
    */
  def login = Logging {
    Action { implicit request =>
      Ok(html.login(loginForm))
    }
  }

  /**
    * Handle login form submission.
    */
  def authenticate = Logging {
    Action { implicit request =>
      loginForm.bindFromRequest.fold(
        formWithErrors => BadRequest(html.login(formWithErrors)),
        user => Redirect(routes.Administration.index).withSession("username" -> user._1))
    }
  }

  /**
    * Logout and clean the session.
    */
  def logout = Action {
    Redirect(routes.Application.login).withNewSession.flashing(
      "success" -> "You've been logged out")
  }

}