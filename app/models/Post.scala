package models

import org.bson.types.ObjectId

import com.mongodb.casbah.Imports.WriteConcern
import com.mongodb.casbah.query.Imports.MongoDBObject
import com.mongodb.casbah.query.Imports.wrapDBObj
import play.api.Play.current
import plugin.MongoDBPlugin
import com.mongodb.casbah.commons.TypeImports._
import com.novus.salat._


case class Post (
                 title: String,
                 _id: Option[ObjectId] = None
                 )

object PostRepository extends Model[Post] {
  private val posts = MongoDBPlugin.getCollection("Post")

  def getCollection() = MongoDBPlugin.getCollection("Post")

  def all: Seq[Post] = posts.find().map(fromDb).toSeq

  def byId(id: ObjectId): Option[Post] = posts.findOneByID(id).map(fromDb)

  def save(post: Post): ObjectId = {
    val dbo = toDb(post)
    posts.save(dbo, WriteConcern.Safe)
    post._id.getOrElse({
      val newId = dbo.as[ObjectId]("_id")
      post.copy(_id = Some(newId))
      newId
    })
  }

  def removeById(id: ObjectId) = posts.remove(MongoDBObject("_id" -> id))

  def fromDb(dbObject: DBObject): Post = grater[Post].asObject(dbObject)
  def toDb(post: Post): DBObject = grater[Post].asDBObject(post)
}

