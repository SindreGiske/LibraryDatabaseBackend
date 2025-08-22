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
        println("LoginController.login: $email attempted to log in.")
        val user: Borrower? = service.getUserByEmail(email)

        return if (user != null && user.password == password) {
            // Successful Login, return user(without password) and status:200 (OK)
            println("LoginController.login:200 $email logged inn successfully.")
            val safeUser = user.toDTO()
            ResponseEntity.ok().body(safeUser)
        } else {
            // Failed Login, returns status:401 with message
            println("LoginController.login:401 $email failed to log in.")
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
        return if (service.getUserByEmail(email) == null) {
            println("LoginController.createUser:201 $email created a new user.")
            //Successfully created user, returns user(without password) and status:201 (CREATED)
            val newUser: Borrower = (Borrower(name = name, email = email, password = password))
            println("LoginController.createUser:201 newUser: $newUser.")
            service.registerNew(newUser)
            val safeUser = newUser.toDTO()
            ResponseEntity.status(HttpStatus.CREATED).body(safeUser)
        } else {
            //Failed create because email already used, returns status:401 with message
            println("LoginController.createUser:401, $email failed to create a new user.")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("A user with this email already exists.")
        }
    }
}