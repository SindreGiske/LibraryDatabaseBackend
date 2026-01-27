package no.fintlabs.librarydatabasebackend.dto.request

data class LoginRequest(
    val email: String,
    val password: String
)
