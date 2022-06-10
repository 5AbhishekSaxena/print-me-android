package tech.developingdeveloper.printme.printdocument.domain.repository

import tech.developingdeveloper.printme.core.data.Result
import javax.inject.Inject

class DemoPrintDocumentRepository @Inject constructor() : PrintDocumentRepository {
    override fun printDocuments(): Result<String> {
        return Result.Success("Printed successfully.")
    }
}
