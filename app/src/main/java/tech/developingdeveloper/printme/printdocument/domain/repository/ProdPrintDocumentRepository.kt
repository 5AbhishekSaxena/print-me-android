package tech.developingdeveloper.printme.printdocument.domain.repository

import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printdocument.domain.models.File
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class ProdPrintDocumentRepository @Inject constructor(
    private val printDocumentDataSource: PrintDocumentDataSource
) : PrintDocumentRepository {
    override suspend fun printDocuments(files: List<File>, printerName: String): Result<String> {
        return try {
            val response = printDocumentDataSource.printDocument(files, printerName) ?: ""
            Result.Success(response)
        } catch (exception: Exception) {
            Result.Failure(PrintMeException(exception))
        }
    }
}
