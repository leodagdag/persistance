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
    Post.collection.drop
    var comments: List[Comment] = List.empty
    (1 to 10).foreach {
      i =>
        comments = comments :+ Comment(content = "Comment's content " + i, user = User.findOne(MongoDBObject("username" -> "user")).get)
    }
    comments.foreach(c => Comment.save(c))

    val id = User.findOne(MongoDBObject("username" -> "admin")).get._id
    (1 to 5).foreach {
      i =>
        Post.save(Post(title = "titre " + i, featured = 7.equals(i), content = Some("content " + i), authorId = id, comments = comments))
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