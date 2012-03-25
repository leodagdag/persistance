package models

import org.specs2.mutable._
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import play.modules.mongodb.jackson.MongoDB

/**
  * User: leodagdag
  * Date: 22/03/12
  * Time: 22:50
  */

class PostSpec extends Specification {

  "Post Model" should {

    "removeAll" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        Post.deleteAll
        false mustEqual false
      }
    }

    "all empty" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        Post.all.size mustEqual 0
      }
    }

    "create 1 post" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        val post1 = Post(title = "Titre 1", author = "Auteur1", content = "Contenu 1")
        val result = Post.save(post1)
        val byAuthor = Post.findByAuthor("Auteur1")
        byAuthor mustNotEqual null
        byAuthor.size mustNotEqual 0
      }
    }

    "create 2 posts with same author" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        val post1 = Post(title = "Titre 1", author = "Auteur2", content = "Contenu 1")
        Post.save(post1)
        val post2 = Post(title = "Titre 2", author = "Auteur2", content = "Contenu 2")
        Post.save(post2)

        val byAuthor = Post.findByAuthor("Auteur2")
        byAuthor mustNotEqual null
        byAuthor.size mustEqual 2
      }
    }

    "all = 3" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        Post.all.size mustEqual 3
      }
    }

    "create 12 posts" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        for (i <- 1 to 12) {
          var post = Post(title = "Titre " + i, author = "Auteur2", content = "Contenu 1")
          Post.save(post)
        }
      }
    }

    "get by page" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        for (i <- 1 to 2) {
          var posts = Post.byPage(i)
          posts.size mustNotEqual 0
        }
      }
    }

  }

  trait Setup extends After {
    def fakeApp(o: Map[String, String]) = {
      FakeApplication(additionalConfiguration = Map("ehcacheplugin" -> "disabled",
        "mongodbJacksonMapperCloseOnStop" -> "disabled"))
    }

    def after {
    }
  }

}
