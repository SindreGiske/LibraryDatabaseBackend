package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookRepository : JpaRepository<Book, Long> {
    fun findByTitle(title: String): Book?

    fun findByAuthor(author: String): List<Book?>

    @Query("SELECT b FROM Book b WHERE b.loaned = true")
    fun listLoaned(): List<Book>

    @Query("SELECT b FROM Book b WHERE b.loaned = false")
    fun listAvailableBooks(): List<Book>
}