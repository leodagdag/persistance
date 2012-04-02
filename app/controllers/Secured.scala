package controllers

import play.api.Logger
import play.api.mvc._
import models.User

/**
 * Provide security features
 */
trait Secured {

  /**
   * Retrieve the connected username.
   */
  private def username(request: RequestHeader):Option[String] = request.session.get("username")

  /**
   * Retrieve user in session from Database
   * @param request
   * @return Option[User]
   */
  implicit def user(implicit request: RequestHeader): Option[User] = {
    for (username <- request.session.get("username")) yield User.byUsername(username).get
  }

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = {
    Results.Redirect(routes.Application.login).flashing("url" -> request.path)
  }

  // --

  /**
   * Action for authenticated users.
   */
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = {

    Security.Authenticated(username, onUnauthorized) {
      user =>
        Action(request => f(user)(request))
    }
  }

  /*
  /**
   * Check if the connected user is a member of this project.
   */
  def IsMemberOf(project: Long)(f: => String => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
    if(Project.isMember(project, user)) {
      f(user)(request)
    } else {
      Results.Forbidden
    }
  }

  /**
   * Check if the connected user is a owner of this task.
   */
  def IsOwnerOf(task: Long)(f: => String => Request[AnyContent] => Result) = IsAuthenticated { user => request =>
    if(Task.isOwner(task, user)) {
      f(user)(request)
    } else {
      Results.Forbidden
    }
  }
*/
}