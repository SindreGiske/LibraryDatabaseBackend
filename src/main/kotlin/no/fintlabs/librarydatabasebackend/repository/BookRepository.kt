package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface BookRepository : JpaRepository<Book, UUID> {

    @Query("""
        SELECT b FROM Book b
        WHERE lower(b.title) LIKE lower(concat('%', :query, '%'))
           OR lower(b.author) LIKE lower(concat('%', :query, '%'))
    """)
    fun search(@Param("query") query: String): List<Book>

    fun getBookById(bookId: UUID): Book?

    @Query("SELECT b FROM Book b WHERE b.loaned = true")
    fun listLoaned(): List<Book>

    @Query("SELECT b FROM Book b WHERE b.loaned = false")
    fun listAvailableBooks(): List<Book>
}