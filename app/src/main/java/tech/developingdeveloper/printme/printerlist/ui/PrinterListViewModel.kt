package tech.developingdeveloper.printme.printerlist.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrinterListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<PrinterListUiState>(PrinterListUiState.Initial)
    val uiState: StateFlow<PrinterListUiState> = _uiState.asStateFlow()
}