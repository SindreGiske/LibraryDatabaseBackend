package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.entity.Borrower
import no.fintlabs.librarydatabasebackend.service.BorrowerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val service: BorrowerService,
) {

    @GetMapping()
    fun login(
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<Any> {
        val user: Borrower? = service.getUserByEmail(email)

        return if (user != null && user.password == password) {
            // Successful Login, return user(without password) and status:200 (OK)
            val safeUser = user.copy(password = "")
            ResponseEntity.ok().body(safeUser)
        } else {
            // Failed Login, returns status:401 with message
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password")
        }
    }

    @PostMapping()
    fun createUser(
        @RequestParam name: String,
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<Any> {
        return if (service.getUserByEmail(email) != null) {
            //Successfully created user, returns user(without password) and status:201 (CREATED)
            val newUser: Borrower = (Borrower(name = name, email = email, password = password))
            service.registerNew(newUser)
            val safeUser = newUser.copy(password = "")
            ResponseEntity.status(HttpStatus.CREATED).body(safeUser)
        } else {
            //Failed create because email already used, returns status:401 with message
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("A user with this email already exists.")
        }
    }
}