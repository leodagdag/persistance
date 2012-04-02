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
   * Login form
   */
  val loginForm = Form(
    tuple(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText,
      "redirect" -> text
    ).verifying("Invalid username or password", result => result match {
      case (email, password, redirect) => User.authenticate(email, password).isDefined
    }))

  /**
   * Login page.
   */
  def login = Logging {
    Action {
      implicit request => {
        loginForm.forField("redirect") {
          field =>
            flash.get("url").getOrElse("/admin")
        }
        Ok(html.login(loginForm))
      }
    }
  }

  /**
   * Handle login form submission.
   */
  def authenticate = Logging {
    Action {
      implicit request =>
        loginForm.bindFromRequest.fold(
          formWithErrors => BadRequest(html.login(formWithErrors)),
          user => {

            //Redirect(routes.Application.index).withSession("username" -> user._1))
            Logger.debug("authenticate:" + flash.get("url").getOrElse("/"))
            Redirect(flash.get("url").getOrElse("/")).withSession("username" -> user._1)
          })
    }
  }

  /**
   * Logout and clean the session.
   */
  def logout = Logging {
    Action {
      implicit request =>
        Redirect(routes.Application.login).withNewSession.flashing("success" -> "You've been logged out")
    }
  }

}