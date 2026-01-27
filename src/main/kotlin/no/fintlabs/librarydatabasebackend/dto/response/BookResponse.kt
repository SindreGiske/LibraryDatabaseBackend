package no.fintlabs.librarydatabasebackend.dto.response

import no.fintlabs.librarydatabasebackend.entity.Loan
import java.util.UUID

data class BookResponse(
    val id: UUID,
    val title: String,
    val author: String,
    val loaned: Boolean,
)

data class BookAdminResponse(
    val id: UUID,
    val title: String,
    val author: String,
    val loaned: Boolean?,
    val loans: List<Loan>
)
