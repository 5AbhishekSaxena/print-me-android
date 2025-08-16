package tech.developingdeveloper.printme.printdocument.domain.models

import java.io.File

data class File(
    val name: String,
    val uri: String,
    val mimeType: String,
    val color: Color,
    val copies: Int,
    val formFile: File,
    val passwordStatus: PasswordStatus = PasswordStatus.None,
) {
    enum class Color {
        MONOCHROME,
        MULTI_COLOR,
    }
}
