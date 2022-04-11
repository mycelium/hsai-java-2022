name := "funsets"

scalaVersion := "2.13.8"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-optimise"
)

fork := true

javaOptions += "-Xmx2G"

parallelExecution in Test := false
