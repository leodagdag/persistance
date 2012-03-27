package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._

case class Media(_id: ObjectId = new ObjectId,
                 title: String,
                 content: Option(String) = None)

object Media extends SalatDAO[Media, ObjectId](collection = DB.connection("Media")) with Model[Media] {
  
	private lazy val postPictures = new ChildCollection[Picture, Int](collection = Picture.collection, parentIdField = "postId") {}
  
	def pictures(id: ObjectId) = this.postPictures.findByParentId(id).toList
}