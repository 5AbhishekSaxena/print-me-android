package tech.developingdeveloper.printme.printdocument.domain.repository

import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printdocument.domain.models.File

interface PrintDocumentRepository {
    suspend fun printDocuments(
        files: List<File>,
        printerName: String,
    ): Result<String>
}
