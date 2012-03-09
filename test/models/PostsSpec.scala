package models

import org.specs2.mutable.Specification

import models._
import play.api.test.Helpers.running
import play.api.test.FakeApplication

class PostsSpec extends Specification {
	"Post Model" should {
		"created" in {
		  running(FakeApplication()){
		    var title = "Titre"
		    var post:Post = new Post(title)
		    post = PostRepository.save(post)
		    post.title must equalTo(title)
		    post._id must not equalTo(None)
		  }
		}
	}
}