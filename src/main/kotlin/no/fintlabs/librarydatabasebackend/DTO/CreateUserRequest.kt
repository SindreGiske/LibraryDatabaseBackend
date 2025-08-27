package no.fintlabs.librarydatabasebackend.DTO

data class CreateUserRequest (
    val name: String,
    val email: String,
    val password: String
)