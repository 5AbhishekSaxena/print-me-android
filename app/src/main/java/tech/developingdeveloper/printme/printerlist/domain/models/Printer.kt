package tech.developingdeveloper.printme.printerlist.domain.models

import tech.developingdeveloper.printme.printerlist.domain.models.enums.PrinterIsAcceptingJobs

data class Printer(
    val name: String,
    val jobAcceptanceStatus: PrinterIsAcceptingJobs,
)
