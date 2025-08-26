package no.fintlabs.librarydatabasebackend.DTO.mappers

import no.fintlabs.librarydatabasebackend.DTO.GetLoanResponse
import no.fintlabs.librarydatabasebackend.entity.Loan

fun Loan.toResponse(): GetLoanResponse = GetLoanResponse(
    loanId = this.id!!,
    borrowerEmail = this.borrower!!.email,
    bookTitle = this.book.title,
    bookAuthor = this.book.author,
    borrowTime = this.borrowTime.toString(),
    returnTime = this.returnTime.toString(),
)

