package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import no.fintlabs.librarydatabasebackend.DTO.mappers.allLoansToResponse
import no.fintlabs.librarydatabasebackend.DTO.response.BookAdminResponse
import no.fintlabs.librarydatabasebackend.DTO.response.BookResponse

@Entity
open class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Suppress("unused")
    open val id: Long? = null,

    open var title: String,
    open var author: String,
    open var loaned: Boolean? = false,

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open val loans: MutableList<Loan> = mutableListOf()
){
    protected constructor() : this(
        id = null,
        title = "",
        author = "",
        loaned = false,
        loans = mutableListOf()
    )
    fun loanBook(loan: Loan) {
        loans.add(loan)
        loaned = true
    }

    fun returnBook() {
        loaned = false
    }

    fun toResponse(): BookResponse {
        return BookResponse(
            id!!,
            title,
            author,
            loaned
        )
    }

    fun toAdminResponse(): BookAdminResponse {
        return BookAdminResponse(
            id!!,
            title,
            author,
            loaned,
            loans = allLoansToResponse(loans)
        )
    }
}