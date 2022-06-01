package tech.developingdeveloper.printme.printerlist.ui

sealed class PrinterListUiState {
    object Initial : PrinterListUiState()
    object Loading : PrinterListUiState()
    data class Loaded(val printers: List<PrinterUiItem>) : PrinterListUiState()
    data class Error(val errorMessage: String) : PrinterListUiState()
}
