package tech.developingdeveloper.printme.printerlist.domain.repository

import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import tech.developingdeveloper.printme.printerlist.domain.models.enums.PrinterIsAcceptingJobs
import javax.inject.Inject

class DemoPrinterListRepository @Inject constructor() : PrinterListRepository {
    private val printers =
        listOf(
            Printer(
                name = "HP DJ 2130 series",
                jobAcceptanceStatus = PrinterIsAcceptingJobs.ACCEPTING,
            ),
            Printer(
                name = "OneNote for Windows 10",
                jobAcceptanceStatus = PrinterIsAcceptingJobs.NOT_ACCEPTING,
            ),
            Printer(
                name = "Microsoft XPS Document Writer",
                jobAcceptanceStatus = PrinterIsAcceptingJobs.ACCEPTING,
            ),
            Printer(
                name = "Microsoft Print to PDF",
                jobAcceptanceStatus = PrinterIsAcceptingJobs.NOT_ACCEPTING,
            ),
            Printer(
                name = "Fax",
                jobAcceptanceStatus = PrinterIsAcceptingJobs.NOT_ACCEPTING,
            ),
        )

    override suspend fun getPrinters(): Result<List<Printer>> = Result.Success(printers)
}
