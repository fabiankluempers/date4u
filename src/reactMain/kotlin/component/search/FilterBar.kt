package component.search

import component.Checkbox
import csstype.rem
import react.FC
import react.Props
import react.css.css
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.useEffect
import react.useState

val FilterBar = FC<Props> {
  val colClass = "col m-3"
  var interestedIn by useState<Set<Int>>(setOf())

  useEffect(interestedIn) {
    println(interestedIn)
  }

  div {
    className = "row align-items-center"
    div {
      className = colClass
      Range {
        id = "hornLengthRange"
        min = 0
        max = 50
        minLabel = "Minimum horn length"
        maxLabel = "Maximum horn length"
        onChange = { min, max -> println("$min $max") }
      }
    }
    div {
      className = colClass
      Range {
        id = "ageRange"
        min = 18
        max = 50
        minLabel = "Minimum age"
        maxLabel = "Maximum age"
        onChange = { min, max -> println("$min $max") }
      }
    }
    div {
      css { width = 18.rem }
      className += " $colClass"
      div {
        className = "mb-1"
        +"Interested in:"
      }
      listOf("Male", "Female", "Diverse").mapIndexed { index, gender ->
        Checkbox {
          defaultChecked = false
          label = gender
          id = gender + "Checkbox"
          onChange = { checked ->
            println(checked)
            if (checked) interestedIn += index else interestedIn -= index
          }
        }
      }
    }
    div {
      className = colClass
      button {
				type = ButtonType.button
        className = "btn btn-large btn-primary"
        +"Find Unicorns!"
      }
    }
  }
}