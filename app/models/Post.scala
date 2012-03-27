package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import controllers.Blog
import javax.persistence.EntityNotFoundException

case class Post(_id: ObjectId = new ObjectId,
  title: String,
  content: String = null,
  var comments: List[Comment] = List.empty)

object Post extends SalatDAO[Post, ObjectId](collection = DB.connection("Post")) with Model[Post] {

  override val PAGE_SIZE = Blog.config.getInt("pageSize").getOrElse(10)

  def addComment(id: ObjectId, comment: Comment) {
    val post = this.findOneByID(id)
    post match {
      case Some(post) =>
        post.comments = post.comments :+ comment
        this.save(post)
      case _ => throw new EntityNotFoundException("Problem retrieving a post with id[%s]".format(id))
    }

  }
}