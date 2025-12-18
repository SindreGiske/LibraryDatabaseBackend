package no.fintlabs.librarydatabasebackend.DTO.response

data class CreateLoanResponse(
    val loanId: Long,
    val bookTitle: String,
    val bookAuthor: String,
    val borrowTime: String,
)
