package models


import org.specs2.mutable._
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.Imports._
import com.mongodb.WriteConcern
import models._

class MediaSpec  extends Specification {

  com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers()
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()

  
  
  "with Salat" should {
    var savedId: ObjectId = null
    
    
    "remove all" in {
      running(FakeApplication()) {
        Media.collection.drop()
        Media.count() mustEqual 0
      }
    }

    "create 1" in {
      running(FakeApplication()) {
        val media = new Media(title = "titre", content = "content")
        Media.save(media)
        savedId = media._id
        savedId mustNotEqual null
      }
    }

    "find by criteria" in {
      running(FakeApplication()) {
        val newPost = Media.findOne(MongoDBObject("title" -> "titre"))
        newPost mustNotEqual None
      }
    }

    "findById" in {
      running(FakeApplication()) {
        val newPost = Media.findOneByID(savedId)
        newPost mustNotEqual None
      }
    }

    "create 12 Posts" in {
      running(FakeApplication()) {
        for(i <- 1 to 12) {
          Media.save(new Media(title = "titre" + i, content = "content"))
        }
        Media.count() mustEqual 13
      }
    }
    
    "find all" in {
      running(FakeApplication()) {
        val all = Media.find(MongoDBObject()).toList
        all.size mustEqual 13
      }
    }

    "count" in  {
       running(FakeApplication()) {
         Media.count() mustEqual 13
       }
    }
    
    "find by page" in  {
    	 running(FakeApplication()) {
    	   implicit val dao = Media
    	   Media.byPage(0).size mustEqual Media.byPage(1).size
    	   Media.byPage(1).size mustEqual 10
    	   Media.byPage(2).size mustEqual 3
    	   Media.byPage(3).size mustEqual 0
    	 }
    }
    
    "update" in {
      running(FakeApplication()) {
        val newPost = Media.findOneByID(savedId).get
        val cr = Media.update(MongoDBObject("_id" -> savedId), newPost.copy(title = "new Title"), false, false, Media.collection.writeConcern)
        cr mustEqual ()
        Media.findOneByID(savedId).get.title mustEqual "new Title"
      }
    }
    
    "remove by Id" in {
     running(FakeApplication()) {
       Media.removeById(savedId)
       Media.findOneByID(savedId) mustEqual None
       Media.count() mustEqual 12
     }
    }

  }
}