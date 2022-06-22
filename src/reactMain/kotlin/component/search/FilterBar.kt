package component.search

import component.generic.Checkbox
import csstype.rem
import react.FC
import react.Props
import react.css.css
import react.dom.html.ButtonType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.useEffect
import react.useState

interface FilterBarProps : Props {
  var filter: ProfileFilter
  var onSearch: (ProfileFilter) -> Unit
}

data class ProfileFilter(val ageRange: IntRange, val hornLengthRange: IntRange, val interestedIn: Set<Int>)

val FilterBar = FC<FilterBarProps> {props ->
  val colClass = "col m-3"
  var interestedIn by useState(props.filter.interestedIn)
  var ageRange by useState(props.filter.ageRange)
  var hornLengthRange by useState(props.filter.hornLengthRange)

  useEffect(interestedIn) {
	println(interestedIn)
  }

  div {
	className = "row align-items-center"
	div {
	  className = colClass
	  Range {
		id = "hornLengthRange"
		min = props.filter.hornLengthRange.first
		max = props.filter.hornLengthRange.last
		minLabel = "Minimum horn length"
		maxLabel = "Maximum horn length"
		onChange = { hornLengthRange = it }
	  }
	}
	div {
	  className = colClass
	  Range {
		id = "ageRange"
		min = props.filter.ageRange.first
		max = props.filter.ageRange.last
		minLabel = "Minimum age"
		maxLabel = "Maximum age"
		onChange = { ageRange = it }
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
			interestedIn = if (checked) interestedIn + index else interestedIn - index
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
		onClick = { props.onSearch(ProfileFilter(ageRange, hornLengthRange, interestedIn)) }
	  }
	}
  }
}