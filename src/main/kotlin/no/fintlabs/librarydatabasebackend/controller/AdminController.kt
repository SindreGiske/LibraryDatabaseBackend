package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.entity.User
import no.fintlabs.librarydatabasebackend.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/admin")
class AdminController (
    private val service: AdminService,
) {
    @PostMapping("/registerNewBook")
    fun registerNewBook(@RequestBody userId: UUID, title: String, author: String): ResponseEntity<String> {
        if (!service.validateAdmin(userId)) return ResponseEntity(HttpStatus.UNAUTHORIZED)

        val response: Boolean = service.registerNewBook(title, author)
        return if (response)
            ResponseEntity("$title by $author added to Database",
                HttpStatus.CREATED)
        else ResponseEntity("Book already exists in database", HttpStatus.BAD_REQUEST)
    }

    @GetMapping("/getUsers")
    fun getAllUsers(
        @RequestBody userId: UUID
    ): ResponseEntity<List<User>> {
        return if (!service.validateAdmin(userId)) ResponseEntity(emptyList(), HttpStatus.UNAUTHORIZED)
        else {
            val users = service.getAllUsers()
            return ResponseEntity(users, HttpStatus.OK)
        }
    }

    @GetMapping("/getAllLoans")
    fun getAllLoans(
        @RequestBody userId: UUID
    ): ResponseEntity<Any> {
        return (if (!service.validateAdmin(userId)) ResponseEntity(emptyList<Any>(), HttpStatus.UNAUTHORIZED)
        else ResponseEntity(service.getAllLoans(), HttpStatus.OK) )
    }

    @GetMapping("/getAllBooks")
    fun getAllBooksFull(
        @RequestBody userId: UUID
    ): ResponseEntity<Any> {
        return if (!service.validateAdmin(userId)) ResponseEntity("", HttpStatus.UNAUTHORIZED)
        else ResponseEntity(service.getAllBooks(), HttpStatus.OK)
    }

    @PatchMapping("/addAdmin")
    fun setAnotherUserAsAdmin(
        @RequestBody
        selfId: UUID,
        subjectId: UUID,
        ): HttpStatus {
        return if (!service.validateAdmin(selfId)) HttpStatus.UNAUTHORIZED
        else service.addAdmin(subjectId)
    }
}