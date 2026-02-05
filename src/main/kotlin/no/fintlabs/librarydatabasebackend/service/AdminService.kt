package no.fintlabs.librarydatabasebackend.service

import jakarta.transaction.Transactional
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
    private val passwordEncoder: PasswordEncoder
) {
    fun validateAdmin(userId: UUID): Boolean = users.isAdmin(userId)

    fun getAllUsers(): List<User> = users.getAllUsers()

    fun registerNewBook(title: String, author: String): Boolean {
        val check = books.searchBooks("$title $author")
        if (check.isEmpty()) {
            books.addNewBook(title = title, author = author)
            return true
        }
        else return false
    }

    fun getAllBooks(): List<Book> = books.getAllBooksAdmin()

    fun addAdmin(userId: UUID): HttpStatus {
        val user: User? = users.findById(userId)
        if (user == null) return HttpStatus.BAD_REQUEST
        else if (user.admin) return HttpStatus.ALREADY_REPORTED
        else {
            user.makeAdmin()
            return HttpStatus.ACCEPTED
        }
    }

    @Transactional
    fun createAdminUser() {
        if (users.adminExists()) return

            val theAdmin = User(
                name = "admin",
                email = "admin",
                passwordHash = passwordEncoder.encode("admin"),
                admin = true)

            users.registerNew(theAdmin)
    }

    fun getAllLoans(): List<Loan> {
        return loans.getAllLoans()
    }
}