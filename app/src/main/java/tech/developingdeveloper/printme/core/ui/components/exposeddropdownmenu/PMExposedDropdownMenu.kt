package tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme

@Composable
fun PMExposedDropdownMenu(
    label: String,
    options: List<String>,
    exposedDropdownMenuState: PMExposedDropdownMenuState,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier,
    ) {
        TextField(
            readOnly = true,
            value = exposedDropdownMenuState.selectedOption,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    exposedDropdownMenuState.selectedOption = option
                    expanded = false
                }) {
                    Text(text = option)
                }
            }
        }
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
private fun PMExposedDropdownMenuPreview() {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    val exposedDropdownMenuState =
        rememberPMExposedDropdownMenuState(initSelectedOption = options[0])

    PrintMeTheme {
        Surface {
            Column {
                PMExposedDropdownMenu(
                    label = "Test",
                    options = options,
                    exposedDropdownMenuState = exposedDropdownMenuState,
                )

                Text("Selected Option: ${exposedDropdownMenuState.selectedOption}")
            }
        }
    }
}
