package tech.developingdeveloper.printme.printdocument.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu.rememberPMExposedDropdownMenuState

@Composable
@Destination
fun PrintDocumentScreen(
    scaffoldState: ScaffoldState,
    viewModel: PrintDocumentViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val uiState = viewModel.uiState.collectAsState()
    val colorOptions by viewModel.colorOptions.collectAsState()
    val printerOptions by viewModel.printerOptions.collectAsState()

    val colorExposedDropdownMenuState =
        rememberPMExposedDropdownMenuState(initSelectedOption = colorOptions[0])
    val printerExposedDropdownMenuState =
        rememberPMExposedDropdownMenuState(initSelectedOption = printerOptions[0])

    val pickDocumentLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenMultipleDocuments(),
            onResult = viewModel::onPickDocumentResult
        )

    val allowedMimeTypes = arrayOf(
        "application/pdf",
    )

    val modalBottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = {
                if (it == ModalBottomSheetValue.Hidden)
                    viewModel.onBottomSheetIsHidden()
                true
            }
        )

    SideEffect {
        coroutineScope.launch {
            toggleBottomSheet(uiState.value.isBottomSheetVisible, modalBottomSheetState)
            displaySnackbar(scaffoldState, uiState.value.snackbarMessage, viewModel::onSnackbarActionComplete)
        }
    }

    DisposableEffect(key1 = true) {
        onDispose {
            coroutineScope.launch {
                modalBottomSheetState.snapTo(ModalBottomSheetValue.Hidden)
            }
        }
    }

    val onSelectClick = { pickDocumentLauncher.launch(allowedMimeTypes) }

    PrintDocumentContent(
        uiState = uiState.value,
        onSelectClick = onSelectClick,
        onProceedClick = viewModel::onProceedClick,
        onDeleteClick = viewModel::onDeleteClick,
        bottomSheetState = modalBottomSheetState,
        colorOptions = colorOptions,
        colorExposedDropdownMenuState = colorExposedDropdownMenuState,
        printerOptions = printerOptions,
        printerExposedDropdownMenuState = printerExposedDropdownMenuState,
        onBottomSheetPrintClick = viewModel::onPrintDocumentClick
    )
}

private suspend fun toggleBottomSheet(
    isBottomSheetVisible: Boolean,
    modalBottomSheetState: ModalBottomSheetState
) {
    if (isBottomSheetVisible)
        modalBottomSheetState.show()
    else
        modalBottomSheetState.hide()
}

private suspend fun displaySnackbar(scaffoldState: ScaffoldState, snackbarMessage: String?, onComplete: () -> Unit) {
    if (snackbarMessage == null) return

    scaffoldState.snackbarHostState.showSnackbar(snackbarMessage)

    onComplete()
}
