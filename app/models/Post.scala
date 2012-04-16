package models

import _root_.plugin._
import com.novus.salat.dao._
import com.mongodb.casbah.Imports._
import controllers.Blog
import javax.persistence.EntityNotFoundException
import org.joda.time.DateTime
import play.api.libs.json.Json._
import play.api.libs.json.{JsObject, JsString, JsValue, Writes}

case class Post(_id: ObjectId = new ObjectId,
                title: String,
                content: String,
                featured: Boolean = false,
                authorId: Option[ObjectId] = None,
                created: DateTime = new DateTime(),
                var comments: List[Comment] = Nil) {
  def simpleCopy(src: Post): Post = {
    this.copy(title = src.title,
      content = src.content,
      featured = src.featured)
  }
}

object Post extends SalatDAO[Post, ObjectId](collection = DB.connection("Post")) with Model[Post, ObjectId] with Writes[Post] {

  com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers()
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()

  def writes(post: Post): JsValue = JsObject(
    List(
      "_id" -> JsString(post._id.toString),
      "title" -> toJson(post.title),
      "content" -> toJson(post.content),
      "created" -> toJson(post.created.toDate.getTime),
      "featured" -> toJson(post.featured)
    ))

  override val PAGE_SIZE = Blog.config.getInt("pageSize").getOrElse(10)

  def addComment(id: ObjectId, comment: Comment) {
    val post = this.findOneByID(id)
    post match {
      case Some(post) =>
        if (post.comments == null) {
          post.comments = List(comment)
        } else {
          post.comments = post.comments.+:(comment)
        }
        this.save(post)
      case _ => throw new EntityNotFoundException("Problem retrieving a post with id[%s]".format(id))
    }
  }

  def featured: Option[Post] = Post.findOne(MongoDBObject("featured" -> true))

  override def update[A <: DBObject](q: A, post: Post)(implicit dao: SalatDAO[Post, ObjectId]) {
    if (post.featured) {
      updateFeatured()
    }
    super.update(q, post)
  }


  override def insert(post: Post) = {
    if (post.featured) {
      updateFeatured()
    }
    super.insert(post)
  }

  def updateFeatured() {
    collection.updateMulti(MongoDBObject("featured" -> true), $set("featured" -> false))
  }
}