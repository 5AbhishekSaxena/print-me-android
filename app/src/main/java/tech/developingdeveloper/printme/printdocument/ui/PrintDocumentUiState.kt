package tech.developingdeveloper.printme.printdocument.ui

import tech.developingdeveloper.printme.printdocument.domain.models.File

sealed class PrintDocumentUiState(
    open val files: List<File>,
    open val isBottomSheetVisible: Boolean
) {
    object Initial : PrintDocumentUiState(files = emptyList(), isBottomSheetVisible = false)
    data class Active(
        override val files: List<File>,
        override val isBottomSheetVisible: Boolean = false
    ) : PrintDocumentUiState(files, isBottomSheetVisible)
}
