**Back end**
- mill
- cask
- upickle

**Front end**
- mill
- scalajs
- laminar
- laminext
- upickle

**Public**
- upickle

**build & launch**
- open a terminal for the back
  - install mill (via coursier)
  - move in directory revizz
  - execute the command "~/.local/share/coursier/bin/mill RevizzModule"
    - start serving at <http://localhost:8080/>
- open a terminal for the front
  - move in directory revizz
  - execute the command
    - "~/.local/share/coursier/bin/mill clean RevizzFrontEndModule &&  ~/.local/share/coursier/bin/mill RevizzFrontEndModule.buildAndCopy"
  - open <http://localhost:8080/revizz/User-001/index.html> in a browser 
