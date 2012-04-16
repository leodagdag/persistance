package controllers

import play.api.Play.current
import play.api._
import libs.json.Json._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import org.bson.types.ObjectId
import com.mongodb.casbah.commons.MongoDBObject

import models._
import views._

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




  def addComment(id: String) = Logging {
    IsAuthenticated {
      username =>
        Action {
          implicit request => {
            Form("content" -> nonEmptyText).bindFromRequest.fold(
              formWithErrors => BadRequest,
              newComment => {
                val comment: Comment = new Comment(user = user.get, content = newComment)
                Post.addComment(ObjectId.massageToObjectId(id), comment);
                Ok
              }
            )
          }
        }
    }
  }

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

  def showJson(id: String) = Logging {
    Action {
      implicit request =>
        val post = Post.findOneByID(ObjectId.massageToObjectId(id))
        post match {
          case Some(post) => Ok(toJson(Post.writes(post)))
          case None => NotFound
        }
    }
  }

  def create = Logging {
    IsAuthenticated {
      username =>
        Action {
          implicit request => {
            Ok(html.blog.create(postForm))
          }
        }
    }
  }

  def save = Logging {
    IsAuthenticated {
      username =>
        Action {
          implicit request =>
            var p = postForm.bindFromRequest
            p.fold(
              formWithErrors => BadRequest(html.blog.create(formWithErrors)),
              newPost => {
                val id = Post.insert(newPost.copy(authorId = Some(user.get._id)))
                Redirect(routes.Blog.show(id.get.toString)).flashing("success" -> "Post %s has been created".format(newPost.title))
              }
            )
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
                    Post.update(MongoDBObject("_id" -> Some(post._id)), post.simpleCopy(updPost))
                    Redirect(routes.Blog.show(id)).flashing("success" -> "Post %s has been updated".format(post.title))
                  case None => NotFound
                }
              }
            )
        }
    }
  }


}
