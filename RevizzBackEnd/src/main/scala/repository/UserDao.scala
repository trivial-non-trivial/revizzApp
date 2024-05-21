package repository

import model.User
import cats._
import cats.effect.{IO, _}
import cats.effect.unsafe.implicits.global
import cats.data._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.syntax.SqlInterpolator
import RepositoryDao._
import RepositoryDao.yolo._

object UserDao extends RepositoryDao {

  def findUser(id: Int): Option[User] = {
    val sql = sql"""
    select * from "revizzApp"."User" u
       where u.user_i_id = $id"""
      .query[User].option

    // sql.quick.unsafeRunSync() yolo -> Unit
    sql.transact(dbConnexion).unsafeRunSync()

  }

}
