package models

import org.bson.types.ObjectId

import com.mongodb.casbah.query.Imports.{DBObject, MongoDBObject}
import com.mongodb.casbah.MongoCollection
import play.api.Play.current
import play.api.Play
import com.novus.salat.{Context, TypeHintFrequency, StringTypeHintStrategy}

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 23:28
 */


trait Model[T] {
  // Customize Salat context
  implicit val ctx = new Context {
    val name = "play-context"
    val c = new Context {
      val name = "play-context"
      override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary)
    }
    c.registerClassLoader(Play.classloader)
    c
  }

  protected def fromDb(dbObject: DBObject): T

  protected def toDb(t: T): DBObject

  protected lazy val coll: MongoCollection = null

  def save(t: T): ObjectId

  def removeById(id: ObjectId) = coll.remove(MongoDBObject("_id" -> id))

  def all: Seq[T] = coll.find().map(fromDb).toSeq

  def byId(id: ObjectId): Option[T] = coll.findOneByID(id).map(fromDb)

  def removeAll() = coll.dropCollection()

}
