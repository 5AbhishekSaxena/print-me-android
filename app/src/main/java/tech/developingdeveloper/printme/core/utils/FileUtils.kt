package tech.developingdeveloper.printme.core.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File

fun Uri.getFileName(context: Context): String? {
    var displayName: String? = null
    val uriString = this.toString()
    if (uriString.startsWith("content://")) {
        val cursor =
            context.contentResolver
                .query(this, null, null, null, null)

        cursor?.use {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                displayName = cursor.getString(columnIndex)
            }
        }
    } else if (uriString.startsWith("file://")) {
        displayName = File(uriString).name
    } else {
        displayName = ""
    }
    return displayName
}
