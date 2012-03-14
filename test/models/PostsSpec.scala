package models

import org.specs2.mutable.Specification
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import org.bson.types.ObjectId

class PostsSpec extends Specification {
  "Post Model" should {
    "removeAll" in {
      running(FakeApplication()) {
        PostRepository.removeAll()
        PostRepository.all.nonEmpty must equalTo(false)
      }
    }
    "created" in {
      running(FakeApplication()) {
        val title = "Titre"
        val post: Post = new Post(title)
        val id: ObjectId = PostRepository.save(post)
        println("id:[%s]".format(id))
        id must not equalTo (null)
      }
    }
    "all" in {
      running(FakeApplication()) {
        PostRepository.all.nonEmpty must equalTo(true)
      }
    }
    "update" in {
      running(FakeApplication()) {
        val newTitle = "new title"

        val list = PostRepository.all
        list.nonEmpty must equalTo(true)
        val post = list.head
        val id = post._id.get
        post.title = newTitle
        PostRepository.save(post)
        val updatedPost = PostRepository.byId(id).get
        updatedPost.title must equalTo(newTitle)
      }
    }
  }
}