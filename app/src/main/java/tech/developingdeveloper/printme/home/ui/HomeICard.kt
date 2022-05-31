package tech.developingdeveloper.printme.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.ui.theme.PrintMeTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeCard(
    text: String,
    onClick: () -> Unit,
    leftDrawable: ImageVector,
    modifier: Modifier = Modifier
    leftDrawableDescription: String? = null,
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            Modifier
                .padding(16.dp)
        ) {
            Image(
                imageVector = leftDrawable,
                contentDescription = leftDrawableDescription
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = text)
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
private fun HomeICardPreview() {
    PrintMeTheme {
        HomeCard(
            text = "Print Document(PDF)",
            leftDrawable = Icons.Filled.Print,
            onClick = {}
        )
    }
}