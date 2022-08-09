package tech.developingdeveloper.printme.printdocument.domain.repository

import tech.developingdeveloper.printme.printdocument.domain.models.File

interface PrintDocumentDataSource {

    suspend fun printDocument(file: File, printerName: String): String?
}
