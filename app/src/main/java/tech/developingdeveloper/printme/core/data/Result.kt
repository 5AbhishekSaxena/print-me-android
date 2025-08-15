package tech.developingdeveloper.printme.core.data

import tech.developingdeveloper.printme.core.PrintMeException

sealed class Result<out T> {
    data class Success<T>(
        val data: T,
    ) : Result<T>()

    data class Failure(
        val error: PrintMeException,
    ) : Result<Nothing>()
}
