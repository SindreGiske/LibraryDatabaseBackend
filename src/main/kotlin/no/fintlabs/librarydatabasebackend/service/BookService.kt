package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.dto.response.BookResponse
import no.fintlabs.librarydatabasebackend.entity.Book
import no.fintlabs.librarydatabasebackend.repository.BookRepository
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun addNewBook(title: String, author: String) =
        bookRepository.save(Book(title = title, author = author))

    fun searchBooks(input: String): List<Book> =
        bookRepository.search(input)

    fun getAllBooks(): List<BookResponse>
     = bookRepository.findAll().toList().map { book -> book.toResponse() }

    fun getAllBooksAdmin(): List<Book> = bookRepository.findAll()

    fun getAvailableBooks(): List<Book> = bookRepository.listAvailableBooks()

    fun findById(id: UUID): Book? = bookRepository.getBookById(id)
}