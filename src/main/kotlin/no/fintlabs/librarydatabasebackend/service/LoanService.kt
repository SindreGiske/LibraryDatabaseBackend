package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.DTO.mappers.allLoansToResponse
import no.fintlabs.librarydatabasebackend.DTO.response.GetAllLoansResponse
import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.repository.LoanRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LoanService(
    private val loanRepository: LoanRepository,
    private val bookService: BookService,
    private val userService: UserService,
) {
    fun registerLoan(bookId: Long, userId: Long): Loan {
        val book = bookService.findById(bookId)
            ?: throw IllegalArgumentException("Book with ID $bookId not found")
        if (book.loaned == true) throw IllegalStateException("Book is currently unavailable")

        val user = userService.findById(userId)
            ?: throw IllegalArgumentException("User with ID $userId not found")

        val loan = Loan(
            book = book,
            user = user,
            username = user.name,
            borrowTime = LocalDateTime.now(),
        )
        book.loanBook(loan)
        user.loans.add(loan)

        return loanRepository.save(loan)
    }

    fun returnBook(userId: Long, loanId: Long): Loan {
        val loan = loanRepository.findById(loanId)
            .orElseThrow { IllegalArgumentException("Loan $loanId not found") }
        val user = userService.findById(userId)
            ?: throw IllegalArgumentException("User with ID $userId not found")

        loan.returnTime = LocalDateTime.now()
        loan.book!!.returnBook()

        loan.user = null
        return loanRepository.save(loan)
    }

    fun getLoansByUser(userId: Long): GetAllLoansResponse {
        val loans: List<Loan> = loanRepository.findByUserId(userId)
        return allLoansToResponse(loans)
    }

    fun getAllLoans(): List<Loan> = loanRepository.findAll()
}