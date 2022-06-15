import dto.ProfileDTO
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.useState
import ViewMode.*
import dto.ProfileConstraintsDTO
import kotlinx.datetime.LocalDate
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import react.dom.html.ReactHTML.span

enum class ViewMode {
  CREATE,
  UPDATE,
  IMMUTABLE
}

external interface ProfileProps : Props {
  var viewMode: ViewMode
  var profileView: ProfileDTO
  var profileConstraints: ProfileConstraintsDTO
  var onSubmit: (ProfileDTO) -> Unit
}

val Profile = FC<ProfileProps> { props ->
  var profile by useState(props.profileView)
  val constraints = props.profileConstraints

  div {
	className = "container justify-content-center"
	div {
	  className = "row"
	  div {
		className = "col-md-4"
	  }
	  div {
		className = "col-md-8"
		h1 {
		  +"Profile of ${profile.nickname}"
		}
	  }
	}
	div {
	  className = "row"
	  div {
		className = "col-md-4"
		//TODO Photos here
		label {
		  className = "form-label"
		  htmlFor = "nickname"
		  +"Nickname"
		}
		input {
		  when (props.viewMode) {
			CREATE    -> {
			  readOnly = true; className = "form-control-plaintext"
			}
			UPDATE    -> {
			  readOnly = true; className = "form-control-plaintext"
			}
			IMMUTABLE -> {
			  readOnly = true; className = "form-control-plaintext"
			}
		  }
		  id = "nickname"
		  value = profile.nickname
		  onChange = { profile = profile.copy(nickname = it.target.value) }
		}
	  }
	  div {
		className = "col-md-8"
		label {
		  className = "form-label"
		  htmlFor = "birthdate"
		  +"Birthdate"
		}
		input {
		  type = InputType.date
		  when (props.viewMode) {
			CREATE    -> {
			  readOnly = false; className = "form-control"
			}
			UPDATE    -> {
			  readOnly = true; className = "form-control-plaintext"
			}
			IMMUTABLE -> {
			  readOnly = true; className = "form-control-plaintext"
			}
		  }
		  value = profile.birthdate.toString()
		  id = "birthdate"
		  onChange = { profile = profile.copy(birthdate = it.target.valueAsDate as LocalDate) }
		}
		span {
		  label {
			className = "form-label"
			htmlFor = "hornLength"
			+"Horn length"
		  }
		  select {
			className = "form-select"
			id = "hornLength"
			for (index in constraints.minHornLength..constraints.maxHornLength) {
			  option {
				value = "$index"
				+"$index"
				selected = index == profile.hornLength
			  }
			}
			onChange = { profile = profile.copy(hornLength = it.target.value.toInt()) }
		  }
		}
		GenderSelect {
		  currentGender = profile.gender
		  genders = constraints.genders
		  isReadOnly = when (props.viewMode) {
			CREATE    -> false
			UPDATE    -> false
			IMMUTABLE -> true
		  }
		  label = "Gender"
		  id = "gender"
		  onGenderSelected = { profile = profile.copy(gender = it) }
		}
		GenderSelect {
		  currentGender = profile.gender
		  genders = constraints.genders
		  isReadOnly = when (props.viewMode) {
			CREATE    -> false
			UPDATE    -> false
			IMMUTABLE -> true
		  }
		  label = "Interested in"
		  id = "attractedTo"
		  onGenderSelected = { profile = profile.copy(attractedToGender = it) }
		}
	  }
	}
  }
}

private interface GenderSelectProps : Props {
  var currentGender: Int?
  var genders: List<String>
  var isReadOnly: Boolean
  var onGenderSelected: (Int) -> Unit
  var label : String
  var id : String
}

private val GenderSelect = FC<GenderSelectProps> { props ->
  span {
	label {
	  className = "form-label"
	  htmlFor = props.id
	  + props.label
	}
	select {
	  id = props.id
	  className = if (props.isReadOnly) "form-control-plaintext" else "form-control"
	  disabled = props.isReadOnly
	  props.genders.forEachIndexed { index, gender ->
		option {
		  value = "$index"
		  + gender
		  selected = index == props.currentGender
		}
	  }
	  onChange = { props.onGenderSelected(it.target.value.toInt()) }
	}
  }
}