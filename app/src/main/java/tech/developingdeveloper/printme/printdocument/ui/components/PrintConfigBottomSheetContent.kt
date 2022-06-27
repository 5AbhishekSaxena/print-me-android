package tech.developingdeveloper.printme.printdocument.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu.PMExposedDropdownMenu
import tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu.PMExposedDropdownMenuState
import tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu.rememberPMExposedDropdownMenuState
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme

@Composable
fun PrintConfigBottomSheetContent(
    colorOptions: List<String>,
    colorExposedDropdownMenuState: PMExposedDropdownMenuState,
    printerOptions: List<String>,
    printerExposedDropdownMenuState: PMExposedDropdownMenuState,
    onPrintClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Heading()

        Spacer(modifier = Modifier.height(20.dp))

        ColorDropDownMenu(
            colorOptions = colorOptions,
            colorExposedDropdownMenuState = colorExposedDropdownMenuState
        )

        Spacer(modifier = Modifier.height(12.dp))

        PrinterDropdownMenu(
            printerOptions = printerOptions,
            printerExposedDropdownMenuState = printerExposedDropdownMenuState
        )

        Spacer(modifier = Modifier.height(12.dp))

        PrintButton(onPrintClick)
    }
}

@Composable
private fun Heading() {
    Text(text = "Configure Print")
}

@Composable
private fun ColorDropDownMenu(
    colorOptions: List<String>,
    colorExposedDropdownMenuState: PMExposedDropdownMenuState
) {
    PMExposedDropdownMenu(
        label = "Color",
        options = colorOptions,
        exposedDropdownMenuState = colorExposedDropdownMenuState
    )
}

@Composable
private fun PrinterDropdownMenu(
    printerOptions: List<String>,
    printerExposedDropdownMenuState: PMExposedDropdownMenuState
) {
    PMExposedDropdownMenu(
        label = "Printer",
        options = printerOptions,
        exposedDropdownMenuState = printerExposedDropdownMenuState
    )
}

@Composable
private fun PrintButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = "Print")
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
private fun PrintConfigBottomSheetPreview() {

    val colorOptions = listOf("Monochrome", "Color")
    val colorExposedDropdownMenuState =
        rememberPMExposedDropdownMenuState(initSelectedOption = colorOptions[0])

    val printerOptions = listOf("HP", "Fax")
    val printerExposedDropdownMenuState =
        rememberPMExposedDropdownMenuState(initSelectedOption = printerOptions[0])

    PrintMeTheme {
        Surface {
            PrintConfigBottomSheetContent(
                colorOptions = colorOptions,
                colorExposedDropdownMenuState = colorExposedDropdownMenuState,
                printerOptions = printerOptions,
                printerExposedDropdownMenuState = printerExposedDropdownMenuState,
                onPrintClick = {}
            )
        }
    }
}
