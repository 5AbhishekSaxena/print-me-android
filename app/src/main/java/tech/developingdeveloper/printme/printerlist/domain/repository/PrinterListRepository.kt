package tech.developingdeveloper.printme.printerlist.domain.repository

import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printerlist.domain.models.Printer

interface PrinterListRepository {
    suspend fun getPrinters(): Result<List<Printer>>
}
