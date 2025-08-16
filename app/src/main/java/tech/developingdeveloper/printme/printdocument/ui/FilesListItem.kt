package tech.developingdeveloper.printme.printdocument.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.developingdeveloper.printme.R
import tech.developingdeveloper.printme.core.ui.theme.LightGray
import tech.developingdeveloper.printme.core.ui.theme.PrintMeTheme
import tech.developingdeveloper.printme.core.ui.theme.Red
import tech.developingdeveloper.printme.printdocument.domain.models.File
import tech.developingdeveloper.printme.printdocument.domain.models.PasswordStatus

@Composable
fun FilesListItem(
    file: File,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
        ) {
            FileTypeImage()

            Spacer(modifier = Modifier.width(8.dp))

            FileDataColumn(
                file = file,
                modifier = Modifier.weight(1f),
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
        modifier =
            Modifier
                .width(50.dp)
                .height(50.dp),
    )
}

@Composable
private fun FileDataColumn(
    file: File,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        FileNameText(file.name)

        Spacer(modifier = Modifier.height(2.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            PageNumberText()

            if (file.passwordStatus !is PasswordStatus.None) {
                Spacer(modifier = Modifier.width(8.dp))
                PasswordProtection(
                    passwordStatus = file.passwordStatus,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}

@Composable
private fun FileNameText(file: String) {
    Text(
        text = file,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}

@Composable
private fun PageNumberText() {
    Text(
        text = "Pages: xx",
        color = LightGray,
    )
}

@Composable
private fun PasswordProtection(
    passwordStatus: PasswordStatus,
    modifier: Modifier = Modifier,
) {
    val imageVector =
        when (passwordStatus) {
            is PasswordStatus.None -> Icons.Default.Album // just a placeholder
            is PasswordStatus.Password.Unknown -> Icons.Default.LockReset
            is PasswordStatus.Password.Incorrect -> Icons.Default.Lock
            is PasswordStatus.Password.Correct -> Icons.Default.LockOpen
        }

    val tint =
        when (passwordStatus) {
            is PasswordStatus.None -> Color.Transparent
            is PasswordStatus.Password.Unknown ->
                LocalContentColor.current.copy(
                    alpha = LocalContentAlpha.current,
                )

            is PasswordStatus.Password.Correct ->
                if (isSystemInDarkTheme()) {
                    Color(0xFF228B22)
                } else {
                    Color(0xFF77DD77)
                }

            is PasswordStatus.Password.Incorrect ->
                if (isSystemInDarkTheme()) {
                    Color(0xFF800000)
                } else {
                    Color.Red
                }
        }

    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier,
        tint = tint,
    )
}

@Composable
private fun DeleteIcon(onClick: () -> Unit) {
    Image(
        imageVector = Icons.Outlined.RemoveCircleOutline,
        colorFilter = ColorFilter.tint(Red),
        contentDescription = stringResource(R.string.delete),
        modifier =
            Modifier
                .clickable(onClick = onClick)
                .padding(10.dp),
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
    val file =
        File(
            name = "Grad Hire - Poster v2.0.pdf",
            uri = "",
            mimeType = "",
            color = File.Color.MONOCHROME,
            copies = 1,
            formFile = java.io.File(""),
        )

    PrintMeTheme {
        Surface {
            Column {
                FilesListItem(file = file, onDeleteClick = {}, onClick = {})
                FilesListItem(
                    file = file.copy(passwordStatus = PasswordStatus.Password.Unknown("test")),
                    onDeleteClick = {},
                    onClick = {},
                )
                FilesListItem(
                    file = file.copy(passwordStatus = PasswordStatus.Password.Incorrect("test")),
                    onDeleteClick = {},
                    onClick = {},
                )
                FilesListItem(
                    file = file.copy(passwordStatus = PasswordStatus.Password.Correct("test")),
                    onDeleteClick = {},
                    onClick = {},
                )
            }
        }
    }
}
