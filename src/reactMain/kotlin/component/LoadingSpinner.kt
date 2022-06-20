package component

import react.FC
import react.Props
import react.dom.aria.AriaRole
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.span

val LoadingSpinner = FC<Props> {
  div {
	className = "spinner-border"
	span {
	  className = "visually-hidden"
	  + "Loading..."
	}
  }
}