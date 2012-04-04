package models

import _root_.salactx._
import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import org.joda.time._

case class Comment(created: DateTime = new DateTime(),
                   user: User,
                   content: String)

object Comment extends SalatDAO[Comment, ObjectId](collection = DB.connection("Comment")) with Model[Comment] {

}