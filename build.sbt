import Dependencies._

ThisBuild / scalaVersion := "2.12.10"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "io.github"
ThisBuild / organizationName := "redbeeconf"

lazy val root = (project in file("."))
  .settings(
    name := "akka-streams-demo"
  )

val AkkaVersion     = "2.6.14"
val postgresVersion = "42.2.2"
val circeVersion    = "0.13.0"

libraryDependencies ++= Seq(
  // akka streams
  "com.typesafe.akka"  %% "akka-stream"               % AkkaVersion,
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "2.0.2",
  // de acá en adelante, son dependencias de soporte para el ejemplo real world
  "ch.qos.logback"       % "logback-classic" % "1.2.3",
  "io.circe"             %% "circe-core" % circeVersion,
  "io.circe"             %% "circe-generic" % circeVersion,
  "io.circe"             %% "circe-parser" % circeVersion,
  "io.circe"             %% "circe-generic-extras" % circeVersion,
  "com.typesafe.slick"   %% "slick-hikaricp" % "3.3.3",
  "com.typesafe.slick"   %% "slick" % "3.3.3",
  "org.postgresql"       % "postgresql" % postgresVersion,
  "com.github.javafaker" % "javafaker" % "1.0.2",
  scalaTest              % Test
)
