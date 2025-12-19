package no.fintlabs.librarydatabasebackend.unit

import no.fintlabs.librarydatabasebackend.DTO.mappers.toCreateResponse
import no.fintlabs.librarydatabasebackend.entity.Book
import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.entity.User
import no.fintlabs.librarydatabasebackend.repository.BookRepository
import no.fintlabs.librarydatabasebackend.repository.LoanRepository
import no.fintlabs.librarydatabasebackend.repository.UserRepository
import no.fintlabs.librarydatabasebackend.service.BookService
import no.fintlabs.librarydatabasebackend.service.LoanService
import no.fintlabs.librarydatabasebackend.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertNotNull
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoanTests {
    private val bookRepository: BookRepository = mock()
    private val userRepository: UserRepository = mock()
    private val loanRepository: LoanRepository = mock()

    private val bookService: BookService = BookService(bookRepository)
    private val userService: UserService = UserService(userRepository)
    private val loanService: LoanService = LoanService(loanRepository, bookService, userService)

    private lateinit var user: User
    private lateinit var book: Book

    @BeforeEach
    fun setup() {
        user = User(id = 1, name = "Test", email = "x", password = "y")
        book = Book(id = 1, title = "TestBook", author = "Author")

        val loanCaptor = argumentCaptor<Loan>()

        whenever(userService.findById(1)).thenReturn(user)
        whenever(bookService.findById(1)).thenReturn(book)
        whenever(loanRepository.save(loanCaptor.capture()))
            .thenAnswer {
                loanCaptor.firstValue.apply {
                    id = 1L
                }
            }
    }

    @Test
    fun `register loan creates and saves loan`() {
        val loan = loanService.registerLoan(book.id!!, user.id!!)

        println(loan.toCreateResponse())
        assertNotNull(loan)
        verify(loanRepository).save(any())
    }
}