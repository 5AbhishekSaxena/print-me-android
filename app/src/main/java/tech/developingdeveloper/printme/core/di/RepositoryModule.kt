package tech.developingdeveloper.printme.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.developingdeveloper.printme.printdocument.domain.repository.DemoPrintDocumentRepository
import tech.developingdeveloper.printme.printdocument.domain.repository.PrintDocumentRepository
import tech.developingdeveloper.printme.printerlist.domain.repository.PrinterListRepository
import tech.developingdeveloper.printme.printerlist.domain.repository.ProdPrinterListRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPrinterListRepository(
        printerListRepository: ProdPrinterListRepository
    ): PrinterListRepository

    @Binds
    abstract fun bindPrintDocumentRepository(
        printDocumentRepository: DemoPrintDocumentRepository
    ): PrintDocumentRepository
}
