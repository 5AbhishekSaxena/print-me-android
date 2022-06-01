package tech.developingdeveloper.printme.printerlist.domain.usecases

import tech.developingdeveloper.printme.core.data.Result
import tech.developingdeveloper.printme.printerlist.domain.models.GetPrinterListResult
import tech.developingdeveloper.printme.printerlist.domain.models.Printer
import tech.developingdeveloper.printme.printerlist.domain.repository.PrinterListRepository

class ProdGetPrinterListUseCase(
    private val printerListRepository: PrinterListRepository
) : GetPrinterListUseCase {
    override fun getPrinters(): GetPrinterListResult {
        val result = printerListRepository.getPrinters()
        return when (result) {
            is Result.Failure -> handleFailure(result)
            is Result.Success -> handleSuccess(result)
        }
    }

    private fun handleFailure(result: Result.Failure): GetPrinterListResult.Failure {
        return GetPrinterListResult.Failure(result.error)
    }

    private fun handleSuccess(result: Result.Success<List<Printer>>): GetPrinterListResult.Success {
        return GetPrinterListResult.Success(result.data)
    }
}