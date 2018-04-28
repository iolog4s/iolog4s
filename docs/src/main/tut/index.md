---
layout: home
---
# iolog4s

[![Maven Central](https://img.shields.io/maven-central/v/org.iolog4s/iolog4s_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/org.iolog4s/iolog4s_2.12/badge.svg)
[![Sonatype](https://img.shields.io/nexus/r/https/oss.sonatype.org/org.iolog4s/iolog4s_2.12.svg)](https://oss.sonatype.org/#nexus-search;quick~iolog4s_2.12)
[![Scala](https://img.shields.io/badge/scala-2.12.5-brightgreen.svg)](https://github.com/scala/scala/releases/tag/v2.12.5)

-------------------------

Avaialble for scala versions `2.11`, and `2.12`.

`iolog4s` is a logging library for scala that suspends all your logging side-effects into your chosen `cats.effect.Sync` instance, e.g.:
 * [`cats.effect.IO`](https://github.com/typelevel/cats-effect)
 * [`monix.eval.Task`](https://github.com/monix/monix)

`iolog4s` has the same interface as [`log4s`](https://github.com/Log4s/log4s), except all return types are of type `F[Unit]` where `F[_]: Sync`.

## usage
See [Getting started](docs/index.html)

## SBT module ids

```scala
lazy val iolog4s = "iolog4s.org" %% "iolog4s" % "0.0.2"
```

## Credit

The vast majority of the development effort goes to the authors of [log4s](https://github.com/Log4s/log4s), as the code here is mostly altered versions of their macros. So please extend all your thanks to them! It also means that you can expect the same reliability.

### Maintainers

Active maintainers:
* [@lorandszakacs](https://github.com/lorandszakacs)
