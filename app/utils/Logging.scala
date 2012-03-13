package utils

import play.api.mvc.{Action, Request, Result}
import play.api.Logger

/**
  * User: f.patin
 * Date: 13/03/12
 * Time: 11:42
  */

case class Logging[A](action: Action[A]) extends Action[A] {

  def apply(request: Request[A]): Result = {
    Logger.debug {
      "Calling action (%s)".format {
        request.toString()
      }
    }
    action(request)
  }

  lazy val parser = action.parser
}
