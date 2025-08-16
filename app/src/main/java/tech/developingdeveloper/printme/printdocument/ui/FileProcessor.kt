package tech.developingdeveloper.printme.printdocument.ui

import android.content.Context
import android.graphics.pdf.LoadParams
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.ParcelFileDescriptor
import android.os.ext.SdkExtensions
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

    fun isPasswordProtected(documentUri: String): Boolean {
        var parcelFileDescriptor: ParcelFileDescriptor? = null

        try {
            parcelFileDescriptor =
                context.contentResolver.openFileDescriptor(documentUri.toUri(), "r")

            if (parcelFileDescriptor == null) return false

            PdfRenderer(parcelFileDescriptor).use {
                return false
            }
        } catch (_: SecurityException) {
            return true
        } catch (_: Exception) {
            return false
        } finally {
            parcelFileDescriptor?.close()
        }
    }

    fun validatePassword(
        documentUri: String,
        password: String,
    ): PasswordValidity {
        if (
            Build.VERSION.SDK_INT < Build.VERSION_CODES.VANILLA_ICE_CREAM ||
            SdkExtensions.getExtensionVersion(Build.VERSION_CODES.S) < 13
        ) {
            return PasswordValidity.UNKNOWN
        }

        var parcelFileDescriptor: ParcelFileDescriptor? = null

        try {
            parcelFileDescriptor =
                context.contentResolver.openFileDescriptor(documentUri.toUri(), "r")

            if (parcelFileDescriptor == null) return PasswordValidity.UNKNOWN

            val loadParams = LoadParams.Builder().setPassword(password).build()

            PdfRenderer(parcelFileDescriptor, loadParams).use {
                return PasswordValidity.VALID
            }
        } catch (_: Exception) {
            return PasswordValidity.INVALID
        } finally {
            parcelFileDescriptor?.close()
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
