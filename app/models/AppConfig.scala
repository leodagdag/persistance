package models

import org.bson.types.ObjectId

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
                      var name: String,
                      var logo: Option[ObjectId] = None,
                      var modules: List[ModuleType],
                      val _id: Option[ObjectId] = None
                      )

object AppConfigRepository extends Model[AppConfig] {

  override lazy val coll = MongoDBPlugin.getCollection("AppConfig")

  protected def fromDb(dbObject: DBObject): AppConfig = grater[AppConfig].asObject(dbObject)

  protected def toDb(appConfig: AppConfig): DBObject = grater[AppConfig].asDBObject(appConfig)

  def save(t: AppConfig): ObjectId = null
}
