package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current

import utils._
import models._
import views._

object Application extends Controller with Secured {

  protected val HOME_URL = "/"

  lazy val config: Option[Configuration] = current.configuration.getConfig("app")

  lazy val applicationName = current.configuration.getConfig("application").get.getString("name").getOrElse {
    config.get.reportError("application.name", "Missing value")
  }

  def index = Logging {
    Action {
      implicit request =>
        Ok(views.html.index("Your new application is ready."))
    }
  }

  // -- Authentication

  /**
   * Login object
   * @param username
   * @param password
   * @param redirect
   */
  case class Login(username: String, password: String, redirect: Option[String])

  /**
   * Login form
   */
  val loginForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "redirect" -> optional(text)
    )(Login.apply)(Login.unapply)
      .verifying("Invalid username or password", result => result match {
      case login: Login => User.authenticate(login.username, login.password).isDefined
    }))
  /**
   * Login page.
   */
  def login = Logging {
    Action {
      implicit request =>
        Ok(html.login(loginForm.fill(Login("", "", Some(flash.get("url").getOrElse("/"))))))
    }
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Logging {
    Action {
      implicit request =>
        loginForm.bindFromRequest.fold(
          formWithErrors =>
            BadRequest(html.login(formWithErrors)),
          login =>
            Redirect(login.redirect.getOrElse(HOME_URL)).withSession("username" -> login.username)
        )
    }
  }

  /**
   * Logout and clean the session.
   */
  def logout = Logging {
    Action {
      implicit request =>
        Redirect(request.headers.get(REFERER).getOrElse(HOME_URL)).withNewSession.flashing("success" -> "You've been logged out")

    }
  }

}