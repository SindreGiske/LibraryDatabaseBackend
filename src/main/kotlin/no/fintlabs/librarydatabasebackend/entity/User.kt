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
    open var passwordHash: String = "",
    open var admin: Boolean = false,
    open val loans: MutableList<UUID> = mutableListOf(),
) {
    fun makeAdmin() {
        admin = true
    }

    fun toFrontendUserCache(): FrontendUserCache = FrontendUserCache(name, admin)
}

data class FrontendUserCache(
    val name: String,
    val admin: Boolean,
)
