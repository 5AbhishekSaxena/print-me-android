package tech.developingdeveloper.printme.printdocument.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme

@Composable
fun SelectedFileOptionsMenu(
    initialPassword: String,
    onSaveClick: (String) -> Unit,
) {
    var password by remember { mutableStateOf(initialPassword) }

    Column(
        modifier =
            Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        Heading()

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Password") },
            label = { Text("Password") },
        )

        Spacer(modifier = Modifier.height(12.dp))

        SaveButton(onClick = { onSaveClick(password) })
    }
}

@Composable
private fun Heading() {
    Text(
        text = "File Options",
        fontSize = MaterialTheme.typography.h5.fontSize,
    )
}

@Composable
private fun SaveButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        Text(text = "Save")
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
private fun SelectedFileOptionsMenuPreview() {
    PrintMeTheme {
        Surface {
            SelectedFileOptionsMenu(
                initialPassword = "",
                onSaveClick = {},
            )
        }
    }
}
