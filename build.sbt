import Dependencies._

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "io.github"
ThisBuild / organizationName := "serdeliverance"

lazy val root = (project in file("."))
  .settings(
    name := "akka-streams-demo"
  )

val AkkaVersion     = "2.6.14"
val postgresVersion = "42.2.2"

libraryDependencies ++= Seq(
  // log
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  // akka streams
  "com.typesafe.akka"  %% "akka-stream"               % AkkaVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.2",
  // DB
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.slick" %% "slick"          % "3.3.3",
  "org.postgresql"     % "postgresql"      % postgresVersion,
  // misc
  "com.github.javafaker" % "javafaker" % "1.0.2",
  scalaTest              % Test
)
