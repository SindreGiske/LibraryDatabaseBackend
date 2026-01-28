package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.entity.User
import no.fintlabs.librarydatabasebackend.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val repository: UserRepository,
) {
    fun isAdmin(id: UUID): Boolean {
        val user = repository.getUserById(id)
            return user != null && user.admin
    }

    fun adminExists(): Boolean =
        repository.existsByAdminTrue()

    fun getAllUsers(): List<User> = repository.findAll()

    fun findById(id: UUID): User? = repository.getUserById(id)

    fun getUserByEmail(email: String): User? = repository.getUserByEmail(email)

    fun registerNew(user: User): User =
        repository.save(user)

    fun deleteUserById(userId: UUID) {
        val user: User = findById(userId)!!
        println("DELETING USER : ${user.id} @ ${user.email}")
        repository.delete(user)
    }
}