package src.main.scala.repository

import cats.effect._
import model.User
import natchez.Trace.Implicits.noop
import skunk._
import skunk.codec.all._
import skunk.implicits._

object DbSession extends IOApp {

  def getDbSession() = {
    val dbConfig = getConfig()

    val session: Resource[IO, Session[IO]] =
      Session.single(                                          // (2)
        host     = dbConfig.host.getOrElse(""),
        port     = dbConfig.port.getOrElse("-1").toInt,
        user     = dbConfig.user.getOrElse(""),
        database = dbConfig.database.getOrElse(""),
        password = dbConfig.password
      )
    session
  }

  def getConfig(): DbConfig = {
    val resource = DbSession.getClass.getResourceAsStream("/src/main/resources/config/config.properties")
    val lines : Iterator[String] = scala.io.Source.fromInputStream(resource).getLines
    DbConfig(findOption(lines, "host"), findOption(lines, "port"), findOption(lines, "user"),
      findOption(lines, "datadase"), findOption(lines, "password"))
  }

  def findOption(lines: Iterator[String], propertyName: String): Option[String] = {
    lines.filter(s => s.startsWith(propertyName))
         .map(s => s.split("=").last)
         .nextOption()
  }

  case class DbConfig(host: Option[String], port: Option[String],
                      user: Option[String], database: Option[String], password: Option[String])

  def dbState(): IO[ExitCode] = {
    println(s"---- db state ----")

    getDbSession().use { session => // (3)
      for {
        d <- session.unique(sql"select current_date".query(date)) // (4)
        _ <- IO.println(s"The current date is $d.")
      } yield ExitCode.Success
    }
  }

  override def run(args: List[String]): IO[ExitCode] = {
    val o = for {
      u <- UserDao.findUser(1)
      _ <- IO.println(s" ---> $u")
    } yield u.getOrElse(User(0, "_", "_"))
    IO(ExitCode.Success)
    //dbState()
  }
}
