package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current

import utils._

object Administration extends Controller with Secured {

  def index = Logging {
    IsAuthenticated { username =>
      Action {
        TODO
      }
    }
  }

}