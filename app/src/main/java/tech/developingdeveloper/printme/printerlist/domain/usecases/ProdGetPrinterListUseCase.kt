package tech.developingdeveloper.printme.printerlist.domain.usecases

import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import tech.developingdeveloper.printme.printerlist.domain.repository.PrinterListRepository
import tech.developingdeveloper.printme.printerlist.ui.PrinterUiItem

class ProdGetPrinterListUseCase(
    private val printerListRepository: PrinterListRepository
) : GetPrinterListUseCase {
    override fun getPrinters(): List<PrinterUiItem> {
        return printerListRepository.getPrinters().map { it.toPrinterUi() }
    }
}

private fun Printer.toPrinterUi() = PrinterUiItem(
    name = this.name,
    isAcceptingJobs = this.isAcceptingJobs
)