package plugin

import play.api.i18n._
import models.User
import org.bson.types.ObjectId
import play.api.Configuration
import play.api.Play._
import org.joda.time.{Period, Duration, DateTime}
import org.joda.time.format._
import play.api.i18n.Lang

/**
 * User: leodagdag
 * Date: 31/03/12
 * Time: 21:46
 */

object Template {

  private lazy val pattern: String = current.configuration.getString("date.format").getOrElse("dd/MM/yyyy")

  private val suffix = Map("en" -> List(" year ", " years ", " month ", "months ", " day", " days ", "hour", " hours ", " minute", " minutes ", " seconde ", "secondes "),
    "fr" -> List(" année ", " années ", " mois ", " mois ", " jour ", " jours ", " heure ", " heures ", " minute ", " minutes ", " seconde ", " secondes "))



  private lazy val pf: PeriodFormatter = {
    val lang = Lang.defaultLang.language
    new PeriodFormatterBuilder()
      .printZeroRarelyLast()
      .appendYears()
      .appendSuffix(suffix.get(lang).get(0), suffix.get(lang).get(1))
      .appendMonths()
      .appendSuffix(suffix.get(lang).get(2), suffix.get(lang).get(3))
      .appendDays()
      .appendSuffix(suffix.get(lang).get(4), suffix.get(lang).get(5))
      .appendHours()
      .appendSuffix(suffix.get(lang).get(6), suffix.get(lang).get(8))
      .appendMinutes()
      .appendSuffix(suffix.get(lang).get(8), suffix.get(lang).get(9))
      .appendSeconds()
      .appendSuffix(suffix.get(lang).get(10), suffix.get(lang).get(11))
      .toFormatter()
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

  def since(date: DateTime): String = {
    val per: Period = new Period(date, DateTime.now())
    pf.print(per)
  }
}
