name := "DimensionsFeeds"

version := "0.1"

scalaVersion := "2.11.11"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.11.0.0"
libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.11.0.0"
libraryDependencies += "org.apache.kafka" % "connect-api" % "0.11.0.0"
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "0.11.0.0"

libraryDependencies += "com.google.protobuf" % "protobuf-java" % "3.4.0"
libraryDependencies += "com.google.guava" % "guava" % "19.0"

libraryDependencies += "org.apache.spark" % "spark-streaming_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.1.0"
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-sql-kafka-0-10_2.11" % "2.1.0"
libraryDependencies += "org.apache.spark" % "spark-hive_2.11" % "2.1.1" % "provided"

libraryDependencies += "log4j" % "log4j" % "1.2.17"

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"

libraryDependencies += "org.apache.bahir" % "spark-sql-cloudant_2.11" % "2.1.1"
libraryDependencies += "net.liftweb" % "lift-webkit_2.11" % "3.2.0-M2" % "provided"
libraryDependencies += "com.ibm.stocator" % "stocator" % "1.0.16"