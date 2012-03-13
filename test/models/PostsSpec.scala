package models

import org.specs2.mutable.Specification
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import org.bson.types.ObjectId

class PostsSpec extends Specification {
  "Post Model" should {
    "created" in {
      running(FakeApplication()) {
        val title = "Titre"
        var post: Post = new Post(title)
        val id: ObjectId = PostRepository.save(post)
        println("id:[%s]".format(id))
        post.title must equalTo(title)
        id must haveClass[ObjectId]
      }
    }
  }
}