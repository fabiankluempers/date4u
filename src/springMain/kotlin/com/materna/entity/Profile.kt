package com.materna.entity

import dto.ProfileDTO
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.hibernate.Hibernate
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Profile(
	val birthdate: LocalDate,

	val nickname: String,

	val hornLength: Short,

	val gender: Short,

	val attractedToGender: Short?,

	val description: String,

	val lastSeen: LocalDateTime,

	@OneToMany(mappedBy = "profile", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
	val photos: MutableList<Photo>,

	@OneToOne(mappedBy = "profile")
	val unicorn: Unicorn,

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long? = null,
) {
	fun toProfileDTO() = ProfileDTO(
		nickname = nickname,
		hornLength = hornLength.toInt(),
		birthdate = birthdate.toKotlinLocalDate(),
		gender = gender.toInt(),
		attractedToGender = attractedToGender?.toInt(),
		description = description,
		lastSeen = lastSeen.toKotlinLocalDateTime(),
	)

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
		other as Profile

		return id != null && id == other.id
	}

	override fun hashCode(): Int = javaClass.hashCode()

	@Override
	override fun toString(): String {
		return this::class.simpleName + "(id = $id , birthdate = $birthdate , nickname = $nickname , hornLength = $hornLength , gender = $gender , attractedToGender = $attractedToGender , description = $description , lastSeen = $lastSeen , unicorn = ${unicorn.id} )"
	}

}
