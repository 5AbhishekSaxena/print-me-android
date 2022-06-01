package tech.developingdeveloper.printme.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Print
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tech.developingdeveloper.printme.R
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme

@Composable
fun HomeContent(
    onPrintDocumentClick: () -> Unit,
    onViewPrintersClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val (
            printPdfCard,
            viewPrintersCard,
            printPdfViewPrinterSpacer
        ) = createRefs()

        createVerticalChain(
            elements = arrayOf(printPdfCard, viewPrintersCard),
            chainStyle = ChainStyle.Packed
        )

        HomeCard(
            text = stringResource(R.string.print_document_pdf),
            leftDrawable = Icons.Filled.Print,
            onClick = onPrintDocumentClick,
            modifier = Modifier
                .constrainAs(printPdfCard) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
//                    bottom.linkTo(printPdfViewPrinterSpacer.top)
                    bottom.linkTo(viewPrintersCard.top)
                }
        )

        // the spacer is getting ignored
        Spacer(modifier = Modifier
            .height(16.dp)
            .constrainAs(printPdfViewPrinterSpacer) {
                start.linkTo(printPdfCard.start)
                end.linkTo(printPdfCard.end)
                top.linkTo(printPdfCard.bottom)
                bottom.linkTo(viewPrintersCard.top)
                height = Dimension.value(16.dp)
            }
        )

        HomeCard(
            text = stringResource(R.string.view_printers),
            leftDrawable = Icons.Filled.Info,
            onClick = onViewPrintersClick,
            modifier = Modifier
                .constrainAs(viewPrintersCard) {
                    start.linkTo(printPdfCard.start)
                    end.linkTo(printPdfCard.end)
//                    top.linkTo(printPdfViewPrinterSpacer.bottom)
                    top.linkTo(printPdfCard.bottom)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
        )
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
private fun HomeContentPreview() {
    PrintMeTheme {
        HomeContent(onPrintDocumentClick = {}, onViewPrintersClick = {})
    }
}