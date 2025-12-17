package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.DTO.response.GetLoanResponse
import no.fintlabs.librarydatabasebackend.DTO.mappers.toGetResponse
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
        val book = bookService.findBookById(bookId)
            ?: throw IllegalArgumentException("Book with ID $bookId not found")
        if (book.loaned == true) throw IllegalStateException("Book is currently unavailable")

        val user = userService.findById(userId)
            ?: throw IllegalArgumentException("Borrower with ID $userId not found")

        val loan = Loan(
            book = book,
            user = user,
            borrowTime = LocalDateTime.now(),
        )
        book.loanBook(loan)
        user.loans.add(loan)

        return loanRepository.save(loan)
    }

    fun returnBook(loanId: Long): Loan {
        val loan = loanRepository.findById(loanId)
            .orElseThrow { IllegalArgumentException("Loan $loanId not found") }

        loan.returnTime = LocalDateTime.now()
        loan.book.returnBook(loan)
        loan.user?.loans?.remove(loan)

        return loanRepository.save(loan)
    }

    fun getLoansByUser(userId: Long): List<GetLoanResponse> {
        val loans: List<Loan> = loanRepository.findByUserId(userId)
        return loans.map { it.toGetResponse() }
    }
}