package com.gradientepolimorfico.monedapp.Factories

import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R

object FactoryDivisa {
    public val DIVISA_PESO_ARGENTINO        = "ARS"
    public val DIVISA_DOLAR_ESTADOUNIDENSE  = "USD"
    public val DIVISA_EURO_ESPANIOL         = "EUR"
    public val DIVISA_YEN_JAPONES           = "JPY"

    private var divisa : Divisa? = null

    fun create(codigo : String) : Divisa?{
        when(codigo){

            DIVISA_PESO_ARGENTINO -> this.divisa =  this.crearDivisa(
                    FactoryDivisa.DIVISA_PESO_ARGENTINO,
                    "Argentina",
                    "Peso argentino",
                    R.drawable.pais_ar
            )

            DIVISA_DOLAR_ESTADOUNIDENSE -> this.divisa =  this.crearDivisa(
                    FactoryDivisa.DIVISA_DOLAR_ESTADOUNIDENSE,
                    "Estados Unidos",
                    "Dólar",
                    R.drawable.pais_us
            )

            DIVISA_EURO_ESPANIOL -> this.divisa =  this.crearDivisa(
                    FactoryDivisa.DIVISA_EURO_ESPANIOL,
                    "España",
                    "Euro",
                    R.drawable.pais_es
            )

            DIVISA_YEN_JAPONES -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_YEN_JAPONES,
                    "Japón",
                    "Yen",
                    R.drawable.pais_uy
            )
        }
        return this.divisa
    }

    private fun crearDivisa(codigo : String, pais : String, moneda : String, bandera : Int) : Divisa{
        val divisa = Divisa()
        divisa.codigo   = codigo
        divisa.pais     = pais
        divisa.moneda   = moneda
        divisa.bandera  = bandera
        divisa.valor    = 1.0.toFloat()
        return divisa
    }
}