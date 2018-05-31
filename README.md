# iolog4s
[![Maven Central](https://img.shields.io/maven-central/v/org.iolog4s/iolog4s_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/org.iolog4s/iolog4s_2.12/badge.svg)
[![Sonatype](https://img.shields.io/nexus/r/https/oss.sonatype.org/org.iolog4s/iolog4s_2.12.svg)](https://oss.sonatype.org/#nexus-search;quick~iolog4s_2.12)
[![Scala](https://img.shields.io/badge/scala-2.11.12-brightgreen.svg)](https://github.com/scala/scala/releases/tag/v2.11.12)
[![Scala](https://img.shields.io/badge/scala-2.12.6-brightgreen.svg)](https://github.com/scala/scala/releases/tag/v2.12.6)

-------------------------
# Deprecation notice!

See entire [docs](http://iolog4s.org/iolog4s/) at microsite.

`iolog4s` is no longer maintained. It has been completely integrated into `log4cats` as of its version [`0.0.5`](https://github.com/ChristopherDavenport/log4cats/blob/master/CHANGELOG.md#new-and-noteworthy-for-version-005). I highly recommend you use that instead.

The reason behind this is quite simple, both `iolog4s` and `log4cats` have appeared on the scene at roughly the same time, and we decided to merge the effort of creating a logging library for the typelevel ecosystem.

## about

`iolog4s` is a logging library for scala that suspends all your logging side-effects into your chosen `cats.effect.Sync` instance, e.g.:
 * [`cats.effect.IO`](https://github.com/typelevel/cats-effect)
 * [`monix.eval.Task`](https://github.com/monix/monix)

`iolog4s` has the same interface as [`log4s`](https://github.com/Log4s/log4s), except all return types are of type `F[Unit]` where `F[_]: Sync`.

## super-quick start

Avaialble for scala versions `2.11`, and `2.12`.

```sbt
"org.iolog4s" %% "iolog4s" % "0.0.4"
```

You also need to depend on explicitly on a backend for logging, e.g.:
```sbt
"ch.qos.logback" % "logback-classic" % "1.2.3"
```