package tech.developingdeveloper.printme.core.data

import retrofit2.http.GET
import tech.developingdeveloper.printme.printerlist.domain.models.Printer

interface PrinterApiService {

    @GET("/")
    suspend fun getPrinters(): List<Printer>
}
