name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.12.6"

resolvers += Resolver.jcenterRepo

lazy val akkaVersion = "2.5.13"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,

  "com.github.dnvriend" %% "akka-persistence-jdbc" % "3.4.0",

  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,

  "org.iq80.leveldb"  % "leveldb" % "0.7",

  "mysql" % "mysql-connector-java" % "8.0.11",

  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)
