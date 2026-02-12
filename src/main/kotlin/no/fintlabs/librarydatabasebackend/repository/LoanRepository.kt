package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.Loan
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LoanRepository : JpaRepository<Loan, UUID> {
    fun findByUsername(username: String): List<Loan>

    fun findLoanById(loanId: UUID): Loan?

    fun countLoansByActiveTrue(): Long
}
