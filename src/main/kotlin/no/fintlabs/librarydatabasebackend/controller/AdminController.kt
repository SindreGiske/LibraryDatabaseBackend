package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.DTO.request.BookRequest
import no.fintlabs.librarydatabasebackend.DTO.response.UserInfo
import no.fintlabs.librarydatabasebackend.service.AdminService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController (
    private val service: AdminService,
) {
    @GetMapping("/getUsers")
    fun getAllUsers(
        @RequestBody userId: Long
    ): ResponseEntity<List<UserInfo>> {
        return if (!service.validateAdmin(userId)) ResponseEntity(emptyList(), HttpStatus.UNAUTHORIZED)
        else {
            val users = service.getAllUsers().map { it.toDTO() }
            return ResponseEntity(users, HttpStatus.OK)
        }
    }

    @PostMapping("/registerNewBook")
    fun registerNewBook(@RequestBody request: BookRequest): ResponseEntity<String> {
        if (!service.validateAdmin(request.userId)) return ResponseEntity(HttpStatus.UNAUTHORIZED)

        val response: Boolean = service.registerNewBook(request.title, request.author)
        return if (response)
            ResponseEntity("${request.title} by ${request.author} added to Database",
             HttpStatus.CREATED)
        else ResponseEntity("Book already exists in database", HttpStatus.BAD_REQUEST)
    }

    @PatchMapping("/addAdmin")
    fun setAnotherUserAsAdmin(
        @RequestBody
        selfId: Long,
        subjectId: Long,
        ): HttpStatus {
        return if (!service.validateAdmin(selfId)) HttpStatus.UNAUTHORIZED
        else service.addAdmin(subjectId)
    }
}