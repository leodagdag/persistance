package models

import scala.collection.JavaConversions._
import org.codehaus.jackson.annotate.JsonProperty
import reflect.BeanProperty
import javax.persistence.Id
import play.api.Play.current
import play.modules.mongodb.jackson.MongoDB
import net.vz.mongodb.jackson.{ JacksonDBCollection, ObjectId }
import java.util.Date
import org.joda.time.DateTime
import controllers.Blog

/**
  * User: leodagdag
  * Date: 22/03/12
  * Time: 22:18
  */

case class Post(@ObjectId @Id var id: String,
                @BeanProperty @JsonProperty("date") val created: DateTime,
                @BeanProperty @JsonProperty("title") val title: String,
                @BeanProperty @JsonProperty("author") val author: String,
                @BeanProperty @JsonProperty("content") val content: String) {
  @ObjectId def getId = Option(id)
}

object Post {

  private val PAGE_SIZE = Blog.config.getInt("pageSize").getOrElse(10)

  private lazy val db = MongoDB.collection("Post", classOf[Post], classOf[String])

  def apply(title: String, author: String, content: String) = {
    new Post(null, new DateTime, title, author, content)
  }

  def save(post: Post) = {
    val result = db.save(post)
    val newId = result.getSavedId()
    val test = post.getId
    post.getId.getOrElse{
      post.copy(id = newId)
      newId
  	}
  }

  def findById(id: String) = {
    var post = db.findOneById(id)
    Option(post)
  }

  def findByAuthor(author: String) = db.find().is("author", author).iterator().toSeq

  def total = db.find().count()

  def byPage(num: Int): List[Post] = {
    var skip: Int = 0
    num match {
      case 0 => skip = 0
      case _ => skip = (num - 1) * PAGE_SIZE
    }
    db.find().skip(skip).limit(PAGE_SIZE).iterator().toList
  }

  def all = db.find().iterator().toList

  def deleteAll() { db.drop() }

}