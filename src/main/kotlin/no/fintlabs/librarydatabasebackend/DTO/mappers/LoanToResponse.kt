package no.fintlabs.librarydatabasebackend.DTO.mappers

import no.fintlabs.librarydatabasebackend.DTO.response.CreateLoanResponse
import no.fintlabs.librarydatabasebackend.DTO.response.GetLoanResponse
import no.fintlabs.librarydatabasebackend.entity.Loan

fun Loan.toGetResponse(): GetLoanResponse = GetLoanResponse(
    loanId = this.id!!,
    borrowerEmail = this.borrower!!.email,
    bookTitle = this.book.title,
    bookAuthor = this.book.author,
    borrowTime = this.borrowTime.toString(),
    returnTime = this.returnTime.toString(),
)

fun Loan.toCreateResponse(): CreateLoanResponse = CreateLoanResponse(
    loanId = this.id!!,
    bookTitle = this.book.title,
    bookAuthor = this.book.author,
    borrowTime = this.borrowTime.toString(),
    returnTime = "",
)