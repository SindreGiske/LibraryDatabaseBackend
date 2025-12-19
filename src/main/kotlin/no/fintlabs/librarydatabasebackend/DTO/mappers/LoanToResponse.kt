package no.fintlabs.librarydatabasebackend.DTO.mappers

import no.fintlabs.librarydatabasebackend.DTO.response.CreateLoanResponse
import no.fintlabs.librarydatabasebackend.DTO.response.GetLoanResponse
import no.fintlabs.librarydatabasebackend.DTO.response.GetAllLoansResponse
import no.fintlabs.librarydatabasebackend.entity.Loan

fun Loan.toGetResponse(): GetLoanResponse {
    val returnTimeString = this.returnTime?.toString() ?: ""

    return GetLoanResponse(
    loanId = this.id!!,
    username = this.user!!.name,
    bookTitle = this.book!!.title,
    bookAuthor = this.book!!.author,
    borrowTime = this.borrowTime.toString(),
    returnTime = returnTimeString,
)
}

fun Loan.toCreateResponse(): CreateLoanResponse = CreateLoanResponse(
    loanId = this.id!!,
    bookTitle = this.book!!.title,
    bookAuthor = this.book!!.author,
    borrowTime = this.borrowTime.toString(),
)

fun allLoansToResponse(list: List<Loan>): GetAllLoansResponse {
    val (returned, active) = list.partition { it.returnTime != null }
    return GetAllLoansResponse(
        active.map { it.toGetResponse() },
        returned.map { it.toGetResponse() }
    )
}