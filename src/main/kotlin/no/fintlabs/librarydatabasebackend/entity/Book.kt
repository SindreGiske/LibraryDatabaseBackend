package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
open class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Suppress("unused")
    open val id: Long? = null

    open lateinit var title: String
    open lateinit var author: String
    open var loaned: Boolean? = false

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open val loans: MutableList<Loan> = mutableListOf()

    constructor()

    constructor(title: String, author: String) {
        this.title = title
        this.author = author
    }

    fun loanBook(loan: Loan) {
        loans.add(loan)
        loaned = true
    }

    fun returnBook() {
        loaned = false
    }
}