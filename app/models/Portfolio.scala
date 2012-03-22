package models

import com.mongodb.casbah.Imports._


/**
 * Created by IntelliJ IDEA.
 * User: leodagdag
 * Date: 21/03/12
 * Time: 12:02
 */

case class Portfolio(
                      var name: String,
                      var medias: List[Media] = List(),
                      val _id: Option[ObjectId] = None
                      )

/*
object Portfolio extends Model[Portfolio] {

  override lazy val coll = MongoDBPlugin.getCollection("Portfolio")

  protected def fromDb(dbObject: DBObject) = grater[Portfolio].asObject(dbObject)

  protected def toDb(portfolio: Portfolio) = grater[Portfolio].asDBObject(portfolio)

  def save(portfolio: Portfolio): ObjectId = {
    val dbo = toDb(portfolio)
    coll.save(dbo, WriteConcern.Safe)
    portfolio._id.getOrElse({
      val newId = dbo.as[ObjectId]("_id")
      portfolio.copy(_id = Some(newId))
      newId
    })
  }

  def addMedia(portfolio : Portfolio, medias : List[Media]):Unit = {
    portfolio.medias = portfolio.medias ::: medias
  }

}
*/