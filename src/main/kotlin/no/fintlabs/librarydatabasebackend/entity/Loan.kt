package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
open class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    open var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    open var book: Book = Book(),

    open val borrowTime: LocalDateTime = LocalDateTime.now(),

    open var returnTime: LocalDateTime? = null
)

