package models

import org.bson.types.ObjectId

import com.mongodb.casbah.query.Imports.{DBObject, MongoDBObject}
import com.novus.salat.TypeHintFrequency
import com.novus.salat.Context
import com.novus.salat.StringTypeHintStrategy
import com.mongodb.casbah.MongoCollection

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 23:28
 */


trait Model[T] {
  // Customize Salat context
  implicit val ctx = new Context {
    val name = "Custom Context"
    override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary)
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
