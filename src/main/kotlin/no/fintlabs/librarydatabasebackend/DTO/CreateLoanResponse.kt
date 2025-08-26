package no.fintlabs.librarydatabasebackend.DTO

data class CreateLoanResponse(
    val loanId: Long,
    val bookTitle: String,
    val bookAuthor: String,
    val borrowTime: String,
    val returnTime: String,
)
