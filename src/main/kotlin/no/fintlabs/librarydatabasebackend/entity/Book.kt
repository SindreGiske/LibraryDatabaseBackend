package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import no.fintlabs.librarydatabasebackend.DTO.response.BookResponse
import java.util.UUID

@Entity
open class Book(
    @Id
    open val id: UUID? = null,
    open var title: String = "",
    open var author: String = "",
    @Column(length = 1000)
    open var description: String = "",
    open var loaned: Boolean = false,
    private val loans: MutableList<UUID> = mutableListOf(),
) {
    constructor(title: String, author: String, description: String) : this(
        id = UUID.randomUUID(),
        title = title,
        author = author,
        description = description,
        loaned = false,
    )

    fun loanBook(loanId: UUID) {
        loans.add(loanId)
        loaned = true
    }

    fun returnBook() {
        loaned = false
    }

    fun toResponse(): BookResponse = BookResponse(id!!, title, author, description, loaned)
}
