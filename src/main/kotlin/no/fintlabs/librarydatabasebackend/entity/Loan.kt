package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
open class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id")
    open var borrower: Borrower? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    open val book: Book,

    open val borrowTime: LocalDateTime = LocalDateTime.now(),

    open var returnTime: LocalDateTime? = null
)

