package tech.developingdeveloper.printme.printerlist.domain.repository

import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import tech.developingdeveloper.printme.printerlist.domain.models.enums.PrinterIsAcceptingJobs
import javax.inject.Inject

class DemoPrinterListRepository @Inject constructor() : PrinterListRepository {

    private val printers = listOf(
        Printer(
            name = "HP DJ 2130 series",
            isAcceptingJobs = PrinterIsAcceptingJobs.ACCEPTING_JOBS,
        ),
        Printer(
            name = "OneNote for Windows 10",
            isAcceptingJobs = PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS,
        ),
        Printer(
            name = "Microsoft XPS Document Writer",
            isAcceptingJobs = PrinterIsAcceptingJobs.ACCEPTING_JOBS,
        ),
        Printer(
            name = "Microsoft Print to PDF",
            isAcceptingJobs = PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS,
        ),
        Printer(
            name = "Fax",
            isAcceptingJobs = PrinterIsAcceptingJobs.NOT_ACCEPTING_JOBS,
        )
    )

    override suspend fun getPrinters(): Result<List<Printer>> = Result.Success(printers)
}
