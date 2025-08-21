package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.entity.Borrower
import no.fintlabs.librarydatabasebackend.service.BorrowerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController (
    private val borrowers: BorrowerService
) {
    @GetMapping
    fun getAllUsers(): List<Borrower> = borrowers.getAllUsers()
}