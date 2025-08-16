package tech.developingdeveloper.printme.printdocument.domain.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.core.data.PrinterApiService
import tech.developingdeveloper.printme.printdocument.domain.models.File
import javax.inject.Inject

class RemotePrintDocumentDataSource @Inject constructor(
    private val printerApiService: PrinterApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PrintDocumentDataSource {
    override suspend fun printDocument(
        files: List<File>,
        printerName: String,
    ): String? {
        val multipartBodyBuilder = MultipartBody.Builder()

        files.forEach {
            val formFile = it.formFile
            val mimeType = it.mimeType
            multipartBodyBuilder.addFormDataPart(
                "files",
                it.name,
                formFile.asRequestBody(mimeType.toMediaType()),
            )
        }

        val response =
            withContext(dispatcher) {
                printerApiService.printDocument(
                    multipartBody = multipartBodyBuilder.build(),
                    printerName = printerName,
                )
            }

        if (response.isSuccessful) {
            return "Print successful" // response.body()
        } else {
            val message = response.errorBody()?.string() ?: "Something went wrong"
            throw PrintMeException(message)
        }
    }
}
