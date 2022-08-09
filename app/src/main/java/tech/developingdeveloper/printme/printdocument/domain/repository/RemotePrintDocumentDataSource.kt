package tech.developingdeveloper.printme.printdocument.domain.repository

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import tech.developingdeveloper.printme.core.data.PrinterApiService
import tech.developingdeveloper.printme.printdocument.domain.models.File
import javax.inject.Inject

class RemotePrintDocumentDataSource @Inject constructor(
    private val printerApiService: PrinterApiService
) : PrintDocumentDataSource {
    override suspend fun printDocument(file: File, printerName: String): String? {
        val path = file.uri.path ?: throw Exception("Uri lacks 'path'")
        val formFile = file.formFile // java.io.File(path)// file.uri.toFile()

        val fileExtension = formFile.extension // MimeTypeMap.getFileExtensionFromUrl(formFile./*file.uri.toString()*/)
        val mimeType = file.mimeType // MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
        // ?: throw Exception("Ill formed mimetype.")
        val multipartFileBody = MultipartBody.Part
            .createFormData(
                name = "file",
                filename = file.name,
                body = formFile.asRequestBody(mimeType.toMediaType())
            )

        val response = printerApiService.printDocument(multipartFileBody, printerName)

        if (response.isSuccessful)
            return "Print successful" // response.body()
        else {
            val message = response.errorBody()?.string() ?: "Something went wrong"
            throw Exception(message)
        }
    }
}