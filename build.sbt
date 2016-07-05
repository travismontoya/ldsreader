lazy val root = (project in file(".")).
    settings(
            name := "LDSReader",
            version := "1.0",
            scalaVersion := "2.11.8"
    )

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.5"
libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
