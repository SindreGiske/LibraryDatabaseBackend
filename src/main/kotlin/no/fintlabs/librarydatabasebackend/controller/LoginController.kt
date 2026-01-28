package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.dto.request.CreateUserRequest
import no.fintlabs.librarydatabasebackend.dto.request.LoginRequest
import no.fintlabs.librarydatabasebackend.entity.User
import no.fintlabs.librarydatabasebackend.service.LoanService
import no.fintlabs.librarydatabasebackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/login")
class LoginController(
    private val service: UserService,
    private val loans: LoanService
) {
    @PostMapping()
    fun login(
        @RequestBody request: LoginRequest
    ): ResponseEntity<Any> {
        val email = request.email
        val password = request.password
        println("LoginController.login: $email attempted to log in.")
        val user: User? = service.getUserByEmail(email)

        return if (user != null && user.validatePassword(password)) {
            // Successful Login, return user(without password) and status:200 (OK)
            println("LoginController.login:200 $email logged inn successfully.")
            ResponseEntity.ok().body(user)
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
            // Successfully created user, returns user(without password) and status:201 (CREATED)
            val newUser: User = (User(name = request.name, email = request.email, password = request.password, admin = false))
            println("LoginController.createUser:201 newUser: $newUser.")
            service.registerNew(newUser)
            // After successful create, get new user from repo to include ID in return
            val user = service.getUserByEmail(request.email)
            ResponseEntity.status(HttpStatus.CREATED).body(user)
        } else {
            // Failed create because email already used, returns status:401 with message
            println("LoginController.createUser:401, ${request.email} failed to create a new user.")
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("A user with this email already exists.")
        }
    }

    @DeleteMapping("/delete/{id}")
    fun deleteSelf(@PathVariable id: UUID): ResponseEntity<HttpStatus> {
        val user: User? = service.findById(id)
        if (user != null) {
            if (loans.validateAllBooksReturned(user.id)) {
            service.deleteUserById(id)
                // If user has successfully been deleted, return 410 GONE
                return ResponseEntity(HttpStatus.GONE)
            }
            else return ResponseEntity(HttpStatus.FORBIDDEN)
            // Users can not delete their accounts if they have not returned all the books they've loaned; 403
        }
        else return ResponseEntity(HttpStatus.NOT_FOUND)
        // User not found; 404
    }
}