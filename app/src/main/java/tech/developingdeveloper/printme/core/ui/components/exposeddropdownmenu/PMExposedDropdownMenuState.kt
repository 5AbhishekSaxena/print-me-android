package tech.developingdeveloper.printme.core.ui.components.exposeddropdownmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberPMExposedDropdownMenuState(initSelectedOption: String): PMExposedDropdownMenuState {
    return rememberSaveable(
        saver = PMExposedDropdownMenuState.Saver,
        init = {
            PMExposedDropdownMenuState(initSelectedOption)
        },
    )
}

class PMExposedDropdownMenuState(
    initialValue: String,
) {
    var selectedOption: String by mutableStateOf(initialValue)

    companion object {
        val Saver: Saver<PMExposedDropdownMenuState, *> =
            Saver(
                save = { it.selectedOption },
                restore = {
                    PMExposedDropdownMenuState(it)
                },
            )
    }
}
