/**
  * Copyright 2013-2017 Sarah Gerweck
  * see: https://github.com/Log4s/log4s
  *
  * Modifications copyright (C) 2018 Lorand Szakacs
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
package org.iolog4s

import cats.effect.Sync

import language.experimental.macros
import org.slf4j.{Logger => JLogger}

object Logger {

  def create[F[_]]: org.iolog4s.Logger[F] = macro LoggerMacros.getLoggerImpl[F[_]]

  def create[F[_]: Sync](name: String): org.iolog4s.Logger[F] = new Logger(org.slf4j.LoggerFactory.getLogger(name))

  def create[F[_]: Sync](clazz: Class[_]): org.iolog4s.Logger[F] =
    new Logger(org.slf4j.LoggerFactory.getLogger(clazz))

  final val singletonsByName = true
  final val trailingDollar   = false

  sealed trait LevelLogger extends Any {
    def isEnabled: Boolean
    def apply(msg: => String): Unit
    def apply(t:   Throwable)(msg: => String): Unit
  }

  final class TraceLevelLogger private[iolog4s] (val logger: JLogger) extends AnyVal with LevelLogger {
    @inline def isEnabled = logger.isTraceEnabled
    @inline def apply(msg: => String) = if (isEnabled) logger.trace(msg)
    @inline def apply(t:   Throwable)(msg: => String) = if (isEnabled) logger.trace(msg, t)
  }

  final class DebugLevelLogger private[iolog4s] (val logger: JLogger) extends AnyVal with LevelLogger {
    @inline def isEnabled = logger.isDebugEnabled
    @inline def apply(msg: => String) = if (isEnabled) logger.debug(msg)
    @inline def apply(t:   Throwable)(msg: => String) = if (isEnabled) logger.debug(msg, t)
  }

  final class InfoLevelLogger private[iolog4s] (val logger: JLogger) extends AnyVal with LevelLogger {
    @inline def isEnabled = logger.isInfoEnabled
    @inline def apply(msg: => String) = if (isEnabled) logger.info(msg)
    @inline def apply(t:   Throwable)(msg: => String) = if (isEnabled) logger.info(msg, t)
  }

  final class WarnLevelLogger private[iolog4s] (val logger: JLogger) extends AnyVal with LevelLogger {
    @inline def isEnabled = logger.isWarnEnabled
    @inline def apply(msg: => String) = if (isEnabled) logger.warn(msg)
    @inline def apply(t:   Throwable)(msg: => String) = if (isEnabled) logger.warn(msg, t)
  }

  final class ErrorLevelLogger private[iolog4s] (val logger: JLogger) extends AnyVal with LevelLogger {
    @inline def isEnabled = logger.isErrorEnabled
    @inline def apply(msg: => String) = if (isEnabled) logger.error(msg)
    @inline def apply(t:   Throwable)(msg: => String) = if (isEnabled) logger.error(msg, t)
  }
}

final class Logger[F[_]: Sync](val logger: JLogger) {
  private[iolog4s] val F: Sync[F] = Sync[F]

  /** The name of this logger. */
  @inline def name = logger.getName

  @inline def isTraceEnabled: Boolean = logger.isTraceEnabled

  @inline def isDebugEnabled: Boolean = logger.isDebugEnabled

  @inline def isInfoEnabled: Boolean = logger.isInfoEnabled

  @inline def isWarnEnabled: Boolean = logger.isWarnEnabled

  @inline def isErrorEnabled: Boolean = logger.isErrorEnabled

  import Logger._

  /* These will allow maximum inlining if the type is known at compile time. */
  @inline def apply(lvl: Trace.type): TraceLevelLogger = new TraceLevelLogger(logger)
  @inline def apply(lvl: Debug.type): DebugLevelLogger = new DebugLevelLogger(logger)
  @inline def apply(lvl: Info.type):  InfoLevelLogger  = new InfoLevelLogger(logger)
  @inline def apply(lvl: Warn.type):  WarnLevelLogger  = new WarnLevelLogger(logger)
  @inline def apply(lvl: Error.type): ErrorLevelLogger = new ErrorLevelLogger(logger)

  def apply(level: LogLevel): LevelLogger = level match {
    case Trace => new TraceLevelLogger(logger)
    case Debug => new DebugLevelLogger(logger)
    case Info  => new InfoLevelLogger(logger)
    case Warn  => new WarnLevelLogger(logger)
    case Error => new ErrorLevelLogger(logger)
  }

  import LoggerMacros._

  def trace(t:   Throwable)(msg: String): F[Unit] = macro traceTM[F]
  def trace(msg: String): F[Unit] = macro traceM[F]

  def debug(t:   Throwable)(msg: String): F[Unit] = macro debugTM[F]
  def debug(msg: String): F[Unit] = macro debugM[F]

  def info(t:   Throwable)(msg: String): F[Unit] = macro infoTM[F]
  def info(msg: String): F[Unit] = macro infoM[F]

  def warn(t:   Throwable)(msg: String): F[Unit] = macro warnTM[F]
  def warn(msg: String): F[Unit] = macro warnM[F]

  def error(t:   Throwable)(msg: String): F[Unit] = macro errorTM[F]
  def error(msg: String): F[Unit] = macro errorM[F]

}
