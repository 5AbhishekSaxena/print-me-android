package tech.developingdeveloper.printme.printdocument.domain.models

data class File(
    val name: String,
    val uri: String,
    val mimeType: String,
    val color: Color,
    val copies: Int,
    val passwordStatus: PasswordStatus = PasswordStatus.None,
) {
    enum class Color {
        MONOCHROME,
        MULTI_COLOR,
    }
}
