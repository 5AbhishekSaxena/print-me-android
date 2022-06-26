package tech.developingdeveloper.printme.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import tech.developingdeveloper.printme.core.data.PrinterApiService
import tech.developingdeveloper.printme.core.data.RetrofitClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        return RetrofitClient().getClient()
    }

    @Singleton
    @Provides
    fun providePrintApiService(
        retrofit: Retrofit
    ): PrinterApiService {
        return retrofit.create(PrinterApiService::class.java)
    }
}
