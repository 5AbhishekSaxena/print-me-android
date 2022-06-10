package tech.developingdeveloper.printme.printdocument.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.developingdeveloper.printme.core.utils.getContext
import tech.developingdeveloper.printme.core.utils.getFileName
import tech.developingdeveloper.printme.printdocument.domain.models.File

class PrintDocumentViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<PrintDocumentUiState>(PrintDocumentUiState.Initial)
    val uiState: StateFlow<PrintDocumentUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = PrintDocumentUiState.Active(mutableListOf())
    }

    fun onPickDocumentResult(documentUri: Uri?) {
        if (documentUri == null) return
        onFileAdded(documentUri)
    }

    private fun onFileAdded(documentUri: Uri) {
        val context = getContext() ?: return

        val fileName = documentUri.getFileName(context) ?: return
        val file = File(
            name = fileName,
            uri = documentUri,
            color = File.Color.MONOCHROME,
            copies = 1
        )
        addFile(file)
    }

    private fun addFile(file: File) {
        val files = _uiState.value.files.toMutableList()
        files.add(file)
        _uiState.value = PrintDocumentUiState.Active(files)
    }
}
