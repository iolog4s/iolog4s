
lazy val root = Project(id = "effectful-log", base = file("."))
  .aggregate(core)

lazy val core = project
