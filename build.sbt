
lazy val commonSettings = Seq(
  name := """MyToolBox""",
  organization := "hiroki",
  version :="1.0-SNAPSHOT",
  scalaVersion := "2.12.4",
  resolvers += Resolver.sonatypeRepo("releases"),
  libraryDependencies ++= Seq(
    guice,
    "com.typesafe.play" %% "play-slick" % "4.0.0",
    "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
    "com.danielasfregola" %% "twitter4s" % "5.5",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
  )
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
    .settings(
      commonSettings
    )

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
