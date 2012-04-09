package models

import _root_.plugin._
import com.novus.salat.dao._
import com.mongodb.casbah.commons.Imports._

case class User(_id: ObjectId = new ObjectId,
                username: String,
                password: String,
                email: String,
                admin: Boolean = false,
                firstName: Option[String] = None,
                lastName: Option[String] = None,
                bio: Option[String] = None) {
  lazy val fullname = {
    val fullname = List(this.firstName.getOrElse(""), this.lastName.getOrElse("")).filter(!"".equals(_)).map(_.capitalize)

    fullname.isEmpty match {
      case false => fullname.mkString(" ").trim()
      case _ => ""
    }
  }
}

object User extends SalatDAO[User, ObjectId](collection = DB.connection("User")) with Model[User, ObjectId] {

  def authenticate(username: String, password: String): Option[User] = {
    this.findOne(MongoDBObject("username" -> username, "password" -> password))
  }

  def byUsername(username: String): Option[User] = {
    this.findOne(MongoDBObject("username" -> username))
  }

}