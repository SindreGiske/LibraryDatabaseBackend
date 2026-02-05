package no.fintlabs.librarydatabasebackend.config

import no.fintlabs.librarydatabasebackend.entity.Book
import no.fintlabs.librarydatabasebackend.repository.BookRepository
import no.fintlabs.librarydatabasebackend.service.AdminService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StartupBootstrap(
    private val adminService: AdminService,
    private val bookRepository: BookRepository,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        adminService.createAdminUser()

        if (bookRepository.count() == 0L) {
            bookRepository.saveAll(essentialBooks)
        }
    }

    private val essentialBooks: MutableSet<Book> =
        mutableSetOf(
            Book(
                id = UUID.randomUUID(),
                title = "Project Hail Mary",
                author = "Andy Weir",
                description =
                    "An epic adventure into the unknown to find a solution for the biggest threat humanity has ever faced. " +
                        "Doctor Ryland Grace uses his quick wits and great mind for math and physics to solve any and all problems he faces on his way.",
                loaned = false,
            ),
            Book(
                id = UUID.randomUUID(),
                title = "The Name Of The Wind",
                author = "Patrick Rothfuss",
                description =
                    "A mysterious Innkeeper tells the harrowing tales of his past. Adventures of survival and the search for " +
                        "knowledge. Trying to find his place in a magical dangerous world, but unable to let go of his thirst for " +
                        "revenge against the powerful and mysterious man that killed his parents.",
                loaned = false,
            ),
            Book(
                id = UUID.randomUUID(),
                title = "1984",
                author = "George Orwell",
                description =
                    "A dystopian fiction novel about a simple man working as a pencil pusher for a totalitarian super-state " +
                        "that controls every aspect of life in it's lands. Working at the Ministry Of Truth, he rewrites historical " +
                        "records to fit the state's current views and opinions. ",
                loaned = false,
            ),
            Book(
                id = UUID.randomUUID(),
                title = "Of Mice And Men",
                author = "John Steinbeck",
                description =
                    "A tale of two brothers searching for jobs during the Great Depression so they can save up " +
                        "to buy their own farm. ",
                loaned = false,
            ),
            Book(
                id = UUID.randomUUID(),
                title = "Coraline",
                author = "Neil Gaiman",
                description =
                    "The story of a young only child who moved to the country with her parents, despite her reluctance. " +
                        "While her parents work she explores the old house and everything around, meeting quirky neighbors, " +
                        "and exploring a mysterious dimension hidden underneath the property. ",
                loaned = false,
            ),
        )
}
