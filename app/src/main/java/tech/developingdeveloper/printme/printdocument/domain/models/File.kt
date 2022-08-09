package tech.developingdeveloper.printme.printdocument.domain.models

import android.net.Uri
import java.io.File

data class File(
    val name: String,
    val uri: Uri,
    val mimeType: String,
    val color: Color,
    val copies: Int,
    val formFile: File
) {
    enum class Color {
        MONOCHROME, MULTI_COLOR
    }
}
