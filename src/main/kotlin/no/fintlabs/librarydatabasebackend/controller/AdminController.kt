package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.entity.Borrower
import no.fintlabs.librarydatabasebackend.service.BookService
import no.fintlabs.librarydatabasebackend.service.BorrowerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController (
    private val borrowers: BorrowerService,
    private val bookService: BookService
) {
    @GetMapping
    fun getAllUsers(): List<Borrower> = borrowers.getAllUsers()

    @PostMapping("/book")
    fun registerNewBook(@RequestBody title: String, author: String) {
        bookService.addBook(title, author)
    }

}