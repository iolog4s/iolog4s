package org.iolog4s

import cats.effect.IO
import org.scalatest.FunSpec

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com
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
        _ <- logger.warn("test-warn")
        _ <- logger.error("test-error")

      } yield ()

      logs.unsafeRunSync()
    }

  }
}
