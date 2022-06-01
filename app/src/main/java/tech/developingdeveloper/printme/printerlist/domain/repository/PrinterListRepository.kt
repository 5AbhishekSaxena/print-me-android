package tech.developingdeveloper.printme.printerlist.domain.repository

import tech.developingdeveloper.printme.printerlist.domain.models.Printer

interface PrinterListRepository {

    fun getPrinters(): List<Printer>
}