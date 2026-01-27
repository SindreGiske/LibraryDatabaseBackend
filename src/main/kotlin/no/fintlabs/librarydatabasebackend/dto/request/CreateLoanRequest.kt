package no.fintlabs.librarydatabasebackend.dto.request

data class CreateLoanRequest(
    val bookId: Long,
    val userId: Long,
)
