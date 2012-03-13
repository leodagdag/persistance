package models

import org.bson.types.ObjectId

import com.mongodb.casbah.Imports.WriteConcern
import com.mongodb.casbah.query.Imports.{DBObject,MongoDBObject,wrapDBObj}
import com.novus.salat.TypeHintFrequency
import com.novus.salat.grater
import com.novus.salat.Context
import com.novus.salat.StringTypeHintStrategy
import play.api.Play.current
import plugin.MongoDBPlugin
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

  def getCollection(): MongoCollection
  def removeById(id: ObjectId)
  def all: Seq[T]
  def byId(id: ObjectId): Option[T]
  def save(t: T): ObjectId
}
