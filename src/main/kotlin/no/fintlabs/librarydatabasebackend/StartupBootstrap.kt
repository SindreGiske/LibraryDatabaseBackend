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
            bookService.addNewBook(
                title = "Dune",
                author = "Frank Herbert",
                description =
                    "An epic science fiction masterpiece fallowing a young noble as his family " +
                        "takes stewardship of the dangerous planet Arrakis, the sole source of the life-extending " +
                        "'spice' melange.",
            )
            bookService.addNewBook(
                title = "Moby Dick",
                author = "Herman Melville",
                description =
                    "One of the most iconic books ever written, following a young sailor named Ishmael, " +
                        "taking part in the manic quest of captain Ahab for vengeance against the titular white whale " +
                        "that bit his leg off on the ship's previous voyage.",
            )
            bookService.addNewBook(
                title = "Donut Economics",
                author = "Kate Raworth",
                description =
                    "Full title: Donut Economics: Seven Ways To Think Like a 21st-Century Economist. " +
                        "The book elaborates on her concept of donut economics first developed in her 2012 paper, " +
                        "'A Safe And Just Space for Humanity'.",
            )
            bookService.addNewBook(
                title = "We Have No Idea!",
                author = "Jorge Cham & Daniel Whiteson",
                description =
                    "A guide to the unknown universe. A book about many of the things we do, " +
                        "and more importantly don't know about the universe. ",
            )
            bookService.addNewBook(
                title = "All Hell Let Loose",
                author = "Max Hastings",
                description =
                    "A book by historian Max Hastings covering the history of World War II. It follows the " +
                        "military developments of the war, but focusing on the reactions and experiences of  different " +
                        "individuals, both uniformed and civilian.",
            )
            bookService.addNewBook(
                title = "The Changing World Order",
                author = "Ray Dalio",
                description =
                    "Principles for Dealing with The Changing World Order; Why Nations Succeed and Fail. " +
                        "The book examines the cyclical patterns underlying the rise and fall of major empires " +
                        "over the past 500 years.",
            )
            bookService.addNewBook(
                title = "The Shining",
                author = "Stephen King",
                description =
                    "The story centers on a struggling writer and recovering alcoholic who accepts a position " +
                        "as the off-season caretaker of a historic hotel with his family, including his young son who " +
                        "possesses an array of psychic abilities which allow him to glimpse the hotel's horrific true nature. ",
            )
            bookService.addNewBook(
                title = "Death",
                author = "Neil Gaiman",
                description =
                    "A brilliant graphic novel following a physical personification of the eternal deity 'DEATH', " +
                        "on her travels through the mortal plane, guiding people whose lives have run their course to the " +
                        "afterlife. ",
            )

            println("DB init complete.")
            println("")
        }
    }
}
