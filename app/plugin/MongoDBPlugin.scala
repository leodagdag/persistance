package plugin

import play.api.Play._
import play.api._
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.MongoDB
import com.mongodb.casbah.MongoCollection
import play.api.Plugin

class MongoDBPlugin(app: Application) extends Plugin {

  def getCollection(name: String): MongoCollection = {
    mongoDB(name)
  }

  var mongoDB: MongoDB = _

  override def onStart() {
    val mongoConfig: Configuration = app.configuration.getConfig("mongodb").getOrElse(Configuration.empty)
    val seeds: String = mongoConfig.getString("seeds").getOrElse("localhost:27017")
    Logger.debug("seeds:[%s]".format(seeds))
    val dbName: String = mongoConfig.getString("db.name").getOrElse("dev")
    Logger.debug("db.name:[%s]".format(dbName))
    mongoDB = MongoConnection(seeds)(dbName)
    Logger.info("MongoDB connected")
  }
}

object MongoDBPlugin {

  private def error = throw new Exception(
    "There is no MongoDB plugin registered. Make sure at least one MongoPlugin implementation is enabled.")

  def getCollection(name: String)(implicit app: Application): MongoCollection = {
    if(Logger.isDebugEnabled) {
    	Logger.debug("getCollection(%s)".format(name))
    }
    app.plugin[MongoDBPlugin].map(_.getCollection(name)).getOrElse(error)
  }

}
