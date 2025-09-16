package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.DTO.request.CreateUserRequest
import no.fintlabs.librarydatabasebackend.DTO.request.LoginRequest
import no.fintlabs.librarydatabasebackend.entity.Borrower
import no.fintlabs.librarydatabasebackend.service.BorrowerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class LoginController(
    private val service: BorrowerService,
) {
    @PostMapping()
    fun login(
        @RequestBody request: LoginRequest
    ): ResponseEntity<Any> {
        val email = request.email
        val password = request.password
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

    @PostMapping("/register")
    fun createUser(
        @RequestBody request: CreateUserRequest
    ): ResponseEntity<Any> {
        return if (service.getUserByEmail(request.email) == null) {
            println("LoginController.createUser:201 ${request.email} created a new user.")
            //Successfully created user, returns user(without password) and status:201 (CREATED)
            val newUser: Borrower = (Borrower(name = request.name, email = request.email, password = request.password))
            println("LoginController.createUser:201 newUser: $newUser.")
            service.registerNew(newUser)
            val safeUser = newUser.toDTO()
            ResponseEntity.status(HttpStatus.CREATED).body(safeUser)
        } else {
            //Failed create because email already used, returns status:401 with message
            println("LoginController.createUser:401, ${request.email} failed to create a new user.")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("A user with this email already exists.")
        }
    }
}