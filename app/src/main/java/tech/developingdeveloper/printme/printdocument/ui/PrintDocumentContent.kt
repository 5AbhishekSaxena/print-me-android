package tech.developingdeveloper.printme.printdocument.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.R
import tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu.PMExposedDropdownMenuState
import tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu.rememberPMExposedDropdownMenuState
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme
import tech.developingdeveloper.printme.core.ui.theme.VeryLightGray
import tech.developingdeveloper.printme.printdocument.domain.models.ColorExposedDropDownMenuState
import tech.developingdeveloper.printme.printdocument.domain.models.File
import tech.developingdeveloper.printme.printdocument.domain.models.PrinterExposedDropDownMenuState
import tech.developingdeveloper.printme.printdocument.ui.components.PrintConfigBottomSheetContent

@Composable
fun PrintDocumentContent(
    uiState: PrintDocumentUiState,
    onSelectClick: () -> Unit,
    onProceedClick: () -> Unit,
    onDeleteClick: (File) -> Unit,
    bottomSheetState: ModalBottomSheetState,
    colorOptions: List<String>,
    colorExposedDropdownMenuState: PMExposedDropdownMenuState,
    printerOptions: List<String>,
    printerExposedDropdownMenuState: PMExposedDropdownMenuState,
    onBottomSheetPrintClick: (
        ColorExposedDropDownMenuState,
        PrinterExposedDropDownMenuState,
    ) -> Unit,
) {
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 8.dp,
        sheetContent = {
            PrintConfigBottomSheetContent(
                colorOptions = colorOptions,
                colorExposedDropdownMenuState = colorExposedDropdownMenuState,
                printerOptions = printerOptions,
                printerExposedDropdownMenuState = printerExposedDropdownMenuState,
                onPrintClick = onBottomSheetPrintClick,
            )
        },
    ) {
        ContentColumn(
            onSelectClick = onSelectClick,
            uiState = uiState,
            onDeleteClick = onDeleteClick,
            onProceedClick = onProceedClick,
        )
    }
}

@Composable
private fun ContentColumn(
    onSelectClick: () -> Unit,
    uiState: PrintDocumentUiState,
    onDeleteClick: (File) -> Unit,
    onProceedClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Heading()

        Spacer(modifier = Modifier.height(16.dp))

        SelectDocumentCard(onSelectClick = onSelectClick)

        Spacer(modifier = Modifier.height(12.dp))

        FilesList(
            files = uiState.files,
            onDeleteClick = onDeleteClick,
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProceedButton(uiState = uiState, onClick = onProceedClick)
    }
}

@Composable
private fun Heading() {
    Text(
        text = stringResource(R.string.print_documents),
        fontSize = MaterialTheme.typography.h4.fontSize,
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
    )
}

@Composable
fun SelectDocumentCard(onSelectClick: () -> Unit) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .background(VeryLightGray)
                    .padding(8.dp),
        ) {
            Text(
                text = "Select File(s)",
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = onSelectClick) {
                Text(text = stringResource(R.string.select))
            }
        }
    }
}

@Composable
private fun ProceedButton(
    uiState: PrintDocumentUiState,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = (uiState as? PrintDocumentUiState.Active)?.files?.isNotEmpty() ?: false,
    ) {
        Text(text = "Proceed")
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("UnusedPrivateMember", "MagicNumber")
private fun PrintDocumentContentPreview() {
    val files =
        (1..10).map {
            File(
                name = "Grad Hire - Poster v2.0.pdf",
                uri = "",
                mimeType = "",
                color = File.Color.MONOCHROME,
                copies = 1,
                formFile = java.io.File(""),
            )
        }

    val uiState = PrintDocumentUiState.Active(files)

    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val colorOptions = listOf("Monochrome", "Color")
    val colorExposedDropdownMenuState =
        rememberPMExposedDropdownMenuState(initSelectedOption = colorOptions[0])

    val printerOptions = listOf("HP", "Fax")
    val printerExposedDropdownMenuState =
        rememberPMExposedDropdownMenuState(initSelectedOption = printerOptions[0])

    PrintMeTheme {
        Surface {
            PrintDocumentContent(
                uiState = uiState,
                onSelectClick = {},
                onProceedClick = {},
                onDeleteClick = {},
                bottomSheetState = bottomSheetState,
                colorOptions = colorOptions,
                colorExposedDropdownMenuState = colorExposedDropdownMenuState,
                printerOptions = printerOptions,
                printerExposedDropdownMenuState = printerExposedDropdownMenuState,
                onBottomSheetPrintClick = { _, _ ->
                },
            )
        }
    }
}
