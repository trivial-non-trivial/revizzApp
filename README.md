**Back end**
- mill
- cask
- upickle
- cats effect
- doobie

**Front end**
- mill
- scalajs
- laminar
- laminext
- upickle

**Public**
- upickle

**build & launch**
- sync project
  - install mill (via coursier)
  - move in directory 'revizzApp'


- open a terminal for the back
  - execute the command 
    - "~/.local/share/coursier/bin/mill RevizzBackEndModule"
  - here, the back start to serve at <http://localhost:8080/>


- open a terminal for the front
  - move in directory revizz
  - execute the command
    - "~/.local/share/coursier/bin/mill clean RevizzFrontEndModule &&  ~/.local/share/coursier/bin/mill RevizzFrontEndModule.buildAndCopy"
  - open <http://localhost:8080/revizz/User-001/index.html> in a browser 


- edit with IntelliJ
  - genarate a compatible project with the command
    - "~/.local/share/coursier/bin/mill mill.idea.GenIdea/idea"