package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import no.fintlabs.librarydatabasebackend.dto.response.BookResponse
import java.util.UUID

@Entity
open class Book(
    @Id
    open val id: UUID,

    open var title: String,
    open var author: String,
    open var loaned: Boolean = false,

    private val loans: MutableList<UUID> = mutableListOf()
) {
    constructor(title: String, author: String) : this(
        id = UUID.randomUUID(),
        title = title,
        author = author,
        loaned = false,
    )

    fun loanBook(loanId: UUID) {
        loans.add(loanId)
        loaned = true
    }

    fun returnBook() {
        loaned = false
    }
    fun toResponse(): BookResponse = BookResponse( id, title, author, loaned )
}