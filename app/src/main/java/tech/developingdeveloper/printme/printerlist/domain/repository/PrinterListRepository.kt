package tech.developingdeveloper.printme.printerlist.domain.repository

import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import tech.developingdeveloper.printme.ui.models.Result

interface PrinterListRepository {

    fun getPrinters(): Result<List<Printer>>
}