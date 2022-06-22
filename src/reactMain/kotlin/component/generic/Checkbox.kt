package component.generic

import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label

interface CheckboxProps : Props {
  var defaultChecked: Boolean
  var id: String
  var label: String
  var onChange: (Boolean) -> Unit
}

val Checkbox = FC<CheckboxProps> { props ->
  div {
    className = "form-check"
    input {
      className = "form-check-input"
      type = InputType.checkbox
      value = ""
      id = props.id
      defaultChecked = props.defaultChecked
      onChange = { props.onChange(it.target.checked) }
    }
    label {
      className = "form-check-label"
      htmlFor = props.id
      +props.label
    }
  }
}