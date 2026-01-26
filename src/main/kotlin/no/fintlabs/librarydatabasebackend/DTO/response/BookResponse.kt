package no.fintlabs.librarydatabasebackend.DTO.response

data class BookResponse(
    val id: Long,
    val title: String,
    val author: String,
    val loaned: Boolean? = false,
)

data class BookAdminResponse(
    val id: Long,
    val title: String,
    val author: String,
    val loaned: Boolean?,
    val loans: GetAllLoansResponse
)
