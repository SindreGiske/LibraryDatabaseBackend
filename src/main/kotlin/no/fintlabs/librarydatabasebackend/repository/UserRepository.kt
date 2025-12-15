package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun getUserById(id: Long): User?

    fun getUserByName(name: String): User?

    fun getUserByEmail(email: String): User?

}
