import play.api._
import models._
import com.mongodb.casbah.commons.MongoDBObject

object Global extends GlobalSettings {

  def createUser {
    User.collection.drop()
    User.save(User(email = "admin@b.com", username = "admin", password = "admin", firstName = Some("user"), lastName = Some("admin"), admin = true))
    User.save(User(email = "user@b.com", username = "user", password = "user", firstName = Some("first"), lastName = Some("last")))
  }

  def createPost {
    Post.collection.drop()

    val comment: Comment = Comment(content = "Comment's content", user = User.findOne(MongoDBObject("username" -> "user")).get)
    Comment.save(comment)

    for (i <- 1 to 1) {
      Post.save(Post(title = "titre " + i, featured = 7.equals(i), content = Some("content " + i), comments = List(comment)))
    }
  }


  override def onStart(app: Application) {
    Logger.info("Application start")
    val admin = User.findOne(MongoDBObject("admin" -> true))
    admin match {
      case None => User.save(User(email = "admin@b.com", username = "admin", password = "admin", admin = true))
      case Some(admin) =>
    }
    if (app.mode == Mode.Dev) {
      createUser
      createPost
    }
  }

  override def onStop(app: Application) {
    Logger.info("Application stop")
  }

}