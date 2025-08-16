package tech.developingdeveloper.printme.printdocument.ui

import tech.developingdeveloper.printme.printdocument.domain.models.File

sealed class PrintDocumentBottomSheetStatus {
    object Hidden : PrintDocumentBottomSheetStatus()

    object FilesOptions : PrintDocumentBottomSheetStatus()

    data class FileOptions(
        val file: File,
    ) : PrintDocumentBottomSheetStatus()
}
