package tech.developingdeveloper.printme.printerlist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun PrinterListScreen(
    navigator: DestinationsNavigator,
    viewModel: PrinterListViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    PrinterListContent(printerListUiState = uiState.value)
}
