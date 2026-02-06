package no.fintlabs.librarydatabasebackend.controller

import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import no.fintlabs.librarydatabasebackend.DTO.request.CreateUserRequest
import no.fintlabs.librarydatabasebackend.DTO.request.LoginRequest
import no.fintlabs.librarydatabasebackend.entity.User
import no.fintlabs.librarydatabasebackend.service.LoanService
import no.fintlabs.librarydatabasebackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
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
    private val loans: LoanService,
    private val passwordEncoder: PasswordEncoder,
) {
    @PostMapping()
    @Transactional
    fun login(
        @RequestBody request: LoginRequest,
        session: HttpSession,
    ): ResponseEntity<Any> {
        val email = request.email
        val password = request.password
        println("LoginController.login: $email attempted to log in.")
        val user: User? = service.getUserByEmail(email)

        return if (user != null &&
            passwordEncoder.matches(password, user.passwordHash)
        ) {
            // Successful Login, return user(without password) and status:200 (OK)
            println("LoginController.login:200 $email logged inn successfully.")

            session.setAttribute("userId", user.id)
            session.setAttribute("isAdmin", user.admin)

            ResponseEntity.ok().body(user)
        } else {
            // Failed Login, returns status:401 with message
            println("LoginController.login:401 $email failed to log in.")
            ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password")
        }
    }

    @PostMapping("/logout")
    fun logout(session: HttpSession): ResponseEntity<Any> {
        session.invalidate()
        return ResponseEntity.ok().build()
    }

    @PostMapping("/register")
    @Transactional
    fun register(
        @RequestBody request: CreateUserRequest,
        session: HttpSession,
    ): ResponseEntity<Any> =
        if (service.getUserByEmail(request.email) == null) {
            println("LoginController.createUser:201 ${request.email} created a new user.")
            // Successfully created user, returns user(without password) and status:201 (CREATED)
            val newUser: User = (
                User(
                    name = request.name,
                    email = request.email,
                    passwordHash = passwordEncoder.encode(request.password),
                    admin = false,
                )
            )
            println("LoginController.createUser:201 newUser: $newUser.")
            service.registerNew(newUser)
            // After successful create, get new user from repo to include ID in return
            val user = service.getUserByEmail(request.email)
            session.setAttribute("userId", user!!.id)
            session.setAttribute("isAdmin", user.admin)
            ResponseEntity.status(HttpStatus.CREATED).body(user)
        } else {
            // Failed create because email already used, returns status:401 with message
            println("LoginController.createUser:401, ${request.email} failed to create a new user.")
            ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("A user with this email already exists.")
        }

    @DeleteMapping("/delete")
    @Transactional
    fun deleteSelf(session: HttpSession): ResponseEntity<HttpStatus> {
        val userId = session.getAttribute("userId") as UUID
        val user: User? = service.findById(userId)
        if (user != null) {
            if (loans.validateAllBooksReturned(user.id)) {
                service.deleteUserById(userId)
                session.invalidate()
                // If user has successfully been deleted, return 410 GONE
                return ResponseEntity(HttpStatus.GONE)
            } else {
                return ResponseEntity(HttpStatus.FORBIDDEN)
            }
            // Users can not delete their accounts if they have not returned all the books they've loaned; 403
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        // User not found; 404
    }
}
