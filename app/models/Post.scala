package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import controllers.Blog
import javax.persistence.EntityNotFoundException
import salactx._

case class Post(_id: ObjectId = new ObjectId,
  title: String,
  content: Option[String] = None,
  featured: Boolean = false, 
  var comments: List[Comment] = null)

object Post extends SalatDAO[Post, ObjectId](collection = DB.connection("Post")) with Model[Post] {

  override val PAGE_SIZE = Blog.config.getInt("pageSize").getOrElse(10)

  def addComment(id: ObjectId, comment: Comment) {
    val post = this.findOneByID(id)
    post match {
      case Some(post) =>
        if(post.comments == null) { 
          post.comments = List(comment)
        } else {
          post.comments = post.comments.+:(comment)
        }
        this.save(post)
      case _ => throw new EntityNotFoundException("Problem retrieving a post with id[%s]".format(id))
    }
  }
  
  def featured = {
    val featured = Post.findOne(MongoDBObject("featured" -> true))
    featured
  }
  
}