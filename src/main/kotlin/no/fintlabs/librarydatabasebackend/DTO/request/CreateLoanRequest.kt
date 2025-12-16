package no.fintlabs.librarydatabasebackend.DTO.request

data class CreateLoanRequest(
    val bookId: Long,
    val userId: Long,
)
