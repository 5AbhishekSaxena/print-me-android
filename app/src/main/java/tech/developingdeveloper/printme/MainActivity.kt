package tech.developingdeveloper.printme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme
import tech.developingdeveloper.printme.home.ui.HomeDestination
import tech.developingdeveloper.printme.home.ui.HomeScreen
import tech.developingdeveloper.printme.printdocument.ui.PrintDocumentDestination
import tech.developingdeveloper.printme.printdocument.ui.PrintDocumentScreen
import tech.developingdeveloper.printme.printerlist.ui.PrinterListDestination
import tech.developingdeveloper.printme.printerlist.ui.PrinterListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()
            PrintMeTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    scaffoldState = scaffoldState,
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = HomeDestination,
                        modifier =
                            Modifier
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                                .padding(WindowInsets.systemBars.asPaddingValues())
                                .fillMaxSize(),
                    ) {
                        composable<HomeDestination> {
                            HomeScreen(navController)
                        }

                        composable<PrintDocumentDestination> {
                            PrintDocumentScreen(scaffoldState = scaffoldState)
                        }

                        composable<PrinterListDestination> {
                            PrinterListScreen()
                        }
                    }
                }
            }
        }
    }
}
