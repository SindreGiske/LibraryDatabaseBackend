package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.DTO.GetLoanResponse
import no.fintlabs.librarydatabasebackend.DTO.mappers.toGetResponse
import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.repository.LoanRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LoanService(
    private val loanRepository: LoanRepository,
    private val bookService: BookService,
    private val borrowerService: BorrowerService,
) {
    fun registerLoan(bookId: Long, borrowerId: Long): Loan {
        val book = bookService.findBookById(bookId)
            ?: throw IllegalArgumentException("Book with ID $bookId not found")
        if (book.loaned == true) throw IllegalStateException("Book is currently unavailable")

        val borrower = borrowerService.findById(borrowerId)
            ?: throw IllegalArgumentException("Borrower with ID $borrowerId not found")

        val loan = Loan(
            book = book,
            borrower = borrower,
            borrowTime = LocalDateTime.now(),
        )
        book.loanBook(loan)
        borrower.loans.add(loan)

        return loanRepository.save(loan)
    }

    fun returnBook(loanId: Long): Loan {
        val loan = loanRepository.findById(loanId)
            .orElseThrow { IllegalArgumentException("Loan $loanId not found") }

        loan.returnTime = LocalDateTime.now()
        loan.book.returnBook(loan)

        return loanRepository.save(loan)
    }

    fun getLoansByBorrower(borrowerId: Long): List<GetLoanResponse> {
        val loans: List<Loan> = loanRepository.findByBorrowerId(borrowerId)
        return loans.map { it.toGetResponse() }
    }
}