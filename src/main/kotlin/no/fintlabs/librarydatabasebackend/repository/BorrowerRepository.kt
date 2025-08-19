package no.fintlabs.librarydatabasebackend.repository

import no.fintlabs.librarydatabasebackend.entity.Borrower
import org.springframework.data.jpa.repository.JpaRepository

interface BorrowerRepository : JpaRepository<Borrower, Long> {

    fun getBorrowerById(id: Long): Borrower?

    fun getBorrowerByName(name: String): Borrower?

    fun getUserByEmail(email: String): Borrower?

}
