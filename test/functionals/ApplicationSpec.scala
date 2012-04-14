package functionals

import org.specs2.mutable.Specification
import play.api.test.Helpers._

import play.api.test.{FakeApplication, FakeRequest}

/**
 * User: leodagdag
 * Date: 13/03/12
 * Time: 21:53
 */
class ApplicationSpec extends Specification {
  "Application" should {
    "Action index" in {
      running(FakeApplication()) {
        val Some(result) = routeAndCall(FakeRequest(GET, "/"))

        status(result) must equalTo(OK)
        contentType(result) must beSome("text/html")
        charset(result) must beSome("utf-8")
      }
    }
  }
  
}
