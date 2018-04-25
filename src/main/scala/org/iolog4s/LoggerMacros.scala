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

import language.experimental.macros

import scala.annotation.tailrec
import scala.reflect.macros.{blackbox, whitebox}

/** Macros that support the logging system.
  *
  * @author Sarah Gerweck <sarah@atscale.com>
  */
private[iolog4s] object LoggerMacros {

  /** Get a logger by reflecting the enclosing class name. */
  final def getLoggerImpl[F: c.WeakTypeTag](c: blackbox.Context) = {
    import c.universe._

    @tailrec def findEnclosingClass(sym: c.universe.Symbol): c.universe.Symbol = {
      sym match {
        case NoSymbol =>
          c.abort(c.enclosingPosition, s"Couldn't find an enclosing class or module for the logger")
        case s if s.isModule || s.isClass =>
          s
        case other =>
          /* We're not in a module or a class, so we're probably inside a member definition. Recurse upward. */
          findEnclosingClass(other.owner)
      }
    }

    val cls = findEnclosingClass(c.internal.enclosingOwner)

    assert(cls.isModule || cls.isClass, "Enclosing class is always either a module or a class")

    def loggerByParam(param: c.Tree) = {
      q"new _root_.org.iolog4s.Logger(_root_.org.slf4j.LoggerFactory.getLogger(...${List(param)}))"
    }

    def loggerBySymbolName(s: Symbol) = {
      def fullName(s: Symbol): String = {
        @inline def isPackageObject = (
          (s.isModule || s.isModuleClass)
            && s.owner.isPackage
            && s.name.decodedName.toString == termNames.PACKAGE.decodedName.toString
        )
        if (s.isModule || s.isClass) {
          if (isPackageObject) {
            s.owner.fullName
          }
          else if (s.owner.isStatic) {
            s.fullName
          }
          else {
            fullName(s.owner) + "." + s.name.encodedName.toString
          }
        }
        else {
          fullName(s.owner)
        }
      }
      loggerByParam(q"${fullName(s)}")
    }

    def loggerByType(s: Symbol) = {
      val typeSymbol: ClassSymbol = (if (s.isModule) s.asModule.moduleClass else s).asClass
      val typeParams = typeSymbol.typeParams

      if (typeParams.isEmpty) {
        loggerByParam(q"classOf[$typeSymbol]")
      }
      else {
        if (typeParams.exists(_.asType.typeParams.nonEmpty)) {
          /* We have at least one higher-kinded type: fall back to by-name logger construction, as
           * there's no simple way to declare a higher-kinded type with an "any" parameter. */
          loggerBySymbolName(s)
        }
        else {
          val typeArgs        = List.fill(typeParams.length)(WildcardType)
          val typeConstructor = tq"$typeSymbol[..${typeArgs}]"
          loggerByParam(q"classOf[$typeConstructor]")
        }
      }
    }

    @inline def isInnerClass(s: Symbol) = {
      s.isClass && !(s.owner.isPackage)
    }

    val instanceByName = Logger.singletonsByName && (cls.isModule || cls.isModuleClass) || cls.isClass && isInnerClass(
      cls
    )

    if (instanceByName) {
      loggerBySymbolName(cls)
    }
    else {
      loggerByType(cls)
    }
  }

  /** A macro context that represents a method call on a Logger instance. */
  private[this] type LogCtx[F[_]] = whitebox.Context { type PrefixType = Logger[F] }

  /** Log a message reflectively at a given level.
    *
    * This is the internal workhorse method that does most of the logging for real applications.
    *
    * @param msg the message that the user wants to log
    * @param error the `Throwable` that we're logging along with the message, if any
    * @param logLevel the level of the logging
    */
  private[this] def reflectiveLog[F[_]](
    c:   LogCtx[F]
  )(msg: c.Expr[String], error: Option[c.Expr[Throwable]])(logLevel: LogLevel) = {
    import c.universe._

    val logger = q"${c.prefix.tree}.logger"
    val F      = q"${c.prefix}.F"
    val logValues = error match {
      case None    => List(msg.tree)
      case Some(e) => List(msg.tree, e.tree)
    }
    val logExpr   = q"$logger.${TermName(logLevel.methodName)}(..$logValues)"
    val checkExpr = q"$logger.${TermName(s"is${logLevel.name}Enabled")}()"

    def errorIsSimple = {
      error match {
        case None | Some(c.Expr(Ident(_))) => true
        case _                             => false
      }
    }

    msg match {
      case c.Expr(Literal(Constant(_))) if errorIsSimple =>
        q"$F.delay($logExpr)"
      case _ =>
        q"if ($checkExpr) $F.delay($logExpr) else $F.unit"
    }
  }

  def traceTM[F[_]](c: LogCtx[F])(t:   c.Expr[Throwable])(msg: c.Expr[String]) = reflectiveLog(c)(msg, Some(t))(Trace)
  def traceM[F[_]](c:  LogCtx[F])(msg: c.Expr[String]) = reflectiveLog(c)(msg, None)(Trace)

  def debugTM[F[_]](c: LogCtx[F])(t:   c.Expr[Throwable])(msg: c.Expr[String]) = reflectiveLog(c)(msg, Some(t))(Debug)
  def debugM[F[_]](c:  LogCtx[F])(msg: c.Expr[String]) = reflectiveLog(c)(msg, None)(Debug)

  def infoTM[F[_]](c: LogCtx[F])(t:   c.Expr[Throwable])(msg: c.Expr[String]) = reflectiveLog(c)(msg, Some(t))(Info)
  def infoM[F[_]](c:  LogCtx[F])(msg: c.Expr[String]) = reflectiveLog(c)(msg, None)(Info)

  def warnTM[F[_]](c: LogCtx[F])(t:   c.Expr[Throwable])(msg: c.Expr[String]) = reflectiveLog(c)(msg, Some(t))(Warn)
  def warnM[F[_]](c:  LogCtx[F])(msg: c.Expr[String]) = reflectiveLog(c)(msg, None)(Warn)

  def errorTM[F[_]](c: LogCtx[F])(t:   c.Expr[Throwable])(msg: c.Expr[String]) = reflectiveLog(c)(msg, Some(t))(Error)
  def errorM[F[_]](c:  LogCtx[F])(msg: c.Expr[String]) = reflectiveLog(c)(msg, None)(Error)
}
