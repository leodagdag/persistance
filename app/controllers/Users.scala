package controllers

import models.User
import play.api.mvc.Session

/**
 * User: leodagdag
 * Date: 31/03/12
 * Time: 19:10
 */

trait Users {
  implicit def user(implicit session: Session): Option[User] = {
    for (username <- session.get("username")) yield User.byUsername(username)
  }

}
