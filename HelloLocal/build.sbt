name := "HelloLocal"

version := "1.0"

scalaVersion := "2.10.6"

//resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.akka" %% "akka-remote" % "2.3.11",
  "io.kamon" %% "kamon-core" % "0.6.0",
  "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.11",
  "redis.clients" % "jedis" % "2.9.0"
)
