package no.fintlabs.librarydatabasebackend.config

import no.fintlabs.librarydatabasebackend.service.AdminService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class AdminBootstrap(
    private val adminService: AdminService,
): CommandLineRunner {
    override fun run(vararg args: String?) {
        adminService.createAdminUser()
    }
}