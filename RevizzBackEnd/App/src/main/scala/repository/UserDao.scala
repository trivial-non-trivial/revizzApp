package src.main.scala.repository

import cats.effect.unsafe.implicits.global
import cats.effect.{ExitCode, IO}
import model.User
import skunk.Query
import skunk.codec.all.{date, int4, varchar}
import skunk.implicits.toStringOps
import src.main.scala.repository.DbSession.getDbSession

object UserDao extends RepositoryDao {

  def findUser(id: Int): IO[Option[User]] = {
    val sql: Query[Int, User] = sql"""
    select * from "revizzApp"."User" u
       where u.user_i_id = $int4"""
    .query(int4 *: varchar *: varchar).to[User]

    getDbSession().use { session =>
      session.prepare(sql).map { pc =>
        val r = pc.option(id)
        r.flatMap(u => IO.println(u))
      }.unsafeRunSync()
    }.unsafeRunSync()
    IO(None)
  }
}
