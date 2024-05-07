package app

import cask.Logger.Console.globalLogger
import cask.WsChannelActor
import cask.util.Ws
import upickle.default._
import upickle.implicits._
import model.User

import scala.collection.mutable
import scala.util.{Failure, Success, Try}

object Main extends cask.MainRoutes {

  println(s"\nServing at http://localhost:8080/revizz/User-001/index.html \n")

  @cask.staticFiles("/revizz/:User_id/:filename",
    headers = Seq("Accept" -> "text/css, text/javascript, text/html, image/png, image/avif,image/webp,*/*",
      "Content-Type" -> "text/css, text/javascript, text/html, image/png"))
  def all(User_id: String, filename: String) = {

    filename.split("\\.").last match {
      case "css" => s"RevizzBackEnd/App/src/main/resources/styles/$filename"
      case "png" => s"RevizzBackEnd/App/src/main/resources/cards/$filename"
      case _     => s"RevizzBackEnd/App/src/main/resources/$filename"
    }
  }

  @cask.staticFiles("/favicon.ico",
    headers = Seq("Accept" -> "image/avif"))
  def icone() = {
    s"RevizzBackEnd/App/src/main/resources/favicon.ico"

  }

  initialize()
}