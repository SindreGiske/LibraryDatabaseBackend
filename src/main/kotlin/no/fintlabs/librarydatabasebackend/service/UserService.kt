package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.entity.User
import no.fintlabs.librarydatabasebackend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository,
) {
    fun isAdmin(userId: Long): Boolean =
        repository.getUserById(userId)?.adminId != null

    fun getAllUsers(): List<User> = repository.findAll()

    fun findById(id: Long): User? = repository.getUserById(id)

    fun getUserByEmail(email: String): User? = repository.getUserByEmail(email)

    fun registerNew(user: User): User =
        repository.save(User( name = user.name, email = user.email, password = user.password ))
}