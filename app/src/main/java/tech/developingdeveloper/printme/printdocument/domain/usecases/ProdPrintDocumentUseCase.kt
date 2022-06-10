package tech.developingdeveloper.printme.printdocument.domain.usecases

import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printdocument.domain.models.File
import tech.developingdeveloper.printme.printdocument.domain.models.PrintDocumentResult
import tech.developingdeveloper.printme.printdocument.domain.repository.PrintDocumentRepository
import javax.inject.Inject

class ProdPrintDocumentUseCase @Inject constructor(
    private val printDocumentRepository: PrintDocumentRepository
) : PrintDocumentUseCase {
    override suspend fun invoke(files: List<File>): PrintDocumentResult {
        val result = printDocumentRepository.printDocuments()
        return when (result) {
            is Result.Failure -> handleFailure(result)
            is Result.Success -> handleSuccess()
        }
    }

    private fun handleFailure(result: Result.Failure): PrintDocumentResult {
        return PrintDocumentResult.Failure(result.error)
    }

    private fun handleSuccess(): PrintDocumentResult {
        return PrintDocumentResult.Success
    }
}
