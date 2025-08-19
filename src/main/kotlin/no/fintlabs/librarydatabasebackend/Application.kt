package no.fintlabs.librarydatabasebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryDatabaseBackendApplication

fun main(args: Array<String>) {
    runApplication<LibraryDatabaseBackendApplication>(*args)
}
