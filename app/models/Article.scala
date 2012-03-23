package models

import scala.collection.JavaConversions._
import org.codehaus.jackson.annotate.JsonProperty
import reflect.BeanProperty
import javax.persistence.Id
import play.api.Play.current
import play.modules.mongodb.jackson.MongoDB
import java.util.Date
import net.vz.mongodb.jackson.{JacksonDBCollection, ObjectId}

/**
 * User: leodagdag
 * Date: 22/03/12
 * Time: 22:18
 */

case class Article(@ObjectId @Id val id: String,
              @BeanProperty @JsonProperty("date") val created: Date = new Date(),
              @BeanProperty @JsonProperty("title") val title: String,
              @BeanProperty @JsonProperty("author") val author: String,
              @BeanProperty @JsonProperty("content") val content: String) {
}

object Article {
  private lazy val db = MongoDB.collection("Article", classOf[Article], classOf[String])

  def apply(title: String, author: String, content: String) = {
      new Article("", null, title, author, content)
  }
  def save(article: Article) {
    db.save(article)
  }

  def findById(id: String) = Option(db.findOneById(id))

  
  
  def findByAuthor(author: String) = db.find().is("author", author).iterator().toSeq

  def all = db.find().iterator().toList
  
  
  
  def deleteAll() = db.drop()
  
  
}