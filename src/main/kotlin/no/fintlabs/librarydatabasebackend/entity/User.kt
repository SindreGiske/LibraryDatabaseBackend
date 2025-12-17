package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import no.fintlabs.librarydatabasebackend.DTO.response.UserInfo

@Entity
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    open var name: String = "",
    open var email: String = "",
    open var password: String = "",
    open var admin: Boolean = false,

    @OneToMany(mappedBy = "user",  cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open val loans: MutableList<Loan> = mutableListOf(),
) {
    fun toDTO() = UserInfo(id!!, name, email, admin = admin)

    fun makeAdmin() {
        admin = true
    }
}
