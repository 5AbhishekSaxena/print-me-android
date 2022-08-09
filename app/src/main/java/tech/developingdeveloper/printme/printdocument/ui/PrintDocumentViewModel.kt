package tech.developingdeveloper.printme.printdocument.ui

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.apache.commons.io.IOUtils
import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.core.utils.getContext
import tech.developingdeveloper.printme.core.utils.getFileName
import tech.developingdeveloper.printme.printdocument.domain.models.ColorExposedDropDownMenuState
import tech.developingdeveloper.printme.printdocument.domain.models.File
import tech.developingdeveloper.printme.printdocument.domain.models.PrintDocumentResult
import tech.developingdeveloper.printme.printdocument.domain.models.PrinterExposedDropDownMenuState
import tech.developingdeveloper.printme.printdocument.domain.usecases.PrintDocumentUseCase
import tech.developingdeveloper.printme.printerlist.domain.models.GetPrinterListResult
import tech.developingdeveloper.printme.printerlist.domain.usecases.GetAllPrintersUseCase
import java.io.FileOutputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class PrintDocumentViewModel @Inject constructor(
    application: Application,
    private val printDocumentUseCase: PrintDocumentUseCase,
    private val getAllPrintersUseCase: GetAllPrintersUseCase,
) : AndroidViewModel(application) {

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
                    currentState.softUpdate(snackbarMessage = getPrinterListResult.exception.message)
            }
        }
    }

    private fun getColors() {
        _colorOptions.value = mutableListOf("Monochrome", "Color")
    }

    fun onPickDocumentResult(documentUri: Uri?) {
        if (documentUri == null) return
        onFileAdded(documentUri)
    }

    private fun onFileAdded(documentUri: Uri) {

        var fin: InputStream? = null
        var fout: FileOutputStream? = null
        var tempFile: java.io.File? = null

        try {
            Log.e(javaClass.name, "onFileAdded, documentUri: $documentUri")

            val context = getContext() ?: return

            val fullFileName = documentUri.getFileName(context) ?: return
            val mimeType = context.contentResolver.getType(documentUri)
                ?: throw PrintMeException("Unable to get mime type of the file.")

            fin = context.contentResolver.openInputStream(documentUri)
                ?: throw PrintMeException("Failed to get input stream for the selected file.")

            tempFile = java.io.File(context.cacheDir, fullFileName)
            tempFile.createNewFile()

            fout = tempFile.outputStream()
            IOUtils.copy(fin, fout)

            val file = File(
                name = fullFileName,
                uri = documentUri,
                mimeType = mimeType,
                color = File.Color.MONOCHROME,
                copies = 1,
                formFile = tempFile
            )
            addFile(file)
        } catch (exception: PrintMeException) {
            _uiState.value = _uiState.value.softUpdate(snackbarMessage = exception.message)
        } finally {
            fin?.close()
            fout?.close()
            tempFile?.deleteOnExit()
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
        printerExposedDropDownMenuState: PrinterExposedDropDownMenuState
    ) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val files = currentState.files
            val printerName =
                currentState.selectedPrinter ?: printerExposedDropDownMenuState.value.selectedOption

            if (printerName == null) {
                val snackbarMessage = "Printer is not selected."
                _uiState.value = if (currentState is PrintDocumentUiState.Active) currentState.copy(
                    snackbarMessage = snackbarMessage
                ) else PrintDocumentUiState.Active(
                    currentState.files,
                    currentState.selectedPrinter,
                    snackbarMessage,
                    currentState.isBottomSheetVisible
                )
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
            if (it.formFile.exists())
                it.formFile.delete()
        }
    }

    private fun handlePrintDocumentResultFailure(result: PrintDocumentResult.Failure) {
        Log.e(javaClass.name, "handlePrintDocumentResultFailure, result: $result")
        result.exception.printStackTrace()
        _uiState.value = _uiState.value.softUpdate(
            snackbarMessage = result.exception.message ?: "Something went wrong."
        )
    }

    private fun handlePrintDocumentResultSuccess(result: PrintDocumentResult.Success) {
        _uiState.value = PrintDocumentUiState.Initial
        Log.e(javaClass.name, "handlePrintDocumentResultSuccess,  result: $result")
    }

    fun onProceedClick() {
        val currentUiState = _uiState.value
        _uiState.value =
            if (currentUiState is PrintDocumentUiState.Active)
                currentUiState.copy(isBottomSheetVisible = true)
            else
                PrintDocumentUiState.Active(files = emptyList(), isBottomSheetVisible = true)
    }

    fun onDeleteClick(file: File) {
        val files = _uiState.value.files.toMutableList()
        files.remove(file)
        _uiState.value = PrintDocumentUiState.Active(files)
    }

    fun onBottomSheetIsHidden() {
        val currentUiState = _uiState.value

        if (currentUiState is PrintDocumentUiState.Active && currentUiState.isBottomSheetVisible)
            _uiState.value = currentUiState.copy(isBottomSheetVisible = false)
    }

    fun onSnackbarActionComplete() {
        val currentState = _uiState.value

        _uiState.value = currentState.softUpdate(snackbarMessage = null)
    }
}

private fun PrintDocumentUiState.softUpdate(
    files: List<File> = this.files,
    selectedPrinter: String? = this.selectedPrinter,
    snackbarMessage: String? = this.snackbarMessage,
    isBottomSheetVisible: Boolean = this.isBottomSheetVisible,
): PrintDocumentUiState {
    return when (this) {
        is PrintDocumentUiState.Active -> this.copy(
            files,
            selectedPrinter,
            snackbarMessage,
            isBottomSheetVisible
        )
        is PrintDocumentUiState.Initial -> PrintDocumentUiState.Initial
    }
}
