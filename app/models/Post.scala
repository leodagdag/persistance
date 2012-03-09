package models
import org.bson.types.ObjectId

import com.mongodb.casbah.Imports.WriteConcern
import com.mongodb.casbah.query.Imports.DBObject
import com.mongodb.casbah.query.Imports.MongoDBObject
import com.mongodb.casbah.query.Imports.wrapDBObj
import com.novus.salat.TypeHintFrequency
import com.novus.salat.grater
import com.novus.salat.Context
import com.novus.salat.StringTypeHintStrategy

import play.api.Play.current
import plugin.MongoDBPlugin




case class Post(
  title: String,
  _id: Option[ObjectId] = None
)

object PostRepository {
  private val posts = MongoDBPlugin.getCollection("Post")

  
  def all: Seq[Post] = posts.find().map(fromDb).toSeq

  def byId(id: ObjectId): Option[Post] = posts.findOneByID(id).map(fromDb)

  def save(post: Post): Post = {
    val dbo = toDb(post)
    posts.save(dbo, WriteConcern.Safe)
    post._id match {
      case Some(_) => post
      case None =>
        val newId = dbo.as[ObjectId]("_id")
        post.copy(_id = Some(newId))
    }
  }

  def removeById(id: ObjectId) = posts.remove(MongoDBObject("_id" -> id))

  // Customize Salat context
  implicit val ctx = new Context {
    val name = "Custom Context"
    override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary)
  }

  private def fromDb(dbObject: DBObject): Post = grater[Post].asObject(dbObject)
  private def toDb(post: Post): DBObject = grater[Post].asDBObject(post)
}