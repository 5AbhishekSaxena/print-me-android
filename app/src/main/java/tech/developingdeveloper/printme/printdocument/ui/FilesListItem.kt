package tech.developingdeveloper.printme.printdocument.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import tech.developingdeveloper.printme.R
import tech.developingdeveloper.printme.core.ui.theme.LightGray
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme
import tech.developingdeveloper.printme.core.ui.theme.Red
import tech.developingdeveloper.printme.printdocument.domain.models.File

@Composable
fun FilesListItem(
    file: File,
    onDeleteClick: () -> Unit
) {
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            FileTypeImage()

            Spacer(modifier = Modifier.width(8.dp))

            FileDataColumn(
                file = file,
                modifier = Modifier.weight(1f)
            )

            DeleteIcon(onClick = onDeleteClick)
        }
    }
}

@Composable
private fun FileTypeImage() {
    Image(
        painter = painterResource(id = R.drawable.pdf_file_icon),
        contentDescription = stringResource(R.string.pdf_icon),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
    )
}

@Composable
private fun FileDataColumn(
    file: File,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        FileNameText(file.name)

        Spacer(modifier = Modifier.height(2.dp))

        PageNumberText()
    }
}

@Composable
private fun FileNameText(file: String) {
    Text(
        text = file,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
private fun PageNumberText() {
    Text(
        text = "Pages: xx",
        color = LightGray
    )
}

@Composable
private fun DeleteIcon(onClick: () -> Unit) {
    Image(
        imageVector = Icons.Outlined.RemoveCircleOutline,
        colorFilter = ColorFilter.tint(Red),
        contentDescription = stringResource(R.string.delete),
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(10.dp)
    )
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
private fun FilesListItemPreview() {

    val file = File(
        name = "Grad Hire - Poster v2.0.pdf",
        uri = "".toUri(),
        color = File.Color.MONOCHROME,
        copies = 1
    )

    PrintMeTheme {
        Surface {
            FilesListItem(file = file, onDeleteClick = {})
        }
    }
}
