package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.dto.response.BookResponse
import no.fintlabs.librarydatabasebackend.service.BookService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController(
    private val service : BookService,
) {
    @GetMapping("/all")
    fun getAllBooks(): List<BookResponse> {
    val books: List<BookResponse> = service.getAllBooks()

        println(books)
        return books
    }

    @GetMapping("/available")
    fun getAvailableBooks() = service.getAvailableBooks()

    @GetMapping("/search")
    fun searchBooks(
        @RequestBody input: String,
    ) = service.searchBooks(input)
}