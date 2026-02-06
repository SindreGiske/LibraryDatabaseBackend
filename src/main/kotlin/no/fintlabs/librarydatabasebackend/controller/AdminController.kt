package no.fintlabs.librarydatabasebackend.controller

import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
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
class AdminController(
    private val service: AdminService,
) {
    @PostMapping("/registerNewBook")
    fun registerNewBook(
        @RequestBody
        title: String,
        author: String,
        description: String,
        session: HttpSession,
    ): ResponseEntity<String> {
        val userId = session.getAttribute("userId") as UUID
        if (!service.validateAdmin(userId)) return ResponseEntity(HttpStatus.UNAUTHORIZED)

        val response: Boolean = service.registerNewBook(title, author, description)
        return if (response) {
            ResponseEntity(
                "$title by $author added to Database",
                HttpStatus.CREATED,
            )
        } else {
            ResponseEntity("Book already exists in database", HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/getUsers")
    fun getAllUsers(session: HttpSession): ResponseEntity<List<User>> {
        val userId = session.getAttribute("userId") as UUID
        return if (!service.validateAdmin(userId)) {
            ResponseEntity(emptyList(), HttpStatus.UNAUTHORIZED)
        } else {
            val users = service.getAllUsers()
            return ResponseEntity(users, HttpStatus.OK)
        }
    }

    @GetMapping("/getAllLoans")
    fun getAllLoans(session: HttpSession): ResponseEntity<Any> {
        val userId = session.getAttribute("userId") as UUID
        return (
            if (!service.validateAdmin(userId)) {
                ResponseEntity(emptyList<Any>(), HttpStatus.UNAUTHORIZED)
            } else {
                ResponseEntity(service.getAllLoans(), HttpStatus.OK)
            }
        )
    }

    @GetMapping("/getAllBooks")
    fun getAllBooksFull(session: HttpSession): ResponseEntity<Any> {
        val userId = session.getAttribute("userId") as UUID
        return if (!service.validateAdmin(userId)) {
            ResponseEntity("", HttpStatus.UNAUTHORIZED)
        } else {
            ResponseEntity(service.getAllBooks(), HttpStatus.OK)
        }
    }

    @PatchMapping("/addAdmin")
    @Transactional
    fun setAnotherUserAsAdmin(
        @RequestBody
        subjectId: UUID,
        session: HttpSession,
    ): HttpStatus {
        val userId = session.getAttribute("userId") as UUID
        return if (!service.validateAdmin(userId)) {
            HttpStatus.UNAUTHORIZED
        } else {
            service.addAdmin(subjectId)
        }
    }
}
