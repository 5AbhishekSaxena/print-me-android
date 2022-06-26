package tech.developingdeveloper.printme.printerlist.domain.repository

import tech.developingdeveloper.printme.core.data.PrinterApiService
import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import javax.inject.Inject

class RemotePrinterListDataSource @Inject constructor(
    private val printerApiService: PrinterApiService
) : PrinterListDataSource {
    override suspend fun getPrinters(): List<Printer> {
        return printerApiService.getPrinters()
    }
}
