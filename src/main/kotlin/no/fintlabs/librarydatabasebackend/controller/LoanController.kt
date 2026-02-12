package no.fintlabs.librarydatabasebackend.controller

import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.service.LoanService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
        @RequestBody bookId: String,
        session: HttpSession,
    ): ResponseEntity<Any> =
        try {
            val bookUUID = UUID.fromString(bookId)
            val userId = session.getAttribute("userId") as UUID
            val loan = service.registerLoan(bookUUID, userId)
            // Successful loan returns status: 200(OK) with additional information
            println("LOAN CREATED: ${loan.username} loaned ${loan.title} by ${loan.author}")
            ResponseEntity.ok().build()
        } catch (e: IllegalArgumentException) {
            // Book or User not found returns status: 404(Not Found)
            ResponseEntity.status(404).body(e.message)
        } catch (e: IllegalStateException) {
            // Book already loaned returns status: 400(Bad Request)
            ResponseEntity.status(400).body(e.message)
        }

    @PatchMapping("/return")
    @Transactional
    fun returnBook(
        @RequestBody loanId: String,
        session: HttpSession,
    ): ResponseEntity<Any> {
        val loanUUID = UUID.fromString(loanId)
        val userId = session.getAttribute("userId") as UUID
        if (!service.validateLoanOwner(loanUUID, userId)) {
            return ResponseEntity.status(403).body("You cannot return others' loans.")
        }
        try {
            service.returnBook(loanUUID)
            // Successful return, status 200(OK) with additional information
            return ResponseEntity.ok("Book returned successfully!")
        } catch (e: IllegalArgumentException) {
            // Loan not found, returns status 400(Bad Request) with additional information from the service
            return ResponseEntity.status(400).body(e.message)
        }
    }

    @GetMapping("/getMyLoans")
    fun getMyLoans(session: HttpSession): List<Loan> = service.getLoansByUser(session.getAttribute("userId") as UUID)
}
