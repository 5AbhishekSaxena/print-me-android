package tech.developingdeveloper.printme.core.data

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Failure(val error: Exception) : Result<Nothing>()
}
