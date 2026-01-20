package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.Loan
import org.springframework.data.jpa.repository.JpaRepository

interface LoanRepository: JpaRepository<Loan, Long> {

    fun findByUserId(userId: Long): List<Loan>

    fun findLoanById(loanId: Long): Loan?
}