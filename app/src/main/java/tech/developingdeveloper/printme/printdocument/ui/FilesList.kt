package tech.developingdeveloper.printme.printdocument.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme

@Composable
fun FilesList(
    files: List<String>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(files) {
            FilesListItem(file = it, onDeleteClick = {})
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
private fun FilesListPreview() {

    val files = (1..10).map {
        "Grad Hire v2.0.pdf"
    }

    PrintMeTheme {
        Surface {
            FilesList(files)
        }
    }
}
