package tech.developingdeveloper.printme.printerlist.domain.repository

import tech.developingdeveloper.printme.core.PrintMeException
import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import javax.inject.Inject

class ProdPrinterListRepository @Inject constructor(
    private val printerListDataSource: PrinterListDataSource
) : PrinterListRepository {
    override suspend fun getPrinters(): Result<List<Printer>> {
        return try {
            val printers = printerListDataSource.getPrinters()
            Result.Success(data = printers)
        } catch (exception: PrintMeException) {
            Result.Failure(error = exception)
        }
    }
}
