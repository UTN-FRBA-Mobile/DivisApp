package com.gradientepolimorfico.monedapp.Entities

import com.github.mikephil.charting.data.Entry
import com.google.gson.annotations.SerializedName



class Divisa {
    var codigo:   String?       = null
    var fecha:  String?         = null
    var valor:  Float?          = null
    var pais:   String?         = null
    var moneda: String?         = null
    var bandera: Int?           = null
    var dataRequested: Boolean  = false
    var timeSeriesData          = ArrayList<Entry>()

    fun hayDatos() : Boolean{
        return !this.timeSeriesData.isEmpty()
    }

    private fun cantidadDatos() : Int{
        return this.timeSeriesData.size
    }

    fun ultimoCambio() : Float{
        var cantidadDatos = this.cantidadDatos()
        var anteultimo    = this.timeSeriesData[cantidadDatos-2]
        var ultimo        = this.timeSeriesData[cantidadDatos-1]
        var diferencia = anteultimo.y - ultimo.y
        return diferencia
    }

    fun ultimoCambioEnPorcentaje() : Float{
        var cantidadDatos = this.cantidadDatos()
        var anteultimo    = this.timeSeriesData[cantidadDatos-2]
        var ultimo        = this.timeSeriesData[cantidadDatos-1]
        var resultado = 0.0f
        if(this.ultimoCambio()>0){
            var incremento = ultimo.y - anteultimo.y
            resultado = (incremento/anteultimo.y)*100
        }
        else{
            var reduccion = anteultimo.y - ultimo.y
            resultado = (reduccion/anteultimo.y)*100
        }
        return resultado
    }

}
