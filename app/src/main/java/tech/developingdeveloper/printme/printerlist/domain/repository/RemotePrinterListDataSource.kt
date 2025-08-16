package tech.developingdeveloper.printme.printerlist.domain.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tech.developingdeveloper.printme.core.data.PrinterApiService
import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import javax.inject.Inject

class RemotePrinterListDataSource @Inject constructor(
    private val printerApiService: PrinterApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : PrinterListDataSource {
    override suspend fun getPrinters(): List<Printer> =
        withContext(dispatcher) {
            printerApiService.getPrinters()
        }
}
