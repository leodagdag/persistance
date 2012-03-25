package controllers

import play.api.mvc.Controller
import utils.Logging
import play.api.Configuration
import play.api.Play.current

/**
 * User: leodagdag
 * Date: 21/03/12
 * Time: 09:48
 */

object Blog  extends Controller {

  lazy val config: Configuration = Application.config.get.getConfig("blog").getOrElse{
    current.configuration.globalError("app.blog config is missing")
    Configuration.empty
    }
  
  def index = Logging {
    TODO
  }

}
