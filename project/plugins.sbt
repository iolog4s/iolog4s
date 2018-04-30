/**
  * Copyright (c) 2018 iolog4s project authors and contributors
  *
  * See project homepage at: http://iolog4s.org/
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

/**
  * Helps publish the artifacts to sonatype, which in turn
  * pushes to maven central
  *
  * https://github.com/xerial/sbt-sonatype
  */
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.3")

/**
  *
  * Signs all the jars, used in conjunction with sbt-sonatype.
  *
  * https://github.com/sbt/sbt-pgp
  */
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")

/**
  * build configured in ``project/ReleaseProcess``
  *
  * https://github.com/sbt/sbt-release
  */
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.8")

/**
  * The best thing since sliced bread.
  *
  * https://github.com/scalameta/scalafmt
  */
addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")

/**
  * Refactoring/linting tool for scala.
  *
  * https://github.com/scalacenter/scalafix
  * https://scalacenter.github.io/scalafix/
  *
  * From docs:
  * {{{
  *   // ===> sbt shell
  *
  *   > scalafixEnable                         // Setup scalafix for active session.
  *
  *   > scalafix                               // Run all rules configured in .scalafix.conf
  *
  *   > scalafix RemoveUnusedImports           // Run only RemoveUnusedImports rule
  *
  *   > myProject/scalafix RemoveUnusedImports // Run rule in one project only
  *
  * }}}
  */
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.6.0-M1")

/**
  * Used to build the documentation.
  *
  * https://github.com/47deg/sbt-microsites
  */
addSbtPlugin("com.47deg" % "sbt-microsites" % "0.7.18")

/**
  *
  * Used by sbt-microsites
  *
  * https://github.com/sbt/sbt-ghpages
  */
addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.6.2")

/**
  * https://github.com/scoverage/sbt-scoverage
  */
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
