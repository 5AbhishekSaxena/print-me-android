package tech.developingdeveloper.printme.printdocument.ui

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import tech.developingdeveloper.printme.core.utils.getContext
import tech.developingdeveloper.printme.core.utils.getFileName
import tech.developingdeveloper.printme.printdocument.domain.models.File
import tech.developingdeveloper.printme.printdocument.domain.models.PrintDocumentResult
import tech.developingdeveloper.printme.printdocument.domain.usecases.PrintDocumentUseCase
import javax.inject.Inject

@HiltViewModel
class PrintDocumentViewModel @Inject constructor(
    application: Application,
    private val printDocumentUseCase: PrintDocumentUseCase
) : AndroidViewModel(application) {

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

    fun onPrintDocumentClick() {
        viewModelScope.launch {
            val files = _uiState.value.files
            val result = printDocumentUseCase(files)
            when (result) {
                is PrintDocumentResult.Failure -> handlePrintDocumentResultFailure(result)
                is PrintDocumentResult.Success -> handlePrintDocumentResultSuccess(result)
            }
        }
    }

    private fun handlePrintDocumentResultFailure(result: PrintDocumentResult.Failure) {
        Log.e(javaClass.name, "handlePrintDocumentResultFailure, result: $result")
    }

    private fun handlePrintDocumentResultSuccess(result: PrintDocumentResult.Success) {
        _uiState.value = PrintDocumentUiState.Initial
        Log.e(javaClass.name, "handlePrintDocumentResultSuccess,  result: $result")
    }

    fun onDeleteClick(file: File) {
        val files = _uiState.value.files.toMutableList()
        files.remove(file)
        _uiState.value = PrintDocumentUiState.Active(files)
    }
}
