package app

import model.User
import repository.{UserDao}

object Main extends cask.MainRoutes {

  println(s"\nServing at http://localhost:8080/revizz/User-001/index.html \n")

  println(repository.UserDao.findUser(1))
  println(repository.UserDao.findUser(0))

  @cask.staticFiles("/revizz/:User_id/:filename",
    headers = Seq("Accept" -> "text/css, text/javascript, text/html, image/png, image/avif,image/webp,*/*",
      "Content-Type" -> "text/css, text/javascript, text/html, image/png"))
  def all(User_id: String, filename: String) = {

    filename.split("\\.").last match {
      case "css" => s"RevizzBackEnd/src/main/resources/styles/$filename"
      case "png" => s"RevizzBackEnd/src/main/resources/cards/$filename"
      case _     => s"RevizzBackEnd/src/main/resources/$filename"
    }
  }

  @cask.staticFiles("/favicon.ico",
    headers = Seq("Accept" -> "image/avif"))
  def icone() = {
    s"RevizzBackEnd/src/main/resources/favicon.ico"

  }

  initialize()
}