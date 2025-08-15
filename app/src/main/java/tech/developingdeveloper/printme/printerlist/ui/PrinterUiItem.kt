package tech.developingdeveloper.printme.printerlist.ui

import tech.developingdeveloper.printme.printerlist.domain.models.enums.PrinterIsAcceptingJobs

data class PrinterUiItem(
    val name: String,
    val isAcceptingJobs: PrinterIsAcceptingJobs,
)
