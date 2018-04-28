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
lazy val root = Project(id = "root-iolog4s", base = file("."))
  .disablePlugins(MicrositesPlugin)
  .disablePlugins(TutPlugin)
  .settings(CommonSettings.commonSettings)
  .settings(PublishingSettings.noPublishSettings)
  .aggregate(iolog4s)

lazy val iolog4s = project
  .disablePlugins(MicrositesPlugin)
  .disablePlugins(TutPlugin)
  .settings(CommonSettings.commonSettings)
  .settings(PublishingSettings.sonatypeSettings)
  .settings(
    libraryDependencies ++= Seq(
      Dependencies.scalaReflect % scalaVersion.value withSources (),
      Dependencies.cats,
      Dependencies.catsEffect,
      Dependencies.slf4jApi,
      Dependencies.logbackClassic,
      //
      Dependencies.scalaTest
    )
  )

lazy val docs = project
  .enablePlugins(MicrositesPlugin)
  .enablePlugins(TutPlugin)
  .disablePlugins(ScalafmtPlugin)
  .disablePlugins(ScalafixPlugin)
  .dependsOn(iolog4s)
  .settings(CommonSettings.commonSettings)
  .settings(PublishingSettings.noPublishSettings)
  .settings(DocsSettings.micrositeSettings)
