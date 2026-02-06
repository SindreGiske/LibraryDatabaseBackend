package no.fintlabs.librarydatabasebackend.controller

import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.service.LoanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
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
    @Transactional
    fun createLoan(
        @RequestParam bookId: UUID,
        session: HttpSession,
    ): ResponseEntity<Any> =
        try {
            val userId = session.getAttribute("userId") as UUID
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
    @Transactional
    fun returnBook(
        @RequestParam loanId: UUID,
        session: HttpSession,
    ): ResponseEntity<Any> {
        val userId = session.getAttribute("userId") as UUID
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
    fun getMyLoans(session: HttpSession): List<Loan> = service.getLoansByUser(session.getAttribute("userId") as UUID)
}
