package tech.developingdeveloper.printme.printdocument.domain.models

import tech.developingdeveloper.printme.core.PrintMeException

sealed class PrintDocumentResult {
    object Success : PrintDocumentResult()
    data class Failure(val exception: PrintMeException) : PrintDocumentResult()
}
