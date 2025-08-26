package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.DTO.CreateLoanResponse
import no.fintlabs.librarydatabasebackend.DTO.GetLoanResponse
import no.fintlabs.librarydatabasebackend.service.LoanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/loan")
class LoanController (
    private val service: LoanService,
) {

    @PostMapping
    fun createLoan(
        @RequestParam bookId: Long,
        @RequestParam borrowerId: Long,
    ): ResponseEntity<Any> {
        return try {
            val loan = service.registerLoan(bookId, borrowerId)
            val response = CreateLoanResponse(
                loanId = loan.id!!,
                bookTitle = loan.book.title,
                bookAuthor = loan.book.author,
                borrowTime = loan.borrowTime.toString(),
                returnTime = "",
            )
            //Successful loan returns status: 200(OK) with additional information
            ResponseEntity.ok().body(response)
        } catch (e: IllegalArgumentException) {
            //Book of Borrower not found returns status: 404(Not Found)
            ResponseEntity.status(404).body(mapOf("error" to e.message))
        } catch (e: IllegalStateException) {
            // Book already loaned returns status: 400(Bad Request)
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    @PatchMapping
    fun returnBook(
        @RequestParam loanId: Long
    ): ResponseEntity<Any> {
        return try {
            val loan = service.returnBook(loanId)

            val response = mapOf(
                "message" to "Book returned Successfully!",
                "loanId" to loanId,
                "bookTitle" to loan.book.title,
                "returnTime" to loan.returnTime.toString()
            )
            //Successful return, status 200(OK) with additional information
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            // Loan not found, returns status 400(Bad Request) with additional information from the service
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }

    @GetMapping
    fun getMyLoans(
        @RequestParam borrowerId: Long
    ): List<GetLoanResponse> {
        return service.getLoansByBorrower(borrowerId)

    }
}