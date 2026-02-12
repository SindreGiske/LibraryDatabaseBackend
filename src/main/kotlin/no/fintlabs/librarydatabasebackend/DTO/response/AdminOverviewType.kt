package no.fintlabs.librarydatabasebackend.DTO.response

data class AdminOverviewType(
    val userCount: Int,
    val totalBookCount: Int,
    val availableBooksCount: Int,
    val totalLoanCount: Int,
    val activeLoanCount: Int,
)
