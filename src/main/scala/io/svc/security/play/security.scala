package io.svc.security.play

import play.api.mvc.{Action, Result, Request, BodyParser}
import io.svc.security.play.authentication.PlayAuthenticator

/**
 * @author Rintcius Blok
 */
object security {
  trait PlaySecurity[A, User] {

    val authenticator: PlayAuthenticator[A, User]

    def onAuthenticated(parser: BodyParser[A])(action: (Request[A], User) => Result): Action[A] = Action(parser) {
      req =>
        authenticator.onSuccess(action)(req)
    }

    //TODO how to get this working..
    //def onAuthenticated(action: (Request[AnyContent], User) => Result): Action[A] = onAuthenticated (BodyParsers.parse.anyContent) action
  }

}
