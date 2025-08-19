package no.fintlabs.librarydatabasebackend.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class Borrower(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String,

    @OneToMany(mappedBy = "borrower",  cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val loans: MutableList<Loan> = mutableListOf(),
)
