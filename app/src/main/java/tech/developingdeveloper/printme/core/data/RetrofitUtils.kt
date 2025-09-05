package tech.developingdeveloper.printme.core.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import retrofit2.HttpException
import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.core.data.model.PrintMeApiError
import java.io.IOException
import kotlin.coroutines.CoroutineContext

suspend fun <R> safeApiCall(
    dispatcher: CoroutineContext,
    onError: ((PrintMeApiError) -> PrintMeException)? = null,
    block: suspend () -> R,
): R =
    withContext(dispatcher) {
        try {
            block()
        } catch (exception: Exception) {
            val fallbackErrorMessage = "Something went wrong."
            when (exception) {
                is IOException -> throw PrintMeException(fallbackErrorMessage)

                is HttpException -> {
                    val apiError = convertErrorBody(exception, fallbackErrorMessage)
                    if (onError != null) throw onError(apiError)
                    throw PrintMeException(apiError.message)
                }

                else ->
                    throw PrintMeException(exception.message ?: fallbackErrorMessage)
            }
        }
    }

private val moshiAdapter =
    Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
        .adapter(PrintMeApiError::class.java)

private fun convertErrorBody(
    exception: HttpException,
    fallbackErrorMessage: String,
): PrintMeApiError {
    val source = exception.response()?.errorBody()?.source()
    if (exception.response()?.errorBody()?.contentType() ==
        "application/json".toMediaTypeOrNull() ||
        source == null
    ) {
        throw PrintMeException(fallbackErrorMessage)
    }

    return try {
        moshiAdapter.fromJson(source) ?: throw PrintMeException(fallbackErrorMessage)
    } catch (_: Exception) {
        throw PrintMeException(fallbackErrorMessage)
    }
}
