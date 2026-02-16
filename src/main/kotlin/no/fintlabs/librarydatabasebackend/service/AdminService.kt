package no.fintlabs.librarydatabasebackend.service

import jakarta.transaction.Transactional
import no.fintlabs.librarydatabasebackend.DTO.response.AdminOverviewType
import no.fintlabs.librarydatabasebackend.entity.Book
import no.fintlabs.librarydatabasebackend.entity.Loan
import no.fintlabs.librarydatabasebackend.entity.User
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AdminService(
    private val loans: LoanService,
    private val users: UserService,
    private val books: BookService,
    private val passwordEncoder: PasswordEncoder,
) {
    fun validateAdmin(userId: UUID): Boolean {
        val valid = users.isAdmin(userId)
        if (!valid) {
            println("User $userId tried to perform an admin action.")
        }
        return valid
    }

    fun overview(): AdminOverviewType =
        AdminOverviewType(
            userCount = users.getAmountOfUsers(),
            totalBookCount = books.getAmountOfBooks(),
            availableBooksCount = books.getAmountOfAvailableBooks(),
            totalLoanCount = loans.getAmountOfLoans(),
            activeLoanCount = loans.getAmountOfActiveLoans(),
        )

    fun getAllUsers(): List<User> = users.getAllUsers()

    fun registerNewBook(
        title: String,
        author: String,
        description: String,
    ): Boolean {
        val check = books.searchBooks("$title $author")
        if (check.isEmpty()) {
            books.addNewBook(title = title, author = author, description = description)
            return true
        } else {
            return false
        }
    }

    fun getAllBooks(): List<Book> = books.getAllBooksAdmin()

    fun addAdmin(userId: String): HttpStatus {
        val user: User? = users.findById(UUID.fromString(userId))
        if (user == null) {
            return HttpStatus.BAD_REQUEST
        } else if (user.admin) {
            return HttpStatus.ALREADY_REPORTED
        } else {
            user.makeAdmin()
            return HttpStatus.ACCEPTED
        }
    }

    fun getSpecificUsersLoans(subjectId: String): List<Loan> = loans.getLoansByUser(UUID.fromString(subjectId))

    @Transactional
    fun createAdminUser() {
        if (users.adminExists()) return

        val theAdmin =
            User(
                name = "admin",
                email = "admin",
                passwordHash = passwordEncoder.encode("admin"),
                admin = true,
            )

        users.registerNew(theAdmin)
    }

    fun getAllLoans(): List<Loan> = loans.getAllLoans()
}
