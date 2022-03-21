name := "funsets"

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-optimise"
)

fork := true

javaOptions += "-Xmx2G"

parallelExecution in Test := false
