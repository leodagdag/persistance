package models

import com.mongodb.casbah.Imports._

import play.api.Play.current
import plugin.{GridFSHelper, MongoDBPlugin}


/**
 * Created by IntelliJ IDEA.
 * User: leodagdag
 * Date: 21/03/12
 * Time: 12:02
 */

case class Media(
                  var name: String,
                  var fileId: ObjectId = null,
                  var _id: Option[ObjectId] = None
                  )
/*
object Media extends Model[Media] {

  override lazy val coll = MongoDBPlugin.getCollection("Media")

  protected def fromDb(dbObject: DBObject) = grater[Media].asObject(dbObject)

  protected def toDb(media: Media) = grater[Media].asDBObject(media)

  def save(media: Media) = save(media,null, null)

  def save(media: Media, file: java.io.File, params: Map[String, AnyRef]): ObjectId = {
    if (file != null) {
      media.fileId = GridFSHelper.createNewFile(file)
    }
    val dbo = toDb(media)
    coll.save(dbo, WriteConcern.Safe)
    media._id.getOrElse({
      val newId = dbo.as[ObjectId]("_id")
      media.copy(_id = Some(newId))
      newId
    })
  }


}
*/