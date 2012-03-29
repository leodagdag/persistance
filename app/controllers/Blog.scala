package controllers

import play.api.mvc.Controller
import utils.Logging
import play.api.Configuration
import play.api.Play.current
import models._
import play.api.mvc.Action
import play.api.i18n.Lang
import play.api.i18n.Messages

/**
  * User: leodagdag
  * Date: 21/03/12
  * Time: 09:48
  */

object Blog extends Controller {

  lazy val config: Configuration = Application.config.get.getConfig("blog").getOrElse {
    current.configuration.globalError("app.blog config is missing")
    Configuration.empty
  }

  implicit lazy val dao = Post

  def index = Logging {
    Action { implicit request =>
      val count = Post.count()
      val featured = Post.featured
      val posts = Post.byPage(1)
      Ok(views.html.blog.index(count, featured, posts))
    }
  }

}
