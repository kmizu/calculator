organization := "com.github.kmizu"

name := "calculator"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.2"

testOptions in Test += Tests.Argument("-oI")

scalacOptions ++= {
  Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions")
}

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.ow2.asm" % "asm" % "5.0.4",
  "junit" % "junit" % "4.7" % "test",
  "org.scalatest" %% "scalatest" %  "3.0.0"
)

assemblyJarName in assembly := "calculator.jar"

mainClass in assembly := Some("com.github.kmizu.calculator.Main")

initialCommands in console += {
  Iterator(
    "com.github.kmizu.calculator._"
  ).map("import "+).mkString("\n")
}
