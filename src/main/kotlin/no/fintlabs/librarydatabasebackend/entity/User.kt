package no.fintlabs.librarydatabasebackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.util.UUID

@Entity
open class User(

    @Id
    open val id: UUID,

    open var name: String = "",
    open var email: String = "",

    @JsonIgnore
    private var password: String = "",
    open var admin: Boolean = false,

    @OneToMany(mappedBy = "user",  cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open val loans: MutableList<Loan> = mutableListOf(),
) {
    constructor(name: String, email: String, password: String, admin: Boolean?) :
            this(UUID.randomUUID(), name, email, password, admin?: false)

    fun makeAdmin() {
        admin = true
    }

    fun validatePassword(input: String): Boolean {
        return input == password
    }
}
