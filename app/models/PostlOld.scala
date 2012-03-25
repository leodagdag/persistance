package models

import org.bson.types.ObjectId

import play.api.Play.current
import plugin.MongoDBPlugin
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.Imports

case class PostOld(
  var title: String,
  val _id: Option[ObjectId] = None)

object PostOld extends Model[PostOld] {

  override lazy val coll = MongoDBPlugin.getCollection("Post")

  def fromDb(dbObject: DBObject): PostOld = new PostOld(dbObject.getAs[String]("title").getOrElse(""), dbObject.getAs[ObjectId]("_id"))

  def toDb(post: PostOld): DBObject = MongoDBObject("_id" -> post._id.getOrElse(null), "title" -> post.title)

  def save(post: PostOld): ObjectId = {
    val dbo = toDb(post)
    coll.save(dbo, WriteConcern.Safe)
    post._id.getOrElse({
      val newId = dbo.as[ObjectId]("_id")
      post.copy(_id = Some(newId))
      newId
    })
  }

}

