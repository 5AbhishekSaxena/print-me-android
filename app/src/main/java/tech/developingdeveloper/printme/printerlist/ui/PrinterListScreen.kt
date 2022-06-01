package tech.developingdeveloper.printme.printerlist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun PrinterListScreen(
    viewModel: PrinterListViewModel = PrinterListViewModel() // replace with hiltViewModel later
) {

    val uiState = viewModel.uiState.collectAsState()

    PrinterListContent(printerListUiState = uiState.value)

}