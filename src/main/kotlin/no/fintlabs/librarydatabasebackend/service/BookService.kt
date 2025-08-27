package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.entity.Book
import no.fintlabs.librarydatabasebackend.repository.BookRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun addBook(title: String, author: String) =
        bookRepository.save(Book(title = title, author = author))

    fun searchBooks(input: String): List<Book> =
        bookRepository.searchBooksFullText(input)

    fun deleteBook(book: Book) = bookRepository.delete(book)

    fun getAllBooks(): List<Book> = bookRepository.findAll()

    fun getLoanedBooks(): List<Book> = bookRepository.listLoaned()

    fun getAvailableBooks(): List<Book> = bookRepository.listAvailableBooks()

    fun findBookById(id: Long): Book? = bookRepository.findByIdOrNull(id)

    fun findBooksByAuthor(author: String): List<Book?> = bookRepository.findByAuthor(author)
}