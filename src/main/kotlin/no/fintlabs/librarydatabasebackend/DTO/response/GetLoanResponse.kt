package no.fintlabs.librarydatabasebackend.DTO.response

data class GetLoanResponse(
    val loanId: Long,
    val bookTitle: String,
    val bookAuthor: String,
    val borrowTime: String,
    val returnTime: String,
    val borrowerEmail: String,
)
