package tech.developingdeveloper.printme.printerlist.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Print
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.R
import tech.developingdeveloper.printme.printerlist.domain.models.enums.PrinterIsAcceptingJobs
import tech.developingdeveloper.printme.core.ui.theme.DarkGreen
import tech.developingdeveloper.printme.core.ui.theme.DarkRed
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme

@Composable
fun PrinterListItem(
    printerUiItem: PrinterUiItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Image(
                imageVector = Icons.Default.Print,
                contentDescription = stringResource(id = R.string.print_icon),
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
            ) {
                Text(
                    text = printerUiItem.name,
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector =
                        if (printerUiItem.isAcceptingJobs == PrinterIsAcceptingJobs.ACCEPTING_JOBS)
                            Icons.Filled.CheckCircle
                        else
                            Icons.Filled.Cancel,
                        contentDescription = null,
                        colorFilter = if (printerUiItem.isAcceptingJobs == PrinterIsAcceptingJobs.ACCEPTING_JOBS)
                            ColorFilter.tint(DarkGreen)
                        else
                            ColorFilter.tint(DarkRed),
                        modifier = Modifier
                            .width(10.dp)
                            .height(10.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = printerUiItem.isAcceptingJobs.value,
                        fontSize = MaterialTheme.typography.body1.fontSize
                    )
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
@Suppress("UnusedPrivateMember")
private fun PrinterListItemPreview() {
    PrintMeTheme {

        val printerUiItem = PrinterUiItem(
            name = "Sample Printer",
            isAcceptingJobs = PrinterIsAcceptingJobs.ACCEPTING_JOBS
        )

        PrinterListItem(
            printerUiItem = printerUiItem,
            modifier = Modifier.fillMaxWidth()
        )
    }
}