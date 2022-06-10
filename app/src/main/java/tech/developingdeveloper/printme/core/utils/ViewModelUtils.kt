package tech.developingdeveloper.printme.core.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel

fun AndroidViewModel.getContext(): Context? {
    return getApplication<Application>().applicationContext
}
