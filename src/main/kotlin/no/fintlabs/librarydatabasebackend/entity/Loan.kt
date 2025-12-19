package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
open class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    open var user: User? = null,

    open var username: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    open var book: Book? = null,

    open val borrowTime: LocalDateTime = LocalDateTime.now(),

    open var returnTime: LocalDateTime? = null
)

