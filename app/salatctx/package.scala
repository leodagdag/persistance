import com.novus.salat.Context
import play.api.Play.current
import play.api.Play
import com.mongodb.casbah.MongoConnection
import play.api.Logger
import plugin.MongoDBPlugin

package object salactx {
/**
   * Here is where we define the custom Play serialization context, including the Play classloader.
   */
  implicit val ctx = {
    val c = new Context {
      val name = "play-context"
    }
    c.registerClassLoader(Play.classloader)
    Logger.debug("New context registration done!")
    c
  }
  
  object DB {
    //val connection = MongoConnection()("dev")
    val connection = MongoDBPlugin.connection
    
  }
}