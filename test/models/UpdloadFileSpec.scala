package models

import org.specs2.mutable.Specification
import play.api.test.FakeApplication
import play.api.test.Helpers.running
import plugin.MongoDBPlugin
import play.api.Play.current
import org.bson.types.ObjectId
import collection.mutable._
import java.util.Map
import java.util.HashMap
import com.mongodb.casbah.gridfs.{Imports, GridFSDBFile, GridFSInputFile}

/**
 * User: LeoDagDag
 * Date: 10/03/12
 * Time: 17:17
 */

class UpdloadFileSpec extends Specification {
  "UploadFile" should {
    "uploaded" in {
      running(FakeApplication()) {
        val gridFS: Imports.GridFS = MongoDBPlugin.getGridFS()
        val logo: java.io.FileInputStream = new java.io.FileInputStream("public/images/test/thinking.jpg")
        var id = gridFS.apply(logo) {
          (fh: GridFSInputFile) =>
            fh.filename = "thinking.jpg"
            fh.contentType = "image/jpg"
        }
        val file = MongoDBPlugin.getGridFS().find("thinking.jpg")
        file must not empty
        file.get("_id") must equal(id)

      }          
    }
  }
}
