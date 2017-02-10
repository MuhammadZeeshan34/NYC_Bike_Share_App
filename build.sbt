name := """ZipyWebServices"""
organization := "com.feth"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa,
  "org.json" % "json" % "20160212",
  "org.hibernate" % "hibernate-entitymanager" % "5.0.1.Final",
  "mysql" % "mysql-connector-java" % "5.1.36",
  "com.stripe" % "stripe-java" % "1.38.0",
  "com.typesafe.play.modules" %% "play-modules-redis" % "2.4.0",
  "junit" % "junit" % "4.12" % "test",
  "com.google.maps" % "google-maps-services" % "0.1.9",
  "be.objectify" %% "deadbolt-java" % "2.4.2",
  // Comment the next line for local development of the Play Authentication core:
  "org.easytesting" % "fest-assert" % "1.4" % "test",
  "c3p0" % "c3p0" % "0.9.1.2"
)
libraryDependencies += filters
// to resolve the activator start issue
PlayKeys.externalizeResources := false
//
resolvers += "google-sedis-fix" at "http://pk11-scratch.googlecode.com/svn/trunk"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
