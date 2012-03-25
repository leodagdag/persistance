package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import salatctx._

case class Article(_id: ObjectId = new ObjectId,
                   title: String,
                   content: String = null)

object Article extends SalatDAO[Article, ObjectId](collection = DB.connection("Article"))