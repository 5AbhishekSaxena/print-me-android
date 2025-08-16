package tech.developingdeveloper.printme.printdocument.ui

import android.content.Context
import androidx.core.net.toUri
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.io.IOUtils
import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.core.utils.getFileName
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class FileProcessor @Inject constructor(
    @ApplicationContext private val context: Context,
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
        withContext(Dispatchers.IO) {
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
        withContext(Dispatchers.IO) {
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

    fun copyFileToCache(
        documentUri: String,
        fullFileName: String,
    ): File {
        val fileInputStream =
            context.contentResolver.openInputStream(documentUri.toUri())
                ?: throw PrintMeException("Failed to get input stream for the selected file.")

        fileInputStream.use { fin ->
            val tempFile = java.io.File(context.cacheDir, fullFileName)
            tempFile.createNewFile()
            val fileOutputStream = tempFile.outputStream()

            fileOutputStream.use {
                IOUtils.copy(fin, it)
                return tempFile
            }
        }
    }
}
