package org.iolog4s_test

import cats.effect.IO
import org.iolog4s.Logger
import org.scalatest.FunSpec

/**
  *
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 25 Apr 2018
  *
  */
class ErrorLoggerTest extends FunSpec {
  private def test: ItWord = it

  describe("ERROR") {
    test("IO") {
      val logger: Logger[IO] = Logger.create[IO]

      val logs: IO[Unit] = for {
        _ <- logger.trace("test-trace")
        _ <- logger.debug("test-debug")
        _ <- logger.info("test-info")
        _ <- logger.info("structured" -> "logging", "supported" -> "yes")("test-info-context")
        _ <- logger.warn("test-warn")
        _ <- logger.error("hello" -> "world")("test-error-with-context")
        _ <- logger.error("test-error")

      } yield ()

      logs.unsafeRunSync()
    }

  }
}
