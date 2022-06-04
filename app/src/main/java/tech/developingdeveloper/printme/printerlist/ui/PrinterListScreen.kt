package tech.developingdeveloper.printme.printerlist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PrinterListScreen(
    viewModel: PrinterListViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    PrinterListContent(printerListUiState = uiState.value)
}
