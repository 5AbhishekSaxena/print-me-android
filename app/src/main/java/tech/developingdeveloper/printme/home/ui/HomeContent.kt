package tech.developingdeveloper.printme.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Print
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.R
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme

@Composable
fun HomeContent(
    onPrintDocumentClick: () -> Unit,
    onViewPrintersClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.width(IntrinsicSize.Max),
        ) {
            HomeCard(
                text = stringResource(R.string.print_document_pdf),
                leftDrawable = Icons.Filled.Print,
                onClick = onPrintDocumentClick,
                modifier = Modifier.fillMaxWidth(),
            )

            HomeCard(
                text = stringResource(R.string.view_printers),
                leftDrawable = Icons.Filled.Info,
                onClick = onViewPrintersClick,
                modifier = Modifier.fillMaxWidth(),
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
    showBackground = true,
)
@Composable
@Suppress("UnusedPrivateMember")
private fun HomeContentPreview() {
    PrintMeTheme {
        HomeContent(onPrintDocumentClick = {}, onViewPrintersClick = {})
    }
}
