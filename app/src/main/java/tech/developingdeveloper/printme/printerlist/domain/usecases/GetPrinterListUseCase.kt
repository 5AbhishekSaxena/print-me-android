package tech.developingdeveloper.printme.printerlist.domain.usecases

import tech.developingdeveloper.printme.printerlist.domain.models.GetPrinterListResult

interface GetPrinterListUseCase {

    fun getPrinters(): GetPrinterListResult
}