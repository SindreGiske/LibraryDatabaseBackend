package no.fintlabs.librarydatabasebackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
open class User(
    @Id
    open val id: UUID = UUID.randomUUID(),
    open var name: String = "",
    open var email: String = "",
    @JsonIgnore
    var passwordHash: String = "",
    open var admin: Boolean = false,
    open val loans: MutableList<UUID> = mutableListOf(),
) {
//    constructor(name: String, email: String, password: String, admin: Boolean?) :
//            this(UUID.randomUUID(), name, email, password, admin?: false)

    fun makeAdmin() {
        admin = true
    }

    fun toFrontendUserCache(): FrontendUserCache = FrontendUserCache(name, admin)
}

data class FrontendUserCache(
    val name: String,
    val admin: Boolean,
)
