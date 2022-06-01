package tech.developingdeveloper.printme.printerlist.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import tech.developingdeveloper.printme.printerlist.domain.models.GetPrinterListResult
import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import tech.developingdeveloper.printme.printerlist.domain.usecases.GetAllPrintersUseCase

@HiltViewModel
class PrinterListViewModel @Inject constructor(
    private val getAllPrintersUseCase: GetAllPrintersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PrinterListUiState>(PrinterListUiState.Initial)
    val uiState: StateFlow<PrinterListUiState> = _uiState.asStateFlow()

    init {
        getPrinters()
    }

    private fun getPrinters() {

        updateUiState(PrinterListUiState.Loading)

        val result = getAllPrintersUseCase.getPrinters()
        when (result) {
            is GetPrinterListResult.Failure -> handleFailure(result)
            is GetPrinterListResult.Success -> handleSuccess(result)
        }
    }

    private fun handleFailure(result: GetPrinterListResult.Failure) {
        val errorMessage = result.exception.message ?: "Something went wrong."
        val state = PrinterListUiState.Error(errorMessage)
        updateUiState(state)
    }

    private fun handleSuccess(result: GetPrinterListResult.Success) {
        val printers = result.printers.map { it.toPrinterUi() }
        val state = PrinterListUiState.Loaded(printers)
        updateUiState(state)
    }

    private fun updateUiState(printerListUiState: PrinterListUiState) {
        _uiState.value = printerListUiState
    }
}

private fun Printer.toPrinterUi(): PrinterUiItem = PrinterUiItem(
    name = this.name,
    isAcceptingJobs = this.isAcceptingJobs
)