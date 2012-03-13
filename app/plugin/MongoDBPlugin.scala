package plugin

import play.api.Play._
import play.api._
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.MongoDB
import com.mongodb.casbah.MongoCollection
import libs.MimeTypes
import play.api.Plugin
import com.mongodb.casbah.gridfs.Imports._
import org.bson.types.ObjectId
import java.io.{File, FileInputStream}
import com.mongodb.casbah.gridfs.Imports

class MongoDBPlugin(app: Application) extends Plugin {

  var default_host = "localhost:27017"

  def getCollection(name: String): MongoCollection = {
    mongoDB(name)
  }

  var mongoDB: MongoDB = _
  var gridFS: GridFS = _

  override def onStart() {

    val mongoConfig: Configuration = app.configuration.getConfig("mongodb").getOrElse(Configuration.empty)
    val seeds: String = mongoConfig.getString("seeds").getOrElse(default_host)
    Logger.debug("seeds:[%s]".format(seeds))
    val dbName: String = mongoConfig.getString("db.name").getOrElse("dev")
    Logger.debug("db.name:[%s]".format(dbName))
    mongoDB = MongoConnection(seeds)(dbName)
    Logger.debug("MongoDB connected")
    gridFS = GridFS(mongoDB)
    Logger.debug("GridFS created")
  }
}

object MongoDBPlugin {

  private[plugin] def error = throw new Exception(
    "There is no MongoDB plugin registered. Make sure at least one MongoPlugin implementation is enabled.")

  def getCollection(name: String)(implicit app: Application): MongoCollection = {
    Logger.debug("getCollection(%s)".format(name))
    app.plugin[MongoDBPlugin].map(_.getCollection(name)).getOrElse(error)
  }


  private[plugin] def getGridFS()(implicit app: Application): GridFS = {
    Logger.debug("getGridFS()")
    app.plugin[MongoDBPlugin].map(_.gridFS).getOrElse(error)
  }
}

object GridFSHelper {

  private lazy val gridFS: Imports.GridFS = MongoDBPlugin.getGridFS()

  def apply() = {
    gridFS
  }
  def createNewFile(file: java.io.File, params: Map[String, AnyRef]): ObjectId = {
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