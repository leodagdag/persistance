package controllers

import play.api.Play.current
import play.api._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._

import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.Imports._

import templates.HtmlFormat
import utils._
import models._
import views._
import org.joda.time.DateTime
import org.apache.commons.lang.StringEscapeUtils

/**
 * User: leodagdag
 * Date: 21/03/12
 * Time: 09:48
 */

object Blog extends Controller with Secured {

  lazy val config: Configuration = Application.config.get.getConfig("blog").getOrElse {
    current.configuration.globalError("app.blog config is missing")
    Configuration.empty
  }

  implicit lazy val dao = Post


  val postForm =
    Form(
      mapping(
        "_id" -> optional(text),
        "title" -> nonEmptyText,
        "content" -> nonEmptyText,
        "featured" -> boolean)
        ((_id, title, content, featured) =>
          Post(
            _id = ObjectId.massageToObjectId(_id),
            title = title,
            content = content,
            featured = featured))
        ((post: Post) =>
          Some(
            Some(post._id.toString),
            post.title,
            post.content,
            post.featured))

    )

  def index = Logging {
    Action {
      implicit request =>
        val count = Post.count()
        val featured = Post.featured
        val posts = models.Post.byPage(1)
        Ok(html.blog.index(count, featured, posts))
    }
  }

  def show(id: String) = Logging {
    Action {
      implicit request =>
        val post = Post.findOneByID(ObjectId.massageToObjectId(id))
        post match {
          case Some(post) => Ok(html.blog.show(post))
          case None => NotFound
        }

    }
  }

  def edit(id: String) = Logging {
    IsAuthenticated {
      username =>
        Action {
          implicit request =>
            val post = Post.findOneByID(ObjectId.massageToObjectId(id))
            post match {
              case Some(post) => Ok(html.blog.edit(post._id.toString(), postForm.fill(post)))
              case None => NotFound
            }
        }
    }
  }

  def update(id: String) = Logging {
    IsAuthenticated {
      username =>
        Action {
          implicit request =>
            var p = postForm.bindFromRequest
            p.fold(
              formWithErrors => BadRequest(html.blog.edit(id, formWithErrors)),
              updPost => {
                val post = Post.findOneByID(ObjectId.massageToObjectId(id))
                post match {
                  case Some(post) =>
                    post.copy(authorId = Some(user.get._id))
                    Post.update(MongoDBObject("_id" -> Some(post._id)), post.simpleCopy(updPost), false, false, new WriteConcern())
                    Redirect(routes.Blog.show(id)).flashing("success" -> "Post %s has been updated".format(post.title))
                  case None => NotFound
                }
              })
        }
    }
  }

}
