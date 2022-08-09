package tech.developingdeveloper.printme.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.developingdeveloper.printme.printdocument.domain.repository.PrintDocumentDataSource
import tech.developingdeveloper.printme.printdocument.domain.repository.RemotePrintDocumentDataSource
import tech.developingdeveloper.printme.printerlist.domain.repository.PrinterListDataSource
import tech.developingdeveloper.printme.printerlist.domain.repository.RemotePrinterListDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindPrinterDataSource(
        printerDataSource: RemotePrinterListDataSource
    ): PrinterListDataSource

    @Binds
    abstract fun bindPrintDocumentDataSource(
        printDocumentDataSource: RemotePrintDocumentDataSource
    ): PrintDocumentDataSource
}
