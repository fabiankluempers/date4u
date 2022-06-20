package com.materna.entity

import dto.PhotoDTO
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import org.hibernate.Hibernate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Photo(
    @ManyToOne
    @JoinColumn(name = "profile_fk")
    val profile: Profile,

    val name: String,

    val isProfilePhoto: Boolean,

    val created: LocalDateTime,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Photo

        return id != null && id == other.id
    }

    fun toPhotoDTO() = PhotoDTO(
        nickname = profile.nickname,
        name = name,
        isProfilePhoto = isProfilePhoto,
        created = created.toKotlinLocalDateTime()
    )

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , profile = ${profile.id} , name = $name , isProfilePhoto = $isProfilePhoto , created = $created )"
    }
}
