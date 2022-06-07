package tech.developingdeveloper.printme.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.developingdeveloper.printme.printerlist.domain.usecases.GetAllPrintersUseCase
import tech.developingdeveloper.printme.printerlist.domain.usecases.ProdGetAllPrintersUseCase

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetAllPrintersUseCase(
        getAllPrintersUseCase: ProdGetAllPrintersUseCase
    ): GetAllPrintersUseCase
}
