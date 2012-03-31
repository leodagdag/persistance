package helpers

import models.User

/**
 * User: leodagdag
 * Date: 31/03/12
 * Time: 21:46
 */

object Template {
  def connected(user: Option[User]): Boolean = {
    user match {
      case Some(user) => true
      case None => false
    }
  }
}
