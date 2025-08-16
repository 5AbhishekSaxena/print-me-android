@file:Suppress("TooManyFunctions")

package tech.developingdeveloper.printme.printdocument.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.printdocument.domain.models.ColorExposedDropDownMenuState
import tech.developingdeveloper.printme.printdocument.domain.models.File
import tech.developingdeveloper.printme.printdocument.domain.models.PasswordStatus
import tech.developingdeveloper.printme.printdocument.domain.models.PrintDocumentResult
import tech.developingdeveloper.printme.printdocument.domain.models.PrinterExposedDropDownMenuState
import tech.developingdeveloper.printme.printdocument.domain.usecases.PrintDocumentUseCase
import tech.developingdeveloper.printme.printerlist.domain.models.GetPrinterListResult
import tech.developingdeveloper.printme.printerlist.domain.usecases.GetAllPrintersUseCase
import javax.inject.Inject

@HiltViewModel
class PrintDocumentViewModel @Inject constructor(
    private val printDocumentUseCase: PrintDocumentUseCase,
    private val getAllPrintersUseCase: GetAllPrintersUseCase,
    private val fileProcessor: FileProcessor,
) : ViewModel() {
    private val _uiState = MutableStateFlow<PrintDocumentUiState>(PrintDocumentUiState.Initial)
    val uiState: StateFlow<PrintDocumentUiState> = _uiState.asStateFlow()

    private val _colorOptions = MutableStateFlow(mutableListOf(""))
    val colorOptions: StateFlow<List<String>> = _colorOptions.asStateFlow()

    private val _printerOptions = MutableStateFlow(mutableListOf(""))
    val printerOptions: StateFlow<List<String>> = _printerOptions.asStateFlow()

    init {
        _uiState.value = PrintDocumentUiState.Active(mutableListOf())
        getPrinters()
        getColors()
    }

    private fun getPrinters() {
        viewModelScope.launch {
            val result = getAllPrintersUseCase.getPrinters()
            handleGetAllPrintersResult(result)
        }
    }

    private fun handleGetAllPrintersResult(getPrinterListResult: GetPrinterListResult) {
        Log.e(javaClass.name, "handleGetAllPrintersResult, result: $getPrinterListResult")
        when (getPrinterListResult) {
            is GetPrinterListResult.Success ->
                _printerOptions.value =
                    getPrinterListResult.printers.map { it.name }.toMutableList()

            is GetPrinterListResult.Failure -> {
                val currentState = _uiState.value
                _uiState.value =
                    currentState.softUpdate(
                        snackbarMessage = getPrinterListResult.exception.message,
                    )
            }
        }
    }

    private fun getColors() {
        _colorOptions.value = mutableListOf("Monochrome", "Color")
    }

    fun onPickDocumentResult(documentUris: List<String>) {
        documentUris.forEach { onFileAdded(it) }
    }

    private fun onFileAdded(documentUri: String) {
        try {
            val mimeType = fileProcessor.getMineType(documentUri)
            val fullFileName = fileProcessor.getFileName(documentUri)
            val isPasswordProtected = fileProcessor.isPasswordProtected(documentUri)
            val tempFile = fileProcessor.copyFileToCache(documentUri, fullFileName)

            val passwordStatus =
                if (isPasswordProtected) {
                    PasswordStatus.Password.Incorrect("")
                } else {
                    PasswordStatus.None
                }

            val file =
                File(
                    name = fullFileName,
                    uri = documentUri,
                    mimeType = mimeType,
                    color = File.Color.MONOCHROME,
                    copies = 1,
                    formFile = tempFile,
                    passwordStatus = passwordStatus,
                )
            addFile(file)
        } catch (exception: PrintMeException) {
            _uiState.value = _uiState.value.softUpdate(snackbarMessage = exception.message)
        }
    }

    private fun addFile(file: File) {
        val files = _uiState.value.files.toMutableList()
        files.add(file)
        _uiState.value = PrintDocumentUiState.Active(files)
    }

    @Suppress("UnusedPrivateMember")
    fun onPrintDocumentClick(
        colorExposedDropDownMenuState: ColorExposedDropDownMenuState,
        printerExposedDropDownMenuState: PrinterExposedDropDownMenuState,
    ) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val files = currentState.files
            val printerName =
                currentState.selectedPrinter
                    ?: printerExposedDropDownMenuState.value.selectedOption

            if (printerName.isBlank()) {
                val snackbarMessage = "Printer is not selected."
                _uiState.value =
                    if (currentState is PrintDocumentUiState.Active) {
                        currentState.copy(
                            snackbarMessage = snackbarMessage,
                        )
                    } else {
                        PrintDocumentUiState.Active(
                            currentState.files,
                            currentState.selectedPrinter,
                            snackbarMessage,
                            currentState.printDocumentBottomSheetStatus,
                        )
                    }
                return@launch
            }

            val result = printDocumentUseCase(files, printerName)
            when (result) {
                is PrintDocumentResult.Failure -> handlePrintDocumentResultFailure(result)
                is PrintDocumentResult.Success -> handlePrintDocumentResultSuccess(result)
            }

            deleteTempFiles(files)
        }
    }

    private fun deleteTempFiles(files: List<File>) {
        files.forEach {
            if (it.formFile.exists()) {
                it.formFile.delete()
            }
        }
    }

    private fun handlePrintDocumentResultFailure(result: PrintDocumentResult.Failure) {
        Log.e(javaClass.name, "handlePrintDocumentResultFailure, result: $result")
        result.exception.printStackTrace()
        _uiState.value = _uiState.value.softUpdate(snackbarMessage = result.exception.message)
    }

    private fun handlePrintDocumentResultSuccess(result: PrintDocumentResult.Success) {
        _uiState.value = PrintDocumentUiState.Initial
        Log.e(javaClass.name, "handlePrintDocumentResultSuccess,  result: $result")
    }

    fun onProceedClick() {
        _uiState.update {
            it.copyToActive(
                printDocumentBottomSheetStatus = PrintDocumentBottomSheetStatus.FilesOptions,
            )
        }
    }

    fun onItemClick(file: File) {
        _uiState.update {
            it.copyToActive(
                printDocumentBottomSheetStatus = PrintDocumentBottomSheetStatus.FileOptions(file),
            )
        }
    }

    fun onDeleteClick(file: File) {
        val files = _uiState.value.files.toMutableList()
        files.remove(file)
        _uiState.value = PrintDocumentUiState.Active(files)
    }

    fun onBottomSheetIsHidden() {
        val currentUiState = _uiState.value

        if (currentUiState is PrintDocumentUiState.Active &&
            currentUiState.printDocumentBottomSheetStatus !is PrintDocumentBottomSheetStatus.Hidden
        ) {
            _uiState.value =
                currentUiState.copy(
                    printDocumentBottomSheetStatus = PrintDocumentBottomSheetStatus.Hidden,
                )
        }
    }

    fun onSnackbarActionComplete() {
        val currentState = _uiState.value

        _uiState.value = currentState.softUpdate(snackbarMessage = null)
    }

    fun onSelectedFileOptionsMenuSaveClick(password: String) {
        val printDocumentBottomSheetStatus =
            uiState.value.printDocumentBottomSheetStatus

        if (printDocumentBottomSheetStatus !is PrintDocumentBottomSheetStatus.FileOptions) return

        val selectedFile = printDocumentBottomSheetStatus.file

        if (selectedFile.passwordStatus is PasswordStatus.Password &&
            selectedFile.passwordStatus.password == password
        ) {
            return
        }

        if (selectedFile.passwordStatus !is PasswordStatus.Password) return

        val validity = fileProcessor.validatePassword(selectedFile.uri, password)

        val passwordStatus =
            when (validity) {
                PasswordValidity.UNKNOWN -> PasswordStatus.Password.Unknown(password)
                PasswordValidity.VALID -> PasswordStatus.Password.Correct(password)
                PasswordValidity.INVALID -> PasswordStatus.Password.Incorrect(password)
            }

        _uiState.update {
            if (it !is PrintDocumentUiState.Active) return
            it.copy(
                files =
                    it.files.map { file ->
                        if (file == selectedFile) {
                            file.copy(passwordStatus = passwordStatus)
                        } else {
                            file
                        }
                    },
                printDocumentBottomSheetStatus = PrintDocumentBottomSheetStatus.Hidden,
            )
        }
    }
}

private fun PrintDocumentUiState.softUpdate(
    files: List<File> = this.files,
    selectedPrinter: String? = this.selectedPrinter,
    snackbarMessage: String? = this.snackbarMessage,
): PrintDocumentUiState =
    when (this) {
        is PrintDocumentUiState.Active ->
            this.copy(
                files,
                selectedPrinter,
                snackbarMessage,
            )

        is PrintDocumentUiState.Initial -> PrintDocumentUiState.Initial
    }
