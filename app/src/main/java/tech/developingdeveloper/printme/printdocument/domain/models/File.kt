package tech.developingdeveloper.printme.printdocument.domain.models

import android.net.Uri

data class File(
    val name: String,
    val uri: Uri,
    val color: Color,
    val copies: Int
) {
    enum class Color {
        MONOCHROME, MULTI_COLOR
    }
}
