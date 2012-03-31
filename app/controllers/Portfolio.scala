package controllers

import play.api.mvc.{Action, Controller}
import org.bson.types.ObjectId
import play.api.data.Forms._
import play.api.data.Form
import models._

/**
 * User: f.patin
 * Date: 13/03/12
 * Time: 17:26
 */

object Portfolio extends Controller with Secured {

  /*
  val portfolioForm = Form(
    mapping(
      "name" -> text,
      "medias" -> List[Media],
      "id" -> Option[ObjectId]
    )(Portfolio.apply)(Portfolio.unapply)
  )
    */
  def index = Logging {
    Action {
      implicit request =>
        Ok(views.html.portfolio.index())
    }
  }

  def list = Logging {
    Action {
      TODO
    }
  }

  def get(id: ObjectId) = Logging {
    Action {
      TODO
    }
  }

}

