// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// The Typesafe SNAPSHOT repository
resolvers += Resolver.url("Typesafe Ivy Snapshots Repository", url("http://repo.typesafe.com/typesafe/ivy-snapshots/"))(Resolver.ivyStylePatterns)

// Use for intellij
resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

// The Dust plugin
addSbtPlugin("com.typesafe" % "play-plugins-dust" % "1.1-SNAPSHOT")

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0")

// Use for intellij
addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.0.0")
