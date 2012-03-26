package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import salatctx._
import controllers.Blog

case class Post(_id: ObjectId = new ObjectId,
                title: String,
                content: String = null)

object Post extends SalatDAO[Post, ObjectId](collection = DB.connection("Post")) with Model[Post] {

  override val PAGE_SIZE = Blog.config.getInt("pageSize").getOrElse(10)

}