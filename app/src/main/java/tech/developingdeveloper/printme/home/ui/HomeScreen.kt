package tech.developingdeveloper.printme.home.ui

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import tech.developingdeveloper.printme.destinations.PrintDocumentScreenDestination
import tech.developingdeveloper.printme.destinations.PrinterListScreenDestination

@Composable
@Destination
@RootNavGraph(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    HomeContent(
        onPrintDocumentClick = {
            navigator.navigate(PrintDocumentScreenDestination)
        },
        onViewPrintersClick = {
            navigator.navigate(PrinterListScreenDestination)
        }
    )
}
