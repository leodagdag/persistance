package models

import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala._


/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 23:28
 */



trait Model[T] {

  RegisterJodaTimeConversionHelpers()

  protected def fromDb(dbObject: DBObject): T

  protected def toDb(t: T): DBObject

  protected lazy val coll: MongoCollection = null

  def all: Seq[T] = coll.find().map(fromDb).toSeq

  def byId(id: ObjectId): Option[T] = coll.findOneByID(id).map(fromDb)

  def save(t: T): ObjectId

  def removeById(id: ObjectId) = coll.remove(MongoDBObject("_id" -> id))

  def removeAll() = coll.dropCollection()

}







