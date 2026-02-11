package no.fintlabs.librarydatabasebackend.DTO.response

data class BookResponse(
    val id: String,
    val title: String,
    val author: String,
    val description: String,
    val loaned: Boolean,
)
