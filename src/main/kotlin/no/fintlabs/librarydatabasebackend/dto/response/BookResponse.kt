package no.fintlabs.librarydatabasebackend.dto.response

import java.util.UUID

data class BookResponse(
    val id: UUID,
    val title: String,
    val author: String,
    val loaned: Boolean,
)
