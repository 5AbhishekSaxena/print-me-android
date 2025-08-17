package tech.developingdeveloper.printme.printdocument.ui

import android.content.Context
import androidx.core.net.toUri
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.core.utils.getFileName
import java.io.InputStream
import javax.inject.Inject

class FileProcessor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    fun getMineType(documentUri: String): String {
        return context.contentResolver.getType(documentUri.toUri())
            ?: throw PrintMeException("Unable to get mime type of the file.")
    }

    fun getFileName(documentUri: String): String {
        return documentUri.toUri().getFileName(context)
            ?: throw PrintMeException("Failed to get full file name.")
    }

    suspend fun isPasswordProtected(documentUri: String): Boolean =
        withContext(dispatcher) {
            var inputStream: InputStream? = null
            var document: PDDocument? = null

            try {
                inputStream =
                    context.contentResolver.openInputStream(documentUri.toUri())
                document = PDDocument.load(inputStream)
                document.isEncrypted
            } catch (_: InvalidPasswordException) {
                true
            } catch (e: Exception) {
                false
            } finally {
                document?.close()
                inputStream?.close()
            }
        }

    suspend fun validatePassword(
        documentUri: String,
        password: String,
    ): PasswordValidity =
        withContext(dispatcher) {
            var inputStream: InputStream? = null
            var document: PDDocument? = null

            try {
                inputStream =
                    context.contentResolver.openInputStream(documentUri.toUri())
                document = PDDocument.load(inputStream, password)
                PasswordValidity.VALID
            } catch (_: InvalidPasswordException) {
                PasswordValidity.INVALID
            } catch (_: Exception) {
                PasswordValidity.UNKNOWN
            } finally {
                document?.close()
                inputStream?.close()
            }
        }
}
