package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
data class Loan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val borrower: Borrower,

    @ManyToOne(fetch = FetchType.LAZY)
    val book: Book,

    val borrowTime: Timestamp = Timestamp.valueOf(LocalDateTime.now()),
    var returnTime: Timestamp? = null
)