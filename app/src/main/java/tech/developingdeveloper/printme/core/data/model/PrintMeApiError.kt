package tech.developingdeveloper.printme.core.data.model

import java.time.LocalDateTime

data class PrintMeApiError(
    val message: String,
    val status: String,
    val code: Int,
    val timestamp: LocalDateTime,
)
