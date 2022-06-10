package tech.developingdeveloper.printme.printdocument.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PrintDocumentScreen(viewModel: PrintDocumentViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState.collectAsState()

    val pickDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = viewModel::onPickDocumentResult
    )

    val allowedMimeTypes = arrayOf(
        "application/pdf",
    )

    PrintDocumentContent(
        uiState = uiState.value,
        onSelectClick = {
            pickDocumentLauncher.launch(allowedMimeTypes)
        },
        onPrintClick = viewModel::onPrintDocumentClick
    )
}
