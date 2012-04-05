package models

import _root_.salactx._
import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import controllers.Blog
import javax.persistence.EntityNotFoundException
import org.joda.time.DateTime
import play.api.templates.{HtmlFormat, Html}

case class Post(_id: ObjectId = new ObjectId,
                title: String,
                content: String,
                authorId: Option[ObjectId] = None,
                created: DateTime = new DateTime(),
                featured: Boolean = false,
                var comments: List[Comment] = Nil) {
  def simpleCopy(src: Post): Post = {
    this.copy(title = src.title,
      content = src.content,
      featured = src.featured)
  }
}

object Post extends SalatDAO[Post, ObjectId](collection = DB.connection("Post")) with Model[Post] {

  com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers()
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()

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

}