package models

import org.specs2.mutable._
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.Imports._
import com.mongodb.WriteConcern
import models._
import play.api.test.FakeApplication
import play.api.test.FakeApplication

class ArticleSpec extends Specification {

  com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers()
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()

  "With Salat" should {
    var savedId: ObjectId = null
    
    
    "remove all" in {
      implicit val app = FakeApplication()
      running(app) {
        Article.collection.drop()
      }
    }

    "create one" in {
      implicit val app = FakeApplication()
      running(app) {
        val article = new Article(title = "titre", content = "content")
        Article.save(article)
        savedId = article._id
      }
    }

    "find by criteria" in {
      implicit val app = FakeApplication()
      running(app) {
        val newArticle = Article.findOne(MongoDBObject("title" -> "titre"))
        newArticle mustNotEqual None
      }
    }

    "findById" in {
      implicit val app = FakeApplication()
      running(app) {
        val newArticle = Article.findOneByID(savedId)
        newArticle mustNotEqual None
      }
    }

    "create multiple" in {
      implicit val app = FakeApplication()
      running(app) {
        for(i <- 1 to 12) {
          Article.save(new Article(title = "titre" + i, content = "content"))
        }
      }
    }
    
    "find all" in {
      implicit val app = FakeApplication()
      running(app) {
        val all = Article.find(MongoDBObject()).toList
        all.size mustNotEqual 0
      }
    }

    "update" in {
      implicit val app = FakeApplication()
      running(app) {
        val newArticle = Article.findOneByID(savedId).get
        val cr = Article.update(MongoDBObject("_id" -> savedId), newArticle.copy(title = "new Title"), false, false, Article.collection.writeConcern)
        cr mustEqual ()
        Article.findOneByID(savedId).get.title mustEqual "new Title"
      }
    }
    
    "remove by Id" in {
     implicit val app = FakeApplication()
     running(app) {
       Article.removeById(savedId)
       Article.findOneByID(savedId) mustEqual None
     }
    }

  }
}