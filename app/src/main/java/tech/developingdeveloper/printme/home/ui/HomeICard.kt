package tech.developingdeveloper.printme.home.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme

@Composable
fun HomeCard(
    text: String,
    onClick: () -> Unit,
    leftDrawable: ImageVector,
    modifier: Modifier = Modifier,
    leftDrawableDescription: String? = null,
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        elevation = 2.dp,
        border =
            BorderStroke(
                width = 1.dp,
                color =
                    if (isSystemInDarkTheme()) {
                        MaterialTheme.colors.onSurface
                    } else {
                        Color.Transparent
                    },
            ),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = leftDrawable,
                contentDescription = leftDrawableDescription,
                tint = MaterialTheme.colors.onSurface,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = text)

            Spacer(modifier = Modifier.weight(1f))
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
            onClick = {},
        )
    }
}
