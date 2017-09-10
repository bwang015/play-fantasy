name := """play-fantasy"""
organization := "com.fantasy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  guice,
  "com.amazonaws" % "aws-java-sdk-dynamodb" % "1.11.175",
  "org.mockito" % "mockito-all" % "2.0.2-beta" % "test"
)
