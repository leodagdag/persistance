package plugin

import play.api.Play._
import play.api.libs.MimeTypes
import play.api._
import play.api.Plugin
import org.bson.types.ObjectId
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.gridfs.Imports._
import com.novus.salat._

class MongoDBPlugin(app: Application) extends Plugin {

  val DEFAULT_HOST = "127.0.0.1:27017"

  def getCollection(name: String): MongoCollection = {
    mongoDB(name)
  }

  var mongoDB: MongoDB = _
  var gridFS: GridFS = _

  override def onStart() {

    val mongoConfig: Configuration = app.configuration.getConfig("mongodb").getOrElse(Configuration.empty)
    val seeds: String = mongoConfig.getString("servers").getOrElse(DEFAULT_HOST)
    Logger.debug("servers:[%s]".format(seeds))
    val dbName: String = mongoConfig.getString("database").getOrElse("dev")
    Logger.debug("database:[%s]".format(dbName))
    mongoDB = MongoConnection(seeds)(dbName)
    Logger.debug("MongoDB connected")
    gridFS = GridFS(mongoDB)
    Logger.debug("GridFS created")
  }
}

object MongoDBPlugin {

  private[plugin] def error = throw new Exception(
    "There is no MongoDB plugin registered. Make sure at least one MongoPlugin implementation is enabled.")

  private[plugin] def getGridFS()(implicit app: Application): GridFS = {
    Logger.debug("getGridFS()")
    app.plugin[MongoDBPlugin].map(_.gridFS).getOrElse(error)
  }

  def collection(name: String)(implicit app: Application): MongoCollection = {
    Logger.debug("getCollection(%s)".format(name))
    app.plugin[MongoDBPlugin].map(_.getCollection(name)).getOrElse(error)
  }

  def connection(implicit app: Application): MongoDB = {
    Logger.debug("connexion")
    app.plugin[MongoDBPlugin].map(_.mongoDB).getOrElse(error)
  }


}

object GridFSHelper {

  private lazy val gridFS: GridFS = MongoDBPlugin.getGridFS()
  // private lazy val gridFS: GridFS = MongoDBPlugin.getGridFS()

  def apply() = {
    gridFS
  }

  def createNewFile(file: java.io.File, params: Map[String, AnyRef] = Map()): ObjectId = {
    val newFile: GridFSInputFile = gridFS.createFile(file)
    newFile.filename = file.getName()
    newFile.contentType = MimeTypes.forFileName(file.getName()).getOrElse("")
    params foreach {
      case (key, value) => newFile.put(key, value)
    }
    newFile.save
    newFile.validate
    newFile.id.asInstanceOf[ObjectId]
  }
}




