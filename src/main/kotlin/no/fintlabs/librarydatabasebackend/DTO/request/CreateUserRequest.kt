package no.fintlabs.librarydatabasebackend.DTO.request

data class CreateUserRequest (
    val name: String,
    val email: String,
    val password: String
)