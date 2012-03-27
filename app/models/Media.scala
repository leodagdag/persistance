package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._

case class Media(_id: ObjectId = new ObjectId,
                 title: String,
                 fileId: Option[ObjectId] = None,
                 description: Option[String] = None)

object Media extends SalatDAO[Media, ObjectId](collection = DB.connection("Media")) with Model[Media] {
  
}