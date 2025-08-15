package tech.developingdeveloper.printme.core.data

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import tech.developingdeveloper.printme.printerlist.domain.models.Printer

interface PrinterApiService {
    @GET("/")
    suspend fun getPrinters(): List<Printer>

    @POST("print")
    suspend fun printDocument(
        @Body multipartBody: MultipartBody,
        @Query("printerName") printerName: String,
    ): Response<Unit>
}
