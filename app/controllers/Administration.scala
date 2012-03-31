package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current


object Administration extends Controller with Secured {

  def index = Logging {
    IsAuthenticated {
      username =>
        Action {
          implicit request =>
            Ok(views.html.administration.index())
        }
    }
  }

}