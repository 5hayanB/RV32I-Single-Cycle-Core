ThisBuild / scalaVersion     := "2.12.15"
ThisBuild / version          := "0.1.0"

lazy val root = (project in file("."))
  .settings(
    name := "RISC-V Single Cycle Core",
    libraryDependencies ++= Seq(
      "edu.berkeley.cs" %% "chisel3" % "3.5.0-RC1",
      "edu.berkeley.cs" %% "chiseltest" % "0.5.0-RC1" % "test"
    ),
    scalacOptions ++= Seq(
      "-Xsource:2.11",
      "-language:reflectiveCalls",
      "-deprecation",
      "-feature",
      "-Xcheckinit",
      // Enables autoclonetype2 in 3.4.x (on by default in 3.5)
      "-P:chiselplugin:useBundlePlugin"
    ),
    addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % "3.5.0-RC1" cross CrossVersion.full),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  )
