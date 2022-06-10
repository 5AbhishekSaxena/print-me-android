package tech.developingdeveloper.printme.printdocument.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrintDocumentViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<PrintDocumentUiState>(PrintDocumentUiState.Initial)
    val uiState: StateFlow<PrintDocumentUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = PrintDocumentUiState.Active(mutableListOf())
    }
}
