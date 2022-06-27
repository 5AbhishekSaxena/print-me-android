package tech.developingdeveloper.printme.printerlist.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.core.ui.theme.DarkRed
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme
import tech.developingdeveloper.printme.printerlist.domain.models.enums.PrinterIsAcceptingJobs

@Composable
fun PrinterListContent(
    printerListUiState: PrinterListUiState
) {
    if (printerListUiState is PrinterListUiState.Loading)
        PrinterListLoading()

    if (printerListUiState is PrinterListUiState.Loaded)
        PrinterList(printerListUiState)

    if (printerListUiState is PrinterListUiState.Error)
        PrinterListError(printerListUiState)
}

@Composable
private fun PrinterListLoading() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Fetching printers...")
    }
}

@Composable
private fun PrinterList(printerListUiState: PrinterListUiState.Loaded) {
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

        items(printerListUiState.printers) {
            PrinterListItem(
                printerUiItem = it,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PrinterListError(printerListUiState: PrinterListUiState.Error) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        Image(
            imageVector = Icons.Outlined.Error,
            contentDescription = null,
            colorFilter = ColorFilter.tint(DarkRed),
            modifier = Modifier
                .height(72.dp)
                .width(72.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Failed to load printers.",
            fontSize = MaterialTheme.typography.h4.fontSize
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Error: ${printerListUiState.errorMessage}")
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
private fun PrinterListPreview() {
    PrintMeTheme {
        Surface {
            val printers = (1..10).map {
                PrinterUiItem(
                    name = "Sample Printer",
                    isAcceptingJobs =
                    if (it % 2 == 0)
                        PrinterIsAcceptingJobs.ACCEPTING_JOBS
                    else
                        PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS
                )
            }

            val state = PrinterListUiState.Loaded(printers)

            PrinterListContent(state)
        }
    }
}
