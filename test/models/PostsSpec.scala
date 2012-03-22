package models

import org.specs2.mutable.Specification
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import org.bson.types.ObjectId

class PostsSpec extends Specification {

  "Post Model" should {
    "removeAll" in {
      running(FakeApplication()) {
        Post.removeAll()
        Post.all.nonEmpty must equalTo(false)
      }
    }
    "created" in {
      running(FakeApplication()) {
        val title = "Titre"
        val post: Post = new Post(title)
        val id: ObjectId = Post.save(post)
        id must not equalTo (null)
      }
    }
    "all" in {
      running(FakeApplication()) {
        Post.all.size must equalTo(1)
      }
    }
    "update" in {
      running(FakeApplication()) {
        val newTitle = "new title"
        val list = Post.all
        list.nonEmpty must equalTo(true)
        val post = list.head
        val id = post._id.get
        post.title = newTitle
        Post.save(post)
        val updatedPost = Post.byId(id).get
        updatedPost.title must equalTo(newTitle)
      }
    }
    "still one" in {
      running(FakeApplication()) {
        Post.all.size must equalTo(1)
      }
    }
  }
 }