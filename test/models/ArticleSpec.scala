package models

import play.api.test.Helpers.running
import play.api.test.FakeApplication
import org.specs2.mutable._
import util.Random
import com.mongodb.Mongo

/**
 * User: leodagdag
 * Date: 22/03/12
 * Time: 22:50
 */

class ArticleSpec extends Specification {
  "Article Model" should {

    "removeAll" in new Setup {
      implicit val app = fakeApp()
      running(app) {
        Article.deleteAll
        false mustEqual false
      }
    }

    "create 2 articles with same author" in new Setup {
      implicit val app = fakeApp()
      running(app) {
        val article1 = Article(title = "Titre 1", author = "Auteur", content = "Contenu 1")
        Article.save(article1)
        val article2 = Article(title = "Titre 2", author = "Auteur", content = "Contenu 2")

        Article.save(article2)
        val byAuthor = Article.findByAuthor("Auteur")
        byAuthor mustNotEqual null
      }
    }
  }


  trait Setup  {
    def fakeApp() = {
      FakeApplication(additionalConfiguration = Map("ehcacheplugin" -> "disabled",
        "mongodbJacksonMapperCloseOnStop" -> "disabled"))
    }
  }
}
