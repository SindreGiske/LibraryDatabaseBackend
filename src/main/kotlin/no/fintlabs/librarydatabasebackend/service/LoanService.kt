package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.repository.LoanRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class LoanService(
    private val loanRepository: LoanRepository,
    private val bookService: BookService,
    private val userService: UserService,
) {
    fun registerLoan(
        bookId: UUID,
        userId: UUID,
    ): Loan {
        val book =
            bookService.findById(bookId)
                ?: throw IllegalArgumentException("Book with ID $bookId not found")
        if (book.loaned) throw IllegalStateException("Book is currently unavailable")
        val user =
            userService.findById(userId)
                ?: throw IllegalArgumentException("User with ID $userId not found")

        val loan =
            Loan(
                username = user.name,
                bookId = book.id,
                title = book.title,
                author = book.author,
            )

        user.loans.add(loan.id)
        book.loanBook(loan.id)
        return loanRepository.save(loan)
    }

    fun returnBook(loanId: UUID): Loan {
        val loan =
            loanRepository
                .findById(loanId)
                .orElseThrow { IllegalArgumentException("Loan $loanId not found") }
        val book =
            bookService.findById(loan.bookId)
                ?: throw IllegalArgumentException("Book with ID ${loan.bookId} not found")

        loan.returnTime = LocalDateTime.now()
        book.returnBook()

        return loanRepository.save(loan)
    }

    fun getLoansByUser(userId: UUID): List<Loan> {
        val user = userService.findById(userId)
        return loanRepository.findByUsername(user!!.name)
    }

    fun validateLoanOwner(
        loanId: UUID,
        userId: UUID,
    ): Boolean {
        val user =
            userService.findById(userId)
                ?: throw IllegalArgumentException("User with ID $userId not found")
        val loan = loanRepository.findById(loanId).get()

        return loan.username == user.name
    }

    fun validateAllBooksReturned(id: UUID): Boolean {
        val userLoans: List<Loan> = getLoansByUser(id)

        for (loan in userLoans) {
            if (loan.active) return false
        }
        return true
    }

    fun findLoanById(id: UUID): Loan? = loanRepository.findLoanById(id)

    fun getAllLoans(): List<Loan> = loanRepository.findAll()
}
