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
import sbt._

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com
  * @since 25 Apr 2018
  *
  */
object Dependencies {
  lazy val `scala_2.12`:     String      = "2.12.4"
  lazy val `scala_2.11`:     String      = "2.11.12"
  lazy val mainScalaVersion: String      = `scala_2.12`
  lazy val scalaVersions:    Seq[String] = Seq(`scala_2.12`, `scala_2.11`)

  lazy val scalaReflect = "org.scala-lang" % "scala-reflect"

  lazy val cats       = "org.typelevel" %% "cats-core"   % "1.1.0"    withSources ()
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "1.0.0-RC" withSources ()

  lazy val slf4jApi       = "org.slf4j"      % "slf4j-api"       % "1.7.25" withSources ()
  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"  withSources ()

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5" % Test withSources ()
}
