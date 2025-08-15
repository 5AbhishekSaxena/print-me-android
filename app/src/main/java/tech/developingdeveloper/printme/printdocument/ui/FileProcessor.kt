package tech.developingdeveloper.printme.printdocument.ui

import android.content.Context
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import org.apache.commons.io.IOUtils
import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.core.utils.getFileName
import java.io.File
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
