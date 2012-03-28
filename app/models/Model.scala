package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import utils._

trait Model[T <: AnyRef] {

  val PAGE_SIZE: Int = 10

  def byPage(num: Int)(implicit dao: SalatDAO[T, ObjectId]): List[T] = {
    var skip: Int = 0
    num match {
      case 0 => skip = 0
      case _ => skip = (num - 1) * PAGE_SIZE
    }
    dao.find(MongoDBObject()).skip(skip).limit(PAGE_SIZE).toList
  }
  
  def all(implicit dao: SalatDAO[T, ObjectId]): List[T] = {
    dao.find(MongoDBObject.empty).toList
  }
}