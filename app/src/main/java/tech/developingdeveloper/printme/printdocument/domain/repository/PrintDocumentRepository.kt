package tech.developingdeveloper.printme.printdocument.domain.repository

import tech.developingdeveloper.printme.core.data.Result

interface PrintDocumentRepository {

    fun printDocuments(): Result<String>
}
