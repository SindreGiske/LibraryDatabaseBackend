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
open class Borrower(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    open var name: String = "",
    open var email: String = "",
    open var password: String = "",

    @OneToMany(mappedBy = "borrower",  cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open val loans: MutableList<Loan> = mutableListOf(),
) {
    fun toDTO() = UserInfo(id!!, name, email)
}
