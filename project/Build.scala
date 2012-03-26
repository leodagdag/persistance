import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "persistance"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    //"net.vz.mongodb.jackson" % "play-mongo-jackson-mapper_2.9.1" % "1.0.0-rc.2",
    "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
    "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT"
  )

  val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA)
    .settings(
      lessEntryPoints <<= baseDirectory(customLessEntryPoints))
    .settings(
      // Sonatype repository
      resolvers += "Sonatype repository" at "https://oss.sonatype.org/content/repositories/releases/"
    )
      
  // Only compile the bootstrap bootstrap.less file and any other *.less file in the stylesheets directory
  def customLessEntryPoints(base: File): PathFinder = (
    (base / "app" / "assets" / "stylesheets" / "bootstrap" * "bootstrap.less") +++
    (base / "app" / "assets" / "stylesheets" / "bootstrap" * "responsive.less"))

}
