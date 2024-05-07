package domAction

import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveHtmlElement
import org.scalajs.dom
import org.scalajs.dom.{HTMLButtonElement, HTMLInputElement}

object DomAction {

  def renderDom(): Unit = {
    val appContainer: dom.Element = dom.document.querySelector("#appContainer")
    render(appContainer, createContent(appContainer))
  }

  def createContent(appContainer:  dom.Element): Div = {

    appContainer.children.foreach(c => appContainer.removeChild(c))
    div(
      h1("Hello, revizzApp !")
    )
  }
}
