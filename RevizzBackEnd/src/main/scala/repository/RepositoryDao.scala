package src.main.scala.repository

import cats.effect
import cats.effect.IO
import skunk.Session

trait RepositoryDao {

  val dbSession: effect.Resource[IO, Session[IO]] = DbSession.getDbSession()

}
