package no.fintlabs.librarydatabasebackend.DTO.response


data class GetLoanResponse(
    val loanId: Long,
    val bookTitle: String,
    val bookAuthor: String,
    val username: String,
    val borrowTime: String,
    val returnTime: String,
)

data class GetAllLoansResponse(
    val activeLoans: List<GetLoanResponse>,
    val loanHistory: List<GetLoanResponse>
)