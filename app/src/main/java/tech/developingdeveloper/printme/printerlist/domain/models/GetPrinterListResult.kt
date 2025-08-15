package tech.developingdeveloper.printme.printerlist.domain.models

import tech.developingdeveloper.printme.core.PrintMeException

sealed class GetPrinterListResult {
    data class Success(
        val printers: List<Printer>,
    ) : GetPrinterListResult()

    data class Failure(
        val exception: PrintMeException,
    ) : GetPrinterListResult()
}
