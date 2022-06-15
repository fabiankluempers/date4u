package com.materna.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
data class Unicorn(
    val email: String,

    val password: String,

    @OneToOne
    @JoinColumn(name = "profile_fk")
    val profile: Profile? = null,

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Unicorn

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , email = $email , password = $password , profile = $profile.id )"
    }
}
