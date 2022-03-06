package org.narrative
package analytics


import analytics.QueryParameters.{EventType, Timestamp, UserId}

import cats.Hash
import org.http4s.dsl.impl.{QueryParamDecoderMatcher, ValidatingQueryParamDecoderMatcher}
import org.http4s.{ParseFailure, QueryParamDecoder}

import scala.util.Try


case class Username(name: String)

case class Password(hash: Long)

def help(id: Username | Password) =
  val user = id match
    case Username(name) => name
    case Password(hash) => hash

object QueryMatchers {
  given eventQueryParamDecoder: QueryParamDecoder[EventType] = QueryParamDecoder[String]
    .emap(EventType.safe)

  given userQueryParamDecoder: QueryParamDecoder[UserId] = QueryParamDecoder[String].emap(UserId.safe)

  given timestampQueryParamDecoder: QueryParamDecoder[Timestamp] = QueryParamDecoder[Long].emap(Timestamp.safe)

  object TimestampQueryParamMatcher extends ValidatingQueryParamDecoderMatcher[Timestamp]("timestamp")

  object UserQueryParamMatcher extends ValidatingQueryParamDecoderMatcher[UserId]("user")

  object EventQueryParamMatcher extends ValidatingQueryParamDecoderMatcher[EventType]("event")
}