package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookRepository : JpaRepository<Book, Long> {

    @Query(
        value = """
            SELECT * FROM book
            WHERE MATCH(title, author) AGAINST (?1 IN BOOLEAN MODE)
        """,
        nativeQuery = true
    )
    fun searchBooksFullText(query: String): List<Book>

    fun findByAuthor(author: String): List<Book?>

    @Query("SELECT b FROM Book b WHERE b.loaned = true")
    fun listLoaned(): List<Book>

    @Query("SELECT b FROM Book b WHERE b.loaned = false")
    fun listAvailableBooks(): List<Book>
}