package tech.developingdeveloper.printme.printdocument.ui

import tech.developingdeveloper.printme.printdocument.domain.models.File

sealed class PrintDocumentUiState(
    open val files: List<File>,
    open val selectedPrinter: String?,
    open val snackbarMessage: String?,
    open val isBottomSheetVisible: Boolean,
) {
    object Initial : PrintDocumentUiState(
        files = emptyList(),
        isBottomSheetVisible = false,
        snackbarMessage = null,
        selectedPrinter = null,
    )

    data class Active(
        override val files: List<File>,
        override val selectedPrinter: String? = null,
        override val snackbarMessage: String? = null,
        override val isBottomSheetVisible: Boolean = false,
    ) : PrintDocumentUiState(files, selectedPrinter, snackbarMessage, isBottomSheetVisible)
}
