package no.fintlabs.librarydatabasebackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
open class Loan(
    @Id
    open val id: UUID,
    open val username: String,
    @JsonIgnore
    open val bookId: UUID,
    open val title: String,
    open val author: String,
    open val borrowTime: LocalDateTime = LocalDateTime.now(),
    open var returnTime: LocalDateTime? = null,
    open var active: Boolean = returnTime != null,
) {
    constructor(username: String, bookId: UUID, title: String, author: String) :
        this(
            id = UUID.randomUUID(),
            username = username,
            bookId = bookId,
            title = title,
            author = author,
            borrowTime = LocalDateTime.now(),
        )
}
