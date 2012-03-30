import play.api._
import models._
import com.mongodb.casbah.commons.MongoDBObject

object Global extends GlobalSettings {

  def createPost {
    Post.collection.drop()
    
    val comment:Comment = Comment(content = "Comment's content")
    Comment.save(comment)
    
    for (i <- 1 to 1) {
      Post.save(Post(title = "titre " + i, featured = 7.equals(i), content = Some("content " + i), comments = List(comment)))
    }
  }

  def createUser {
    User.collection.drop()
    User.save(User(email = "user@b.com", username = "user", password = "user"))
  }

  override def onStart(app: Application) {
    Logger.info("Application start")
    if (app.mode == Mode.Dev) {
      createUser
      createPost
    }

    User.find(ref = MongoDBObject("admin" -> true)).limit(1).hasNext match {
      case false => User.save(User(email = "admin@b.com", username = "admin", password = "admin", admin = true))
      case _     =>
    }
  }

  override def onStop(app: Application) {
    Logger.info("Application stop")
  }

}