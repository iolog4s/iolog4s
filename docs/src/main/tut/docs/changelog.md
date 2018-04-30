---
layout: docs
title: Changelog
---

-----------------------------------

# 0.0.3 (April 29th 2018)

- remove `logback-classic` as a dependency
  - using it only in test now
  - this allows users of `iolog4s` to easily specify their logging backend w/out creating serious problems when creating fat jars and whatnot
  - updated docs to reflect the need to explicitly depend on a backend

### Contributors:
[@lorandszakacs](https://github.com/lorandszakacs)

-----------------------------------

# 0.0.2 (April 29th 2018)

Added:
- microsite available at [https://iolog4s.github.io/iolog4s/](https://iolog4s.github.io/iolog4s/)
- fixed signature of `Logger.getLogger` from `def create[F[_]]: org.iolog4s.Logger[F]` to `def create[F[_]: Sync]: org.iolog4s.Logger[F]` to force searching for a `Sync` typeclass sooner. Thus yielding better error messages when attempted to be used with something that has no `Sync`
- loosened access mods in `org.iolog4s.Logger` because macro code would be generated that had no access to the `F: Sync[F[_]]` field. Thus making logger unusable outside of the `org.iolog4s` package.

### Contributors:
[@lorandszakacs](https://github.com/lorandszakacs)
[@oleg-py](https://github.com/oleg-py)

-----------------------------------

# 0.0.1 (April 25th 2018)

First working version of a macro-based pure-FP logger for Scala. Depends on `cats-effects`.

Current state of the art:
```scala
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

### Contributors:
[@lorandszakacs](https://github.com/lorandszakacs)
