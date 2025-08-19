package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.DTO.LoanResponse
import no.fintlabs.librarydatabasebackend.service.LoanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
        @RequestBody borrowerId: Long,
    ): ResponseEntity<Any> {
        return try {
            val loan = service.registerLoan(bookId, borrowerId)
            val response = LoanResponse(
                loanId = loan.id!!,
                bookTitle = loan.book.title,
                bookAuthor = loan.book.author,
                borrowTime = loan.borrowTime.toString()
            )
            //Successful loan! Returns status: 200(OK) with additional information
            ResponseEntity.ok().body(response)
        } catch (e: IllegalArgumentException) {
            //Book of Borrower not found returns status: 404(Not Found)
            ResponseEntity.status(404).body(mapOf("error" to e.message))
        } catch (e: IllegalStateException) {
            // Book already loaned returns status: 400(Bad Request)
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }
    }
}