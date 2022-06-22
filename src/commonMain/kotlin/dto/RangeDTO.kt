package dto

@kotlinx.serialization.Serializable
data class RangeDTO(val first : Int, val last : Int) {
  fun toRange() = first..last

  companion object {
    fun fromRange(range: IntRange) = with(range) { RangeDTO(first, last) }
  }
}