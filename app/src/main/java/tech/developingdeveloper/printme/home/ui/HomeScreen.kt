package tech.developingdeveloper.printme.home.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import tech.developingdeveloper.printme.printdocument.ui.PrintDocumentDestination
import tech.developingdeveloper.printme.printerlist.ui.PrinterListDestination

@Composable
fun HomeScreen(navController: NavController) {
    HomeContent(
        onPrintDocumentClick = {
            navController.navigate(PrintDocumentDestination)
        },
        onViewPrintersClick = {
            navController.navigate(PrinterListDestination)
        },
    )
}
