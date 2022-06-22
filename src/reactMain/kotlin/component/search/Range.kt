package component.search

import csstype.rem
import react.*
import react.css.css
import react.dom.html.InputType
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.label
import kotlin.math.max
import kotlin.math.min

interface RangeProps : Props {
  var id: String
  var min: Int
  var minLabel: String
  var max: Int
  var maxLabel: String
  var onChange: (IntRange) -> Unit
}

val Range = FC<RangeProps> { props ->
  var range by useState(props.min..props.max)

  val minId = "${props.id}Min" // useId does not work for some reason...
  val maxId = "${props.id}Max"

  useEffect(range) {
    props.onChange(range)
  }

  div {
    css { width = 18.rem }
    label {
      className = "form-label"
      htmlFor = minId
      +"${props.minLabel}: ${range.first}"
    }
    ReactHTML.input {
      id = minId
      type = InputType.range
      className = "form-range"
      min = props.min.toDouble()
      max = props.max.toDouble()
      defaultValue = "${props.min}"
      onInput = {
        range = it.currentTarget.valueAsNumber.toInt()..range.last
      }
    }
    label {
      className = "form-label"
      htmlFor = minId
      +"${props.maxLabel}: ${range.last}"
    }
    ReactHTML.input {
      id = maxId
      type = InputType.range
      className = "form-range"
      min = props.min.toDouble()
      max = props.max.toDouble()
      defaultValue = "${props.max}"
      onInput = {
        range = range.first..it.currentTarget.valueAsNumber.toInt()
      }
    }
  }
}