package tech.developingdeveloper.printme.printdocument.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu.rememberPMExposedDropdownMenuState

@Composable
fun PrintDocumentScreen(
    scaffoldState: ScaffoldState,
    viewModel: PrintDocumentViewModel = hiltViewModel(),
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
            onResult = { viewModel.onPickDocumentResult(it.map(Uri::toString)) },
        )

    val allowedMimeTypes = arrayOf("application/pdf")

    val modalBottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = {
                if (it == ModalBottomSheetValue.Hidden) viewModel.onBottomSheetIsHidden()
                true
            },
        )

    LaunchedEffect(
        key1 = uiState.value.printDocumentBottomSheetStatus,
        key2 = modalBottomSheetState,
    ) {
        toggleBottomSheet(
            isBottomSheetVisible =
                uiState.value.printDocumentBottomSheetStatus
                    !is PrintDocumentBottomSheetStatus.Hidden,
            modalBottomSheetState = modalBottomSheetState,
        )
    }

    SideEffect {
        coroutineScope.launch {
            displaySnackbar(
                scaffoldState,
                uiState.value.snackbarMessage,
                viewModel::onSnackbarActionComplete,
            )
        }
    }

    DisposableEffect(key1 = true) {
        onDispose {
            coroutineScope.launch {
                if (modalBottomSheetState.isVisible) {
                    modalBottomSheetState.hide()
                }
            }
        }
    }

    PrintDocumentContent(
        uiState = uiState.value,
        onSelectClick = { pickDocumentLauncher.launch(allowedMimeTypes) },
        onProceedClick = viewModel::onProceedClick,
        onItemClick = viewModel::onItemClick,
        onDeleteClick = viewModel::onDeleteClick,
        bottomSheetState = modalBottomSheetState,
        colorOptions = colorOptions,
        colorExposedDropdownMenuState = colorExposedDropdownMenuState,
        printerOptions = printerOptions,
        printerExposedDropdownMenuState = printerExposedDropdownMenuState,
        onPrintConfigPrintClick = viewModel::onPrintDocumentClick,
        onSelectedFileOptionsMenuSaveClick = viewModel::onSelectedFileOptionsMenuSaveClick,
    )
}

private suspend fun toggleBottomSheet(
    isBottomSheetVisible: Boolean,
    modalBottomSheetState: ModalBottomSheetState,
) {
    if (isBottomSheetVisible) {
        modalBottomSheetState.show()
    } else {
        modalBottomSheetState.hide()
    }
}

private suspend fun displaySnackbar(
    scaffoldState: ScaffoldState,
    snackbarMessage: String?,
    onComplete: () -> Unit,
) {
    if (snackbarMessage == null) return

    scaffoldState.snackbarHostState.showSnackbar(snackbarMessage)

    onComplete()
}
