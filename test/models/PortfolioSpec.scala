package models

import org.specs2.mutable.Specification
import play.api.test.Helpers.running
import play.api.test.FakeApplication
import org.bson.types.ObjectId
import java.io.File

/**
 * Created by IntelliJ IDEA.
 * User: leodagdag
 * Date: 21/03/12
 * Time: 12:07
 */
 /*
class PortfolioSpec extends Specification {

  val FILE_NAME: String = "thinking.jpg"
  val FILE_PATH: String = "public/images/test/" + FILE_NAME
  val FILE_CONTENT_TYPE = "image/jpeg"
  var allPortfolio: Seq[Portfolio] = List()

  "Portfolios Model" should {
    "removeAll" in {
      running(FakeApplication()) {
        Portfolio.removeAll()
        Portfolio.all.nonEmpty must equalTo(false)
      }
    }
    "create" in {
      running(FakeApplication()) {
        var media = new Media("Media 1")
        val mediaId = Media.save(media,new File(FILE_PATH), null)
        media = Media.byId(mediaId).get

        val portfolio = new Portfolio("Portfolio 1")
        Portfolio.addMedia(portfolio, List(media))
        Portfolio.addMedia(portfolio, List(media))
        portfolio.medias.size must equalTo(2)

        val id: ObjectId = Portfolio.save(portfolio)
        var newPortfolio = Portfolio.byId(id).get
        newPortfolio must not equalTo (null)
        newPortfolio.medias.size must equalTo(2)
      }
    }
    "all" in {
      running(FakeApplication()) {
        allPortfolio = Portfolio.all
        allPortfolio.nonEmpty must equalTo(true)
      }
    }
  }
}
*/
