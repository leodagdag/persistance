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

  private lazy val pf: PeriodFormatter = {

    val suffix: Map[Symbol,Map[Symbol,Tuple2[String,String]]] =
      """
      en=y:year,years|M:month,months|d:day,days|h:hour,hours|m:minute,minutes|s:seconde,secondes
      fr=y:année,années|M:mois,mois|d:jour,jours|h:heure,heures|m:minute,minutes|s:seconde,secondes
      """.split('\n').map(_.trim).filter(_.size > 0).map(_.split('=')).map {
        line: Array[String] => // ["en","y:year,years|M:month,months|d:day,days|h:hour,hours|m:minute,minutes|s:seconde,secondes"]
          Symbol(line(0)) -> line(1).split('|').map {
            period: String => // "y:year,years"
              Symbol(period(0).toString) -> period.drop(2).mkString
          }.toMap.map {
            words: Tuple2[Symbol, String] => // ('y,"year,years")(Symbol, String)
              val word: Array[String] = words._2.split(',').map(" " + _ + " ").asInstanceOf[Array[String]]
              words._1 -> Tuple2(word(0), word(1))
          }.toMap
      }.toMap

    val lang = Symbol(Lang.defaultLang.language)

    new PeriodFormatterBuilder()
      .printZeroRarelyLast()
      .appendPrefix("since ")
      .appendYears()
      .appendSuffix(suffix.get(lang).get('y)._1, suffix.get(lang).get('y)._2)
      .appendMonths()
      .appendSuffix(suffix.get(lang).get('M)._1, suffix.get(lang).get('M)._2)
      .appendDays()
      .appendSuffix(suffix.get(lang).get('d)._1, suffix.get(lang).get('d)._2)
      .appendHours()
      .appendSuffix(suffix.get(lang).get('h)._1, suffix.get(lang).get('h)._2)
      .appendMinutes()
      .appendSuffix(suffix.get(lang).get('m)._1, suffix.get(lang).get('m)._2)
      .appendSeconds()
      .appendSuffix(suffix.get(lang).get('s)._1, suffix.get(lang).get('s)._2)
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

  def since(date: DateTime): String = {
    val per: Period = new Period(date, DateTime.now())
    pf.print(per).trim
  }
}
