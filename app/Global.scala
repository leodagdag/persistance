import play.api._
import models._

object Global extends GlobalSettings {

  def createPost = {
    Post.collection.drop()
    for (i <- 1 to 12) {
     // var featured = false
      var featured = 7.equals(i)
      Post.save(new Post(title = "titre " + i, featured = featured, content = Some("content " + i)))

    }
  }

  override def onStart(app: Application) {
    Logger.info("Application start")
    if (app.mode == Mode.Dev) {
      createPost
    }
  }

  override def onStop(app: Application) {
    Logger.info("Application stop")
  }

}