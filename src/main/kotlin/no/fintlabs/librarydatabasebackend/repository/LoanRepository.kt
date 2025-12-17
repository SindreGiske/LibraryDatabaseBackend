package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.Loan
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository: JpaRepository<Loan, Long> {

    fun findByBookTitle(bookTitle: String): Loan?

    fun findByUserId(userId: Long): List<Loan>
}