name := """vagas"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  filters,
  "mysql" % "mysql-connector-java" % "5.1.27",
  "org.xerial" % "sqlite-jdbc" % "3.8.7",
  "com.google.guava" % "guava" % "14.0"
)
