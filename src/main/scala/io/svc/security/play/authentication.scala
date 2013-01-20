package io.svc.security.play

import scalaz.Validation
import play.api.mvc._
import io.svc.security.std._
import io.svc.security.http
import io.svc.security.http.CredentialsExtractionFailure
import io.svc.security.std.UsernamePasswordCredentials
import play.api.mvc.Request
import io.svc.core.function.Extractor
import io.svc.core.validation.{ValidatingSuccessActionHandler, FailureHandler}

/**
 * @author Rintcius Blok
 */
object authentication {

  class PlayBasicAuthenticationCredentialsExtractor[A] extends Extractor[Request[A], UsernamePasswordCredentials, CredentialsExtractionFailure] {
    override def extract(request: Request[A]): Validation[CredentialsExtractionFailure, UsernamePasswordCredentials] = {

      val authHeader = request.headers.get("AUTHORIZATION")

      http.extractCredentialsBasicAuthentication(authHeader)
    }
  }

  def authFailureHandler[A](result: Result) = {
    new FailureHandler[Request[A], AuthenticationFailure, Result] {
      override def handleFailure(req: Request[A], f: AuthenticationFailure): Result = result
    }
  }

  trait PlayAuthenticator[A, User] extends ValidatingSuccessActionHandler[Request[A], User, Result] {
    type Failure = AuthenticationFailure
  }
}