package tech.developingdeveloper.printme.printerlist.domain.usecases

import tech.developingdeveloper.printme.printerlist.ui.PrinterUiItem

interface GetPrinterListUseCase {

    fun getPrinters(): List<PrinterUiItem>
}