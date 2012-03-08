import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "persistance"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
      "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
