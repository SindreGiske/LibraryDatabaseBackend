package no.fintlabs.librarydatabasebackend.service

import no.fintlabs.librarydatabasebackend.entity.Borrower
import no.fintlabs.librarydatabasebackend.repository.BorrowerRepository
import org.springframework.stereotype.Service

@Service
class BorrowerService(
    private val repository: BorrowerRepository,
) {
    fun findById(id: Long): Borrower? = repository.getBorrowerById(id)

    fun getUserByEmail(email: String): Borrower? = repository.getUserByEmail(email)

    fun registerNew(borrower: Borrower): Borrower =
        repository.save(Borrower( name = borrower.name, email = borrower.email, password = borrower.password ))
}