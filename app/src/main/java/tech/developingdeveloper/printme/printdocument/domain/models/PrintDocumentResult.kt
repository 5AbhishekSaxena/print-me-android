package tech.developingdeveloper.printme.printdocument.domain.models

sealed class PrintDocumentResult {
    object Success : PrintDocumentResult()
    data class Failure(val exception: Exception) : PrintDocumentResult()
}
