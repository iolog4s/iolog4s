---
layout: docs
title: MDC
---

Simply use the overloads that take variable number of `(String, String)` tuples. And configure your logging via the `logback.xml` file to display `mdc`.

## Example

### config file

```xml
<configuration>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <!-- encoders are assigned the type
             ch.qos.logback.classic.encoder.PatternLayoutEncoder by default -->
        <encoder>
            <pattern>%-5level [%mdc] %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="debug">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>
```

### scala program

```scala
import cats.effect.IO
import org.iolog4s.Logger
import org.scalatest.FunSpec

object ErrorLoggerTest extends App {
  val logger: Logger[IO] = Logger.create[IO]

  val logs: IO[Unit] = for {
    _ <- logger.info("structured" -> "logging", "supported" -> "yes")("test-info-context")
    _ <- logger.error("hello" -> "world")("test-error-with-context")
  } yield ()

  logs.unsafeRunSync()
}
```

### output

```
INFO  [structured=logging, supported=yes] org.iolog4s_test.ErrorLoggerTest - test-info-context
ERROR [hello=world] org.iolog4s_test.ErrorLoggerTest - test-error-with-context
```
