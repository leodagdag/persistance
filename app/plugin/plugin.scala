import com.novus.salat.Context
import play.api.Play.current
import play.api.Play
import play.api.Logger

/**
 * User: leodagdag
 * Date: 08/04/12
 * Time: 20:23
 */

package object plugin {
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
    val connection = MongoDBPlugin.connection
  }
}
