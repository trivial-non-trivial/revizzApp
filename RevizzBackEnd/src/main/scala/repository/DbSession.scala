package repository

import cats._
import cats.effect.{IO, _}
import cats.effect.unsafe.implicits.global
import cats.data._
import cats.implicits._
import doobie._
import doobie.implicits._

import java.time.LocalDate

object DbSession extends IOApp {

  def getConnexion() = {
    val dbConfig = getConfig()

    Transactor.fromDriverManager[IO](
      driver = "org.postgresql.Driver",
      url = s"jdbc:postgresql:${dbConfig.database.getOrElse("?")}",
      user = dbConfig.user.getOrElse(""),
      password = dbConfig.password.getOrElse(""),
      logHandler = None
    )
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

  def dbState() = {
    println(s"---- db state ----")

    val sql = sql"""select current_date""".query[String].unique
    println(sql.transact(getConnexion()).unsafeRunSync())
  }

  override def run(args: List[String]): IO[ExitCode] = {

    dbState()
    IO(ExitCode.Success)
  }
}
