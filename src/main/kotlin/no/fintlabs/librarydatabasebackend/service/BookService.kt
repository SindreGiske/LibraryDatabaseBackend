package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.entity.Book
import no.fintlabs.librarydatabasebackend.repository.BookRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun addBook(book: Book) = bookRepository.save(Book(title = book.title, author = book.author))

    fun deleteBook(book: Book) = bookRepository.delete(book)

    fun getAllBooks(): List<Book> = bookRepository.findAll()

    fun getLoanedBooks(): List<Book> = bookRepository.listLoaned()

    fun getAvailableBooks(): List<Book> = bookRepository.listAvailableBooks()

    fun findBookById(id: Long): Book? = bookRepository.findByIdOrNull(id)

    fun findBookByTitle(title: String): Book? = bookRepository.findByTitle(title)

    fun findBooksByAuthor(author: String): List<Book?> = bookRepository.findByAuthor(author)

    fun loanBook(title: String) = {
        val book = bookRepository.findByTitle(title)
        bookRepository.save(book.loaned = true)
    }
}