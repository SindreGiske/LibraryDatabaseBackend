package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/books")
class BookController(
    private val service : BookService,
) {
    @GetMapping("/all")
    fun getAllBooks() = service.getAllBooks()

    @GetMapping("/available")
    fun getAvailableBooks() = service.getAvailableBooks()

    @GetMapping("/search")
    fun searchBooks(
        @RequestParam input: String,
    ) = service.searchBooks(input)
}