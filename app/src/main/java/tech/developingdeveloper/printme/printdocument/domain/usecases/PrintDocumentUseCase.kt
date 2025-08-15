package tech.developingdeveloper.printme.printdocument.domain.usecases

import tech.developingdeveloper.printme.printdocument.domain.models.File
import tech.developingdeveloper.printme.printdocument.domain.models.PrintDocumentResult

interface PrintDocumentUseCase {
    suspend operator fun invoke(
        files: List<File>,
        printerName: String,
    ): PrintDocumentResult
}
