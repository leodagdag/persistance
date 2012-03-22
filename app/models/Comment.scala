package models

import scala.collection.JavaConversions.iterableAsScalaIterable
import org.codehaus.jackson.annotate.JsonProperty
import reflect.BeanProperty
import javax.persistence.Id
import play.api.Play.current
import play.modules.mongodb.jackson.MongoDB
import net.vz.mongodb.jackson.ObjectId

/**
 * User: leodagdag
 * Date: 22/03/12
 * Time: 22:18
 */

case class Comment(@ObjectId @Id val id: String,
              @BeanProperty @JsonProperty("date") val date: java.util.Date,
              @BeanProperty @JsonProperty("title") val title: String,
              @BeanProperty @JsonProperty("author") val author: String,
              @BeanProperty @JsonProperty("content") val content: String)

object Comment {
  private lazy val db = MongoDB.collection("Comment", classOf[Comment], classOf[String])

  def save(comment: Comment) {
    db.save(comment)
  }

  def findById(id: String) = Option(db.findOneById(id))

  def findByAuthor(author: String) = db.find().is("author", author)
}