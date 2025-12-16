package no.fintlabs.librarydatabasebackend.DTO.request

data class BookRequest(
    val userId: Long,
    val title: String,
    val author: String
)
