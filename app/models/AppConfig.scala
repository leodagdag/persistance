package models

import org.bson.types.ObjectId

import com.mongodb.casbah.Imports.WriteConcern
import com.mongodb.casbah.query.Imports.MongoDBObject
import com.mongodb.casbah.query.Imports.wrapDBObj
import play.api.Play.current
import plugin.MongoDBPlugin
import com.mongodb.casbah.commons.TypeImports._
import com.novus.salat._
import utils.ModuleType

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 22:28
 */

case class AppConfig(
                      name: String,
                      logo: Option[ObjectId] = None,
                      modules: List[ModuleType],
                      _id: Option[ObjectId] = None
                      )

object AppConfigRepository  extends Model[AppConfig] {
  private val appconfigs = MongoDBPlugin.getCollection("AppConfig")

  def removeById(id: ObjectId) = null

  def all = null

  def byId(id: ObjectId) = null

  def getCollection() = MongoDBPlugin.getCollection("AppConfig")

  def save(t: AppConfig): ObjectId = null

  protected def fromDb(dbObject: DBObject): AppConfig = grater[AppConfig].asObject(dbObject)

  protected def toDb(appConfig: AppConfig): DBObject = grater[AppConfig].asDBObject(appConfig)
}