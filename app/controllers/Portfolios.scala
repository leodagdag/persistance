package controllers

import utils.Logging
import play.api.mvc.{Action, Controller}

/**
 * User: f.patin
 * Date: 13/03/12
 * Time: 17:26
 */

object Portfolios extends Controller {

  def index = Logging {
    Action {
      Ok(views.html.portfolio())
    }
  }

}
