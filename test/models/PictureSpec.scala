package models

import org.specs2.mutable._
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.Imports._
import com.mongodb.WriteConcern
import models._
/*
class PictureSpec extends Specification {

  com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers()
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()

  "with Salat" should {
    var savedId: ObjectId = null
    val postId: ObjectId = {
      running(FakeApplication()) {
        var post = new Post(title = "titre", content = "content")
        Post.save(post)
        post._id
      }
    }

    "remove all" in {
      running(FakeApplication()) {
        Picture.collection.drop()
        Picture.count() mustEqual 0
      }
    }

    "find 1 post and create 1 picture" in {
      running(FakeApplication()) {
        val picture = new Picture(postId = postId, content = "content")
        Picture.save(picture)
        savedId = picture._id
        Post.pictures(postId).size mustEqual 1
        savedId mustNotEqual null
      }
    }

    "find by criteria" in {
      running(FakeApplication()) {
        val newPost = Picture.findOne(MongoDBObject("content" -> "content"))
        newPost mustNotEqual None
      }
    }

    "findById" in {
      running(FakeApplication()) {
        val newPost = Picture.findOneByID(savedId)
        newPost mustNotEqual None
      }
    }

    "create 12 Posts" in {
      running(FakeApplication()) {
        for (i <- 1 to 12) {
          Picture.save(new Picture(postId = postId, content = "content " + i))
        }
        Picture.count() mustEqual 13
        Post.pictures(postId).size mustEqual 13
      }
    }

    "find all" in {
      running(FakeApplication()) {
        val all = Picture.find(MongoDBObject()).toList
        all.size mustEqual 13
      }
    }

    "count" in {
      running(FakeApplication()) {
        Picture.count() mustEqual 13
      }
    }

    "find by page" in {
      running(FakeApplication()) {
        implicit val dao = Picture
        Picture.byPage(0).size mustEqual Picture.byPage(1).size
        Picture.byPage(1).size mustEqual 10
        Picture.byPage(2).size mustEqual 3
        Picture.byPage(3).size mustEqual 0
      }
    }

    "update" in {
      running(FakeApplication()) {
        val newPost = Picture.findOneByID(savedId).get
        val cr = Picture.update(MongoDBObject("_id" -> savedId), newPost.copy(content = "new content"), false, false, Picture.collection.writeConcern)
        cr mustEqual ()
        Picture.findOneByID(savedId).get.content mustEqual "new content"
      }
    }

    "remove by Id" in {
      running(FakeApplication()) {
        Picture.removeById(savedId)
        Picture.findOneByID(savedId) mustEqual None
        Picture.count() mustEqual 12
        Post.pictures(postId).size mustEqual 12
      }
    }

  }
}
*/