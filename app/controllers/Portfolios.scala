package controllers

import utils.Logging
import play.api.mvc.{ Action, Controller }
import org.bson.types.ObjectId
import play.api.data.Forms._
import play.api.data.Form
import models.{ Media, Portfolio }

/**
  * User: f.patin
  * Date: 13/03/12
  * Time: 17:26
  */

object Portfolios extends Controller {

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
      Ok(views.html.portfolios.index())
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

