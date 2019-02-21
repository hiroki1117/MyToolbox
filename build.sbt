name := """MyToolBox"""
organization := "hiroki"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies += guice
libraryDependencies += "postgresql" % "postgresql" % "9.1-901.jdbc4"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0"
libraryDependencies += "com.danielasfregola" %% "twitter4s" % "5.5"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

PlayKeys.playRunHooks += FrontBuildHook(baseDirectory.value)

lazy val prodFrontBuild = taskKey[Unit]("Front Build on production")
prodFrontBuild := {
  import scala.sys.process.Process
  val base = baseDirectory.value
  Process(FrontCommands.install, base / "front").!
  Process(FrontCommands.build, base / "front").!
}

stage := (stage dependsOn prodFrontBuild).value


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "hiroki.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "hiroki.binders._"
