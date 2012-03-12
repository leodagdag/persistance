package models

import org.specs2.mutable.Specification
import play.api.test.FakeApplication
import play.api.test.Helpers.running
import plugin.MongoDBPlugin
import play.api.Play.current
import org.bson.types.ObjectId
import java.io._
import java.security.MessageDigest
import com.mongodb.casbah.gridfs.{GridFSDBFile, GridFS, GridFSInputFile}
import scala.Some

/**
 * User: LeoDagDag
 * Date: 10/03/12
 * Time: 17:17
 */

class UploadFileSpec extends Specification {
  def logo_fh = new FileInputStream("public/images/test/thinking.jpg")

  def logo_bytes = {
    val data = new Array[Byte](logo_fh.available())
    logo_fh.read(data)
    data
  }
  def logo = new ByteArrayInputStream(logo_bytes)

  lazy val digest = MessageDigest.getInstance("MD5")
  digest.update(logo_bytes)
  lazy val logo_md5 = digest.digest().map("%02X" format _).mkString.toLowerCase()



  "UploadFile" should {
    "uploaded manual" in {
      running(FakeApplication()) {
        val gridfs: GridFS = MongoDBPlugin.getGridFS()
        val logo: FileInputStream = new FileInputStream("public/images/test/thinking.jpg")
        val file: GridFSInputFile = gridfs.createFile(logo)
        file.filename = "thinking.jpg"
        file.contentType = "image/jpg"
        file.put("key", "value")
        file.save

        var id: ObjectId = file.id.asInstanceOf[ObjectId]
        var newFile: GridFSDBFile = gridfs.find(id)
        newFile must haveClass[GridFSDBFile]
        file.id must haveClass[ObjectId]
        file.get("key") must equalTo(Some("value"))
      }
    }

    "upload via Plugin" in {
      running(FakeApplication()) {
        val logo: File = new File("public/images/test/thinking.jpg")
        var id = MongoDBPlugin.createNewFile(logo, Map("key1" -> "value1"))
        var file = MongoDBPlugin.getGridFS().find(id)
        file.get("key1") must  equalTo(Some("value1"))
        id must haveClass[ObjectId]
      }
    }
  }
}
