name := "funsets"

scalaVersion := "2.12.7"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-optimise"
)

fork := true

javaOptions += "-Xmx2G"

parallelExecution in Test := false
