package no.fintlabs.librarydatabasebackend

import jakarta.transaction.Transactional
import no.fintlabs.librarydatabasebackend.repository.BookRepository
import no.fintlabs.librarydatabasebackend.service.AdminService
import no.fintlabs.librarydatabasebackend.service.BookService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class StartupBootstrap(
    private val adminService: AdminService,
    private val bookService: BookService,
    private val bookRepository: BookRepository,
) : CommandLineRunner {
    @Transactional
    override fun run(vararg args: String?) {
        adminService.createAdminUser()

        if (bookRepository.count() == 0L) {
            println("no DB content found. Initializing DB...")
            bookService.addNewBook(
                title = "Project Hail Mary",
                author = "Andy Weir",
                description =
                    "An epic adventure into the unknown to find a solution for the biggest threat humanity has ever faced. " +
                        "Doctor Ryland Grace uses his quick wits and great mind for math and physics to solve any and all problems he faces on his way.",
            )
            bookService.addNewBook(
                title = "The Martian",
                author = "Andy Weir",
                description =
                    "Mark Whatney, a botanist and engineer is on a short research mission to Mars when a storm hits " +
                        "and his crew returned to Earth, presuming he was dead. He is then left alone on a harsh desolate planet " +
                        "using his brilliant mind for science to survive, and try to get back in contact with human civilization.",
            )
            bookService.addNewBook(
                title = "The Name Of The Wind",
                author = "Patrick Rothfuss",
                description =
                    "A mysterious Innkeeper tells the harrowing tales of his past. Adventures of survival and the search for " +
                        "knowledge. Trying to find his place in a magical dangerous world, but unable to let go of his thirst for " +
                        "revenge against the powerful and mysterious man that killed his parents.",
            )
            bookService.addNewBook(
                title = "The Wise Man's Fear",
                author = "Patrick Rothfuss",
                description =
                    "The sequel to the hit fantasy book 'The Name Of The Wind'. A continuance of Kvothe's misadventures. " +
                        "Pursuing an education in magic, performing shenanigans on his adversaries and traveling far and wide " +
                        "in search of the truths of his reality. ",
            )
            bookService.addNewBook(
                title = "1984",
                author = "George Orwell",
                description =
                    "A dystopian fiction novel about a simple man working as a pencil pusher for a totalitarian super-state " +
                        "that controls every aspect of life in it's lands. Working at the Ministry Of Truth, he rewrites historical " +
                        "records to fit the state's current views and opinions. ",
            )
            bookService.addNewBook(
                title = "Animal Farm",
                author = "George Orwell",
                description =
                    "A satirical beast fable following anthropomorphic farm animals rebelling against their human masters, " +
                        "in a story that reflects the events leading up to the Russian Revolution of 1917 as well as what came after.",
            )
            bookService.addNewBook(
                title = "Of Mice And Men",
                author = "John Steinbeck",
                description =
                    "A period piece about two brothers searching for jobs during the Great Depression so they can save up " +
                        "to buy their own farm. ",
            )
            bookService.addNewBook(
                title = "Coraline",
                author = "Neil Gaiman",
                description =
                    "The story of a young only child who moved to the country with her parents, despite her reluctance. " +
                        "While her parents work she explores the old house and everything around, meeting quirky neighbors, " +
                        "and exploring a mysterious dimension hidden underneath the property. ",
            )
            bookService.addNewBook(
                title = "The Storyteller",
                author = "Dave Grohl",
                description =
                    "An autobiography by the famous multi-instrumental artist Dave Grohl, who started out as " +
                        "the drummer for Nirvana, and later started his own band, Foo Fighters. The book holds " +
                        "fascinating stories of life, death, music and everything therein.",
            )
            bookService.addNewBook(
                title = "The Call Of Cthulhu",
                author = "H.P. Lovecraft",
                description =
                    "A short story by the forefather of everything we today call horror. " +
                        "An iconic story primarily in four parts, unfolding the terrifying truth of " +
                        "humanity's insignificance and the unknown horrors that may be lurking under the sea.",
            )
            bookService.addNewBook(
                title = "Dagon",
                author = "H.P. Lovecraft",
                description =
                    "A short story about a man stranded alone on a lifeboat drifting at sea. Eventually " +
                        "he washes ashore on 'a slimy expanse of hellish black mire'. As he later starts exploring " +
                        "the island it just gets more and more strange and unsettling. ",
            )
            bookService.addNewBook(
                title = "Mogworld",
                author = "Ben Croshaw",
                description =
                    "A comedic fantasy novel about an undead minion whose only wish is to die. " +
                        "Seeing as he is undead, his wish to die is quite a tall order. Nonetheless he " +
                        "strives to fulfil his dream of ending his ceaseless existence by any means necessary.",
            )

            println("DB init complete.")
            println("")
        }
    }
}
