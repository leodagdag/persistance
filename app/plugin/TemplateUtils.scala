package plugin

import models.User
import com.mongodb.casbah.commons.Imports._
import play.api.Play._
import org.joda.time.{Period, DateTime}
import org.joda.time.format._
import play.api.i18n._

/**
 * User: leodagdag
 * Date: 31/03/12
 * Time: 21:46
 */

object TemplateUtils {


  private lazy val pattern: String = current.configuration.getString("date.format").getOrElse("dd/MM/yyyy")

  private def pf(implicit lan: Lang): PeriodFormatter = {
    new PeriodFormatterBuilder()
      .printZeroRarelyLast()
      .appendPrefix(Messages("since"))
      .appendYears()
      .appendSuffix(" " + Messages("since.year") + " ", " " + Messages("since.years") + " ")
      .appendMonths()
      .appendSuffix(" " + Messages("since.month") + " ", " " + Messages("since.months") + " ")
      .appendDays()
      .appendSuffix(" " + Messages("since.day") + " ", " " + Messages("since.days") + " ")
      .appendHours()
      .appendSuffix(" " + Messages("since.hour") + " ", " " + Messages("since.hours") + " ")
      .appendMinutes()
      .appendSuffix(" " + Messages("since.minute") + " ", " " + Messages("since.minutes") + " ")
      .appendSeconds()
      .appendSuffix(" " + Messages("since.second"), " " + Messages("since.seconds"))
      .toFormatter()
  }

  def pluralize(n: java.lang.Number): String = {
    if (n != 1) {
      "s"
    } else {
      ""
    }
  }

  def connected(user: Option[User]): Boolean = {
    user match {
      case Some(user) => true
      case None => false
    }
  }

  def getUser(id: ObjectId): User = {
    User.findOneByID(id).get
  }

  def formatDate(date: DateTime): String = {
    date.toString(pattern)
  }

  def since(date: DateTime)(implicit lan: Lang): String = {
    val per: Period = new Period(date, DateTime.now())
    pf.print(per).trim
  }
}
