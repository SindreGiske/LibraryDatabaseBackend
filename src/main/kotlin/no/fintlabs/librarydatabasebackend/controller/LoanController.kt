package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.service.LoanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/loan")
class LoanController(
    private val service: LoanService,
) {
    @PostMapping
    fun createLoan(
        @RequestBody userId: UUID,
        @RequestParam bookId: UUID,
    ): ResponseEntity<Any> =
        try {
            val loan = service.registerLoan(bookId, userId)
            // Successful loan returns status: 200(OK) with additional information
            ResponseEntity.ok().body(loan)
        } catch (e: IllegalArgumentException) {
            // Book or User not found returns status: 404(Not Found)
            ResponseEntity.status(404).body(mapOf("error" to e.message))
        } catch (e: IllegalStateException) {
            // Book already loaned returns status: 400(Bad Request)
            ResponseEntity.status(400).body(mapOf("error" to e.message))
        }

    @PatchMapping("/return")
    fun returnBook(
        @RequestParam userId: UUID,
        @RequestParam loanId: UUID,
    ): ResponseEntity<Any> {
        if (service.validateLoanOwner(userId, loanId)) {
            return ResponseEntity.badRequest().body(null)
        }
        return try {
            service.returnBook(loanId)
            val response =
                mapOf(
                    "message" to "Book returned Successfully!",
                    "loanId" to loanId,
                )
            // Successful return, status 200(OK) with additional information
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            // Loan not found, returns status 400(Bad Request) with additional information from the service
            ResponseEntity.status(400).body(mapOf("Bad Request" to e.message))
        }
    }

    @GetMapping("/getMyLoans")
    fun getMyLoans(
        @RequestParam userId: UUID,
    ): List<Loan> = service.getLoansByUser(userId)
}
