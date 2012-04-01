package models

import org.bson.types.ObjectId
import utils.ModuleType

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 22:28
 */

case class AppConfig(
                      var name: String,
                      var logoId: Option[ObjectId] = None,
                      var modules: List[ModuleType] = Nil,
                      val _id: Option[ObjectId] = None
                      )

/*
object AppConfig extends Model[AppConfig] {

  override lazy val coll = MongoDBPlugin.getCollection("AppConfig")

  protected def fromDb(dbObject: DBObject): AppConfig = grater[AppConfig].asObject(dbObject)

  protected def toDb(appConfig: AppConfig): DBObject = grater[AppConfig].asDBObject(appConfig)

  def save(t: AppConfig): ObjectId = null

  def insert(t: AppConfig): ObjectId = null

  def update(t: AppConfig) = null
}
*/