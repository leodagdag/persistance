package models

import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._
import utils._

trait Model[ObjectType <: AnyRef, K <: Any] {

  val PAGE_SIZE: Int = 10

  def byPage(num: Int)(implicit dao: SalatDAO[ObjectType, K]): List[ObjectType] = {
    var skip: Int = 0
    num match {
      case 0 => skip = 0
      case _ => skip = (num - 1) * PAGE_SIZE
    }
    dao.find(MongoDBObject()).skip(skip).limit(PAGE_SIZE).toList
  }

  def all(implicit dao: SalatDAO[ObjectType, ObjectId]): List[ObjectType] = {
    dao.find(MongoDBObject.empty).toList
  }

  def update[A <: DBObject](q: A,o: ObjectType)(implicit dao: SalatDAO[ObjectType, K]) {
    dao.update(q,o, false, false, dao.collection.writeConcern)
  }

}