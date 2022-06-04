package tech.developingdeveloper.printme.printerlist.domain.models

sealed class GetPrinterListResult {

    data class Success(val printers: List<Printer>) : GetPrinterListResult()
    data class Failure(val exception: Exception) : GetPrinterListResult()
}
