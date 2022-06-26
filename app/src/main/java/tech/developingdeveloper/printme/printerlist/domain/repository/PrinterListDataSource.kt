package tech.developingdeveloper.printme.printerlist.domain.repository

import tech.developingdeveloper.printme.printerlist.domain.models.Printer

interface PrinterListDataSource {

    suspend fun getPrinters(): List<Printer>
}
