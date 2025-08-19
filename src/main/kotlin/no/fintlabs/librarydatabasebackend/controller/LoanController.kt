package no.fintlabs.librarydatabasebackend.controller

import no.fintlabs.librarydatabasebackend.service.LoanService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/loan")
class LoanController (
    private val service: LoanService,
) {

}