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

class ArticleSpec extends Specification {

  "Article Model" should {

    "removeAll" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        Article.deleteAll
        false mustEqual false
      }
    }

    "create 2 articles with same author" in new Setup {
      implicit val app = fakeApp(Map.empty)
      running(app) {
        val article1 = Article(title = "Titre 1", author = "Auteur", content = "Contenu 1")
        Article.save(article1)
        val article2 = Article(title = "Titre 2", author = "Auteur", content = "Contenu 2")

        Article.save(article2)
        val byAuthor = Article.findByAuthor("Auteur")
        byAuthor.foreach(a => println(a))
        
        byAuthor mustNotEqual null
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
