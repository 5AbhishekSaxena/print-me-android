package tech.developingdeveloper.printme.printdocument.domain.repository

import android.content.Context
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import okio.source
import tech.developingdeveloper.printme.core.data.PrinterApiService
import tech.developingdeveloper.printme.core.data.safeApiCall
import tech.developingdeveloper.printme.printdocument.domain.models.File
import javax.inject.Inject

class RemotePrintDocumentDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val printerApiService: PrinterApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PrintDocumentDataSource {
    override suspend fun printDocument(
        files: List<File>,
        printerName: String,
    ): String? =
        safeApiCall(dispatcher) {
            val filesPart = uriToMultipartParts(files)
            printerApiService.printDocument(filesPart, printerName)
            "Print successful"
        }

    private fun uriToMultipartParts(
        files: List<File>,
        paramName: String = "files",
    ): List<MultipartBody.Part> {
        return files.mapNotNull { file ->
            try {
                val requestBody = uriToRequestBody(file.uri, file.mimeType)
                MultipartBody.Part.createFormData(paramName, file.name, requestBody)
            } catch (_: Exception) {
                null // skip invalid uri
            }
        }
    }

    private fun uriToRequestBody(
        documentUri: String,
        mimeType: String,
    ): RequestBody {
        return object : RequestBody() {
            override fun contentType(): MediaType? = mimeType.toMediaTypeOrNull()

            override fun writeTo(sink: BufferedSink) {
                context.contentResolver.openInputStream(documentUri.toUri())?.use {
                    sink.writeAll(it.source())
                }
            }
        }
    }
}
