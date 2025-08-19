package no.fintlabs.librarydatabasebackend.DTO

data class LoanResponse(
    val loanId: Long,
    val bookTitle: String,
    val bookAuthor: String,
    val borrowTime: String,
)
