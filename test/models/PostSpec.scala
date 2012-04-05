package models

import org.specs2.mutable._
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.commons.Imports._
import com.mongodb.WriteConcern
import models._
import play.api.test.FakeApplication
import org.joda.time.DateTime
import utils._

class PostSpec extends Specification {

  com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers()
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()

  "with Salat" should {
    var savedId: ObjectId = null
    var deleteId: ObjectId = null
    "remove all" in {
      running(FakeApplication()) {
        Post.collection.drop()
        Post.count() mustEqual 0
      }
    }

    "create 1" in {
      running(FakeApplication()) {
        val post = Post(title = "titre", content = "content")
        Post.save(post)
        savedId = post._id
        savedId mustNotEqual null
      }
    }

    "find by criteria" in {
      running(FakeApplication()) {
        val newPost = Post.findOne(MongoDBObject("title" -> "titre"))
        newPost mustNotEqual None
      }
    }

    "findById" in {
      running(FakeApplication()) {
        val newPost = Post.findOneByID(savedId)
        newPost mustNotEqual None
      }
    }

    "create 12 Posts" in {
      running(FakeApplication()) {
        var post: Post = null
        (1 to 12).foreach {
          i =>
            post = Post(title = "titre" + i, content = "content " + i)
            Post.save(post)
            if (i == 12) {
              deleteId = post._id
            }
        }
        Post.count() mustEqual 13
      }
    }

    "find all" in {
      running(FakeApplication()) {
        val all = Post.find(MongoDBObject()).toList
        all.size mustEqual 13
      }
    }

    "count" in {
      running(FakeApplication()) {
        Post.count() mustEqual 13
      }
    }

    "find by page" in {
      running(FakeApplication()) {
        implicit val dao = Post
        Post.byPage(0).size mustEqual Post.byPage(1).size
        Post.byPage(1).size mustEqual 10
        Post.byPage(2).size mustEqual 3
        Post.byPage(3).size mustEqual 0
      }
    }

    "update" in {
      running(FakeApplication()) {
        Post.findOneByID(savedId).get.title mustNotEqual "new Title"
        val newPost = Post.findOneByID(savedId).get
        val cr = Post.update(MongoDBObject("_id" -> savedId), newPost.copy(title = "new Title"), false, false, Post.collection.writeConcern)
        cr mustEqual()
        Post.findOneByID(savedId).get.title mustEqual "new Title"
      }
    }

    "add a comment" in {
      running(FakeApplication()) {
        var comment = new Comment(created = new DateTime(), content = "new comment")
        Post.addComment(savedId, comment)
        var post = Post.findOneByID(savedId).get
        post.comments.size mustEqual 1
      }
    }

    "remove by Id" in {
      running(FakeApplication()) {
        Post.removeById(deleteId)
        Post.findOneByID(deleteId) mustEqual None
        Post.count() mustEqual 12
      }
    }

    "add a comment generate EntityNotFoundException" in {
      running(FakeApplication()) {
        var comment = new Comment(created = new DateTime(), content = "new comment")
        Post.addComment(deleteId, comment) must throwAn[Exception]
      }
    }

  }
}