package com.gradientepolimorfico.monedapp.Entities

import android.text.TextUtils
import com.github.mikephil.charting.data.Entry
import com.gradientepolimorfico.monedapp.Services.ExchangeRateResponse
import java.util.*


class Divisa {
    var codigo: String? = null
    var fecha: String? = null
    var valor: Float? = null
    var pais: String? = null
    var moneda: String? = null
    var bandera: Int? = null
    var dataRequested: Boolean = false
    var timeSeriesData = ArrayList<Entry>()
    var from: String? = null
    var lastUpdated: Date = Date(0)

    fun setDatos(body: ExchangeRateResponse) {
        this.fecha = body.lastRefreshed
        this.valor = body.exchangeRate
        this.timeSeriesData = body.data
        this.timeSeriesData.reverse()
        this.timeSeriesData.toSet()
        this.from = body.toCode.toString()
        this.lastUpdated = Date()
    }

    fun hayDatos(): Boolean {
        return this.timeSeriesData.count() > 0
    }

    private fun cantidadDatos(): Int {
        return this.timeSeriesData.size
    }

    fun ultimoCambio(): Float {
        var cantidadDatos = this.cantidadDatos()
        var anteultimo = this.timeSeriesData[cantidadDatos - 2]
        var ultimo = this.timeSeriesData[cantidadDatos - 1]
        var diferencia = anteultimo.y - ultimo.y
        return diferencia
    }

    fun ultimoCambioEnPorcentaje(): Float {
        var cantidadDatos = this.cantidadDatos()
        var anteultimo = this.timeSeriesData[cantidadDatos - 2]
        var ultimo = this.timeSeriesData[cantidadDatos - 1]
        var resultado = 0.0f
        if (this.ultimoCambio() > 0) {
            var incremento = ultimo.y - anteultimo.y
            resultado = (incremento / anteultimo.y) * 100
        } else {
            var reduccion = anteultimo.y - ultimo.y
            resultado = (reduccion / anteultimo.y) * 100
        }
        return resultado
    }

    fun timesSeriesDataToString(): String {
        return TextUtils.join(";", this.timeSeriesData)
    }

    fun getDataFromAPI() {

    }
}
