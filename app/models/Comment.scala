package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import org.joda.time._
import salactx._

case class Comment(created: DateTime = new DateTime(),
  content: String)

object Comment extends SalatDAO[Comment, ObjectId](collection = DB.connection("Comment")) with Model[Comment] {

}