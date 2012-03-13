package models

import org.specs2.mutable.Specification
import play.api.test.FakeApplication
import play.api.test.Helpers.running
import plugin.{MongoDBPlugin,GridFSHelper}
import play.api.Play.current
import org.bson.types.ObjectId
import java.io.{File, FileInputStream, ByteArrayInputStream}
import java.security.MessageDigest
import com.mongodb.casbah.gridfs.{GridFSDBFile, GridFS, GridFSInputFile}
import scala.Some

/**
 * User: LeoDagDag
 * Date: 10/03/12
 * Time: 17:17
 */

class UploadFileSpec extends Specification {

  "UploadFile" should {
    val FILE_NAME: String = "thinking.jpg"
    val FILE_PATH: String = "public/images/test/thinking.jpg"
    val FILE_CONTENT_TYPE = "image/jpeg"
    val KEY = "key1"
    val VALUE= "value1"
    def logo_fh = new FileInputStream(FILE_PATH)

    def logo_bytes = {
      val data = new Array[Byte](logo_fh.available())
      logo_fh.read(data)
      data
    }
    def logo = new ByteArrayInputStream(logo_bytes)

    lazy val digest = MessageDigest.getInstance("MD5")
    digest.update(logo_bytes)
    lazy val logo_md5 = digest.digest().map("%02X" format _).mkString.toLowerCase


    "with apply" in {
      running(FakeApplication()) {
        val logo: File = new File(FILE_PATH)
        val id = GridFSHelper().apply(logo_bytes){ fh =>
          fh.filename = FILE_NAME
          fh.contentType = FILE_CONTENT_TYPE
        }
        GridFSHelper().findOne(FILE_NAME) must beSome[GridFSDBFile]
        var md5 = ""
        GridFSHelper().findOne(FILE_NAME) foreach { file =>
          md5 = file.md5
        }
        md5 must beEqualTo(logo_md5)
      }
    }
    "uploaded manual" in {
      running(FakeApplication()) {
        val logo: File = new File(FILE_PATH)
        val file: GridFSInputFile = GridFSHelper().createFile(logo)
        file.filename = FILE_NAME
        file.contentType = FILE_CONTENT_TYPE
        file.put(KEY, VALUE)
        file.save
        var md5 = ""
        GridFSHelper().findOne(file.filename) foreach { file =>
          md5 = file.md5
        }
        val id: ObjectId = file.id.asInstanceOf[ObjectId]
        val newFile: GridFSDBFile = GridFSHelper().find(id)
        newFile must haveClass[GridFSDBFile]
        file.id must haveClass[ObjectId]
        file.get(KEY) must equalTo(Some(VALUE))
      }
    }

    "upload via Plugin" in {
      running(FakeApplication()) {
        val logo: File = new File(FILE_PATH)
        val id = GridFSHelper.createNewFile(logo, Map(KEY -> VALUE))
        val file = GridFSHelper().find(id)
        file.get(KEY) must  equalTo(Some(VALUE))
        id must haveClass[ObjectId]
      }
    }
  }
}
