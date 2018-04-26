# iolog4s
[![Maven Central](https://img.shields.io/maven-central/v/org.iolog4s/org.iolog4s_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/org.iolog4s/org.iolog4s_2.12) [![Sonatype](https://img.shields.io/nexus/r/https/oss.sonatype.org/org.iolog4s/org.iolog4s_2.12.svg)](https://oss.sonatype.org/#nexus-search;quick~iolog4s_2.12)  [![Scala](https://img.shields.io/badge/scala-2.12.5-brightgreen.svg)](https://github.com/scala/scala/releases/tag/v2.12.5)

-------------------------

Avaialble for scala versions `2.11`, and `2.12`. SBT artifacts:

```
"iolog4s.org" %% "iolog4s" % "0.0.1-SNAPSHOT"
```

## about

`iolog4s` is a logging library for scala that suspends all your logging side-effects into your chosen `cats.effect.Sync` instance, e.g.:
 * [`cats.effect.IO`](https://github.com/typelevel/cats-effect)
 * [`monix.eval.Task`](https://github.com/monix/monix)

`iolog4s` has the same interface as [`log4s`](https://github.com/Log4s/log4s), except all return types are of type `F[Unit]` where `F[_]: Sync`.


## on implementation and copyright

The vast majority of the development effort goes to the authors of [log4s](https://github.com/Log4s/log4s), as the code here is mostly altered versions of their macros. So please extend all your thanks to them! It also means that you can expect the same reliability.

## example

```
object Main extends App {
  import org.iolog4s._
  import cats.effect.IO

  val logger: Logger[IO] = Logger.create[IO]

  val logs: IO[Unit] = for {
    _ <- logger.trace("test-trace")
    _ <- logger.debug("test-debug")
    _ <- logger.info("test-info")
    _ <- logger.warn("test-warn")
    _ <- logger.error("test-error")

  } yield ()

  logs.unsafeRunSync()

}
```

## what is to be done?

Support for `MDC` is still under development, stay tuned.