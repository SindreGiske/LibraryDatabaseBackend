package no.fintlabs.librarydatabasebackend.dto.request

data class CreateUserRequest (
    val name: String,
    val email: String,
    val password: String
)