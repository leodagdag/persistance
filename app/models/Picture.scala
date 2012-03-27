package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._

case class Picture(_id: ObjectId = new ObjectId,
    			postId: ObjectId,
				content: String) 

object Picture extends SalatDAO[Picture, ObjectId](collection = DB.connection("Picture")) with Model[Picture] {

}