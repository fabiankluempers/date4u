package component.search

import component.Checkbox
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.useEffect
import react.useState

val FilterBar = FC<Props> {
  var interestedIn by useState<Set<Int>>(setOf())
  //Gender
  //Horn Length
  //Age

  useEffect(interestedIn) {
	println(interestedIn)
  }

  div {
	className = "row align-items-center"
	div {
	  className = "col-3"
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
	  className = "col-3"
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
	  className = "col-3 m3"
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
	  className = "col-3 m3"
	  button {
		className = "btn btn-large btn-primary"
		+"Find Unicorns!"
	  }
	}
  }
}