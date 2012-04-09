package models

import org.specs2.mutable._
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import com.mongodb.casbah.commons.Imports._
import models._
import java.io.File
import play.api.test.FakeApplication
import plugin.GridFSHelper
/*
class MediaSpec extends Specification {

  com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers()
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()

  "with Salat" should {
    var savedId: ObjectId = null
    var deleteId: ObjectId = null

    val fileName: String = "thinking.jpg"
    val filePath: String = "public/images/test/thinking.jpg"
    val FileContentPath = "image/jpeg"
    val key = "key1"
    val value = "value1"


    "remove all" in {
      running(FakeApplication()) {
        Media.collection.drop()
        Media.count() mustEqual 0
      }
    }

    "create 1" in {
      running(FakeApplication()) {
        val media = new Media(title = "titre")
        Media.save(media)
        savedId = media._id
        savedId mustNotEqual None
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
        var media: Media = null
        for (i <- 1 to 12) {
          media = new Media(title = "titre" + i, description = Some("description"))
          Media.save(media)
          if (i == 1) { deleteId = media._id }
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

    "count" in {
      running(FakeApplication()) {
        Media.count() mustEqual 13
      }
    }

    "find by page" in {
      running(FakeApplication()) {
        implicit val dao = Media
        Media.byPage(0) must haveTheSameElementsAs(Media.byPage(1))
        Media.byPage(1) must have size(10)
        Media.byPage(2) must have size(3)
        Media.byPage(3) must have size(0)
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
        Media.removeById(deleteId)
        Media.findOneByID(deleteId) mustEqual None
        Media.count() mustEqual 12
      }
    }

    "create with a File" in {
      running(FakeApplication()) {
        val file: File = new File(filePath)
        val fileId: ObjectId = GridFSHelper.createNewFile(file, Map(key -> value))
        val media = new Media(title = "titre", fileId = Some(fileId))
        Media.save(media)
        val newMedia = Media.findOneByID(media._id).get
        newMedia._id mustEqual media._id
        newMedia.fileId mustNotEqual None
      }
    }
    
    "update with a File" in {
      running(FakeApplication()){
        val file: File = new File(filePath)
        val fileId: ObjectId = GridFSHelper.createNewFile(file, Map(key -> value))
        val media = Media.findOneByID(savedId).get
        media.fileId = Some(fileId)
        Media.save(media)
        Media.findOneByID(savedId).get.fileId must beAnInstanceOf[Option[ObjectId]]
      }
    }

  }
}
*/