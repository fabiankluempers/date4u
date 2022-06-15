package component.profile

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select

interface GenderSelectProps : Props {
  var currentGender: Int?
  var isIncludeDefault: Boolean
  var genders: List<String>
  var isReadOnly: Boolean
  var onGenderSelected: (Int) -> Unit
  var label: String
  var id: String
}

val ProfileGenderSelect = FC<GenderSelectProps> { props ->
  div {
	className = "mb-3"
	label {
	  className = "form-label"
	  htmlFor = props.id
	  +props.label
	}
	select {
	  id = props.id
	  className = if (props.isReadOnly) "form-control-plaintext w-25" else "form-control w-25"
	  disabled = props.isReadOnly
	  val defaultIndex = props.genders.size
	  props.genders.forEachIndexed { index, gender ->
		option {
		  value = "$index"
		  +gender
		}
	  }
	  if (props.isIncludeDefault) {
		option {
		  value = "$defaultIndex"
		  + "Everyone"
		}
	  }
	  value = "${props.currentGender ?: defaultIndex}"
	  onChange = { props.onGenderSelected(it.target.value.toInt()) }
	}
  }
}