package tech.developingdeveloper.printme.printdocument.ui

import tech.developingdeveloper.printme.printdocument.domain.models.File

sealed class PrintDocumentUiState(open val files: List<File>) {
    object Initial : PrintDocumentUiState(emptyList())
    data class Active(override val files: List<File>) : PrintDocumentUiState(files)
}
