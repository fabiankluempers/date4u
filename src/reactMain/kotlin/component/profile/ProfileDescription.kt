package component.profile

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.textarea

interface ProfileDescriptionProps : Props {
  var description: String
  var isReadOnly: Boolean
  var onChange: (String) -> Unit
}

val ProfileDescription = FC<ProfileDescriptionProps> { props ->
  div {
	label {
	  className = "form-label"
	  htmlFor = "description"
	  +"Description"
	}
	textarea {
	  className = if (props.isReadOnly) "form-control-plaintext" else "form-control"
	  id = "description"
	  value = props.description
	  rows = 5
	  readOnly = props.isReadOnly
	  onChange = { props.onChange(it.target.value) }
	}
  }
}