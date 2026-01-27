package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
open class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    open var username: String? = null,

    open val bookId: Long,
    open val title: String,
    open val author: String,

    open val borrowTime: LocalDateTime = LocalDateTime.now(),
    open var returnTime: LocalDateTime? = null,

    open var active: Boolean = returnTime != null,
)

