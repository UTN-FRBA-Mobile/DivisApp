package com.gradientepolimorfico.monedapp.Services
import com.gradientepolimorfico.monedapp.Entities.DetalleDivisa
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface DetalleMonedaService {
    @GET("divisa/{codigo}")
    fun getDetalleDivisa(@Path("codigo") codigo : String): Observable<DetalleDivisa>

    companion object {
        fun create(): DetalleMonedaService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("http://192.168.1.142:8080/bdc/divisapp/")
                    .build()

            return retrofit.create(DetalleMonedaService::class.java)
        }
    }
}