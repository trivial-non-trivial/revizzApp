import mill._
import mill.scalajslib.api.Report
import os.{Path, home}
import scalalib._
import scalajslib._

trait RevizzModule extends ScalaModule{
  def scalaVersion = T {"2.13.14"}
  def scalaJSVersion = T {"1.15.0"}

  val baseDir: Path = Path(millSourcePath.toNIO.getParent)

  override def ivyDeps = Agg(
    ivy"io.laminext::websocket_sjs1:0.16.0",
    ivy"io.laminext::websocket-upickle_sjs1:0.16.0",
    ivy"io.laminext::fetch-upickle_sjs1:0.16.0",
    ivy"com.lihaoyi::upickle:3.1.1"
  )
}

object RevizzBackEndModule extends RevizzModule {

  // Add (or replace) source folders for the module to use
  override def sources = T.sources {
    super.sources() ++
      Seq(PathRef(baseDir / "RevizzPublic")) ++
      Seq(PathRef(baseDir  / Seq("RevizzBackEnd", "App")))
  }

  override def mainClass = Some("app.Main")

  override def ivyDeps = super.ivyDeps() ++ Seq(
    ivy"com.lihaoyi::cask:0.9.2",
    ivy"com.lihaoyi::mill-scalalib:0.11.6"
  )
}

object RevizzFrontEndModule extends ScalaJSModule with RevizzModule {

  // Add (or replace) source folders for the module to use
  override def sources = T.sources{
    super.sources() ++
      Seq(PathRef(baseDir / "RevizzPublic")) ++
      Seq(PathRef(baseDir / "RevizzFrontEnd"))
  }

  override def fastLinkJS: Target[Report] = {
    val out = super.fastLinkJS

    println("Build:: Done")

    out
  }

  def buildAndCopy = T {

    println("Build:: " + this.fastLinkJS())

    val srcIco = "RevizzFrontEnd/src/main/resources/favicon.ico"
    val destIco = "RevizzBackEnd/App/src/main/resources/favicon.ico"
    os.proc("cp", srcIco, destIco).call()
    println("Copy :: " + srcIco)

    val srcHtml = "RevizzFrontEnd/src/main/resources/index.html"
    val destHtml = "RevizzBackEnd/App/src/main/resources/index.html"
    os.proc("cp", srcHtml, destHtml).call()
    println("Copy :: " + srcHtml)

    val srcCss = "RevizzFrontEnd/src/main/resources/styles"
    val destCss = "RevizzBackEnd/App/src/main/resources/"
    os.proc("cp", "-r", srcCss, destCss).call()
    println("Copy :: " + srcCss)

//    val srcCards = "RevizzFrontEnd/src/main/resources/cards"
//    val destCards = "RevizzBackEnd/App/src/main/resources/"
//    os.proc("cp", "-r", srcCards, destCards).call()
//    println("Copy :: " + srcCards)
//
//    val srcOthers = "RevizzFrontEnd/src/main/resources/others"
//    val destOthers = "RevizzBackEnd/App/src/main/resources/"
//    os.proc("cp", "-r", srcOthers, destOthers).call()
//    println("Copy :: " + srcOthers)

    val srcJs = "out/RevizzFrontEndModule/fastLinkJS.dest/main.js"
    val destJs = "RevizzBackEnd/App/src/main/resources/"
    os.proc("cp", srcJs, destJs).call()
    println("Copy :: " + srcJs)

    val srcJsMap = "out/RevizzFrontEndModule/fastLinkJS.dest/main.js.map"
    val destJsMap = "RevizzBackEnd/App/src/main/resources/"
    os.proc("cp", srcJsMap, destJsMap).call()
    println("Copy :: " + srcJsMap)

  }

  override def ivyDeps = super.ivyDeps() ++ Seq(
    ivy"com.raquo::laminar::16.0.0"
  )
}

object RevizzPublicModule extends RevizzModule {

  // Add (or replace) source folders for the module to use
  override def sources = T.sources {
    super.sources() ++
      Seq(PathRef(baseDir / "RevizzPublic"))
  }

}
