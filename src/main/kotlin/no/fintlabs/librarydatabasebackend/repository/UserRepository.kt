package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {

    fun existsByAdminTrue(): Boolean

    fun getUserById(id: UUID): User?

    fun getUserByEmail(email: String): User?
}
