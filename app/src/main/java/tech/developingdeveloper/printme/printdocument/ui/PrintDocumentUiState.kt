package tech.developingdeveloper.printme.printdocument.ui

import tech.developingdeveloper.printme.printdocument.domain.models.File

sealed class PrintDocumentUiState(
    open val files: List<File>,
    open val selectedPrinter: String?,
    open val snackbarMessage: String?,
    open val printDocumentBottomSheetStatus: PrintDocumentBottomSheetStatus,
) {
    object Initial : PrintDocumentUiState(
        files = emptyList(),
        snackbarMessage = null,
        selectedPrinter = null,
        printDocumentBottomSheetStatus = PrintDocumentBottomSheetStatus.Hidden,
    )

    data class Active(
        override val files: List<File>,
        override val selectedPrinter: String? = null,
        override val snackbarMessage: String? = null,
        override val printDocumentBottomSheetStatus: PrintDocumentBottomSheetStatus =
            PrintDocumentBottomSheetStatus.Hidden,
    ) : PrintDocumentUiState(
            files,
            selectedPrinter,
            snackbarMessage,
            printDocumentBottomSheetStatus,
        )
}

fun PrintDocumentUiState.copyToActive(
    files: List<File> = this.files,
    selectedPrinter: String? = this.selectedPrinter,
    snackbarMessage: String? = this.snackbarMessage,
    printDocumentBottomSheetStatus: PrintDocumentBottomSheetStatus =
        this.printDocumentBottomSheetStatus,
): PrintDocumentUiState.Active {
    return when (this) {
        is PrintDocumentUiState.Active ->
            this.copy(
                files = files,
                selectedPrinter = selectedPrinter,
                snackbarMessage = snackbarMessage,
                printDocumentBottomSheetStatus = printDocumentBottomSheetStatus,
            )

        is PrintDocumentUiState.Initial ->
            PrintDocumentUiState.Active(
                files = files,
                selectedPrinter = selectedPrinter,
                snackbarMessage = snackbarMessage,
                printDocumentBottomSheetStatus = printDocumentBottomSheetStatus,
            )
    }
}
