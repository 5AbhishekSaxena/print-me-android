package tech.developingdeveloper.printme.printerlist.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.ui.theme.PrintMeTheme

@Composable
fun PrinterListContent(
    printers: List<PrinterUiItem>
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {

        stickyHeader {
            Text(
                text = "Printers",
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.h4.fontSize,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }

        items(printers) {
            PrinterListItem(
                printerUiItem = it,
                modifier = Modifier
                    .fillMaxWidth()
            )
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
@Suppress("UnusedPrivateMember")
private fun PrinterListPreview() {
    PrintMeTheme {

        val printers = (1..10).map {
            PrinterUiItem(
                name = "Sample Printer",
                isAcceptingJobs =
                if (it % 2 == 0)
                    PrinterUiItem.PrinterIsAcceptingJobs.ACCEPTING_JOBS
                else
                    PrinterUiItem.PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS
            )
        }

        PrinterListContent(printers)
    }
}