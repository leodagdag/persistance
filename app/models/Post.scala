package models

import org.bson.types.ObjectId

import com.mongodb.casbah.Imports.WriteConcern
import com.mongodb.casbah.query.Imports.wrapDBObj
import play.api.Play.current
import plugin.MongoDBPlugin
import com.mongodb.casbah.commons.TypeImports._
import com.novus.salat._


case class Post(
                 var title: String,
                 val _id: Option[ObjectId] = None
                 )

object Post extends Model[Post] {

  override lazy val coll = MongoDBPlugin.getCollection("Post")

  def fromDb(dbObject: DBObject): Post = grater[Post].asObject(dbObject)

  def toDb(post: Post): DBObject = grater[Post].asDBObject(post)

  def save(post: Post): ObjectId = {
    val dbo = toDb(post)
    coll.save(dbo, WriteConcern.Safe)
    post._id.getOrElse({
      val newId = dbo.as[ObjectId]("_id")
      post.copy(_id = Some(newId))
      newId
    })
  }


}

