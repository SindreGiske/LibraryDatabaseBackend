package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.sql.Timestamp
import java.time.LocalDateTime

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val title: String,
    val author: String,
    var loaned: Boolean? = false,

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val loans: MutableList<Loan> = mutableListOf(),
    ) {
    fun loanBook(loan: Loan) {
        loans.add(loan)
        loaned = true
    }

    fun returnBook(loan: Loan) {
        loans.find { it.id == loan.id }?.let { it.returnTime = Timestamp.valueOf(LocalDateTime.now()) }
        loaned = false
    }
}