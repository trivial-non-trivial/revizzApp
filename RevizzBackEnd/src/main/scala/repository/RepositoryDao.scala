package repository

import cats._
import cats.effect.{IO, _}
import cats.effect.unsafe.implicits.global
import cats.data._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.syntax.SqlInterpolator

trait RepositoryDao {

  val dbConnexion = DbSession.getConnexion()

}
