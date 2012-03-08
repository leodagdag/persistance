package models
import org.bson.types.ObjectId
import com.mongodb.casbah.MongoConnection

case class Post(
  _id: Option[ObjectId] = None,
  title: String
)

object Post {
	private val posts = MongoConnection()
}