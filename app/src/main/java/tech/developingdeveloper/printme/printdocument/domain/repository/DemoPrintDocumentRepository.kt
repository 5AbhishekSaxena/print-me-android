package tech.developingdeveloper.printme.printdocument.domain.repository

import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printdocument.domain.models.File
import javax.inject.Inject

class DemoPrintDocumentRepository @Inject constructor() : PrintDocumentRepository {
    override suspend fun printDocuments(file: File, printerName: String): Result<String> {
        return Result.Success("Printed successfully.")
    }
}
