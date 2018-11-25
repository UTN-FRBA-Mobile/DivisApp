package com.gradientepolimorfico.monedapp.Factories

import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias

object FactoryDivisa {
    public val DIVISA_PESO_ARGENTINO        = "ARS"
    public val DIVISA_DOLAR_ESTADOUNIDENSE  = "USD"
    public val DIVISA_EURO_ESPANIOL         = "EUR"
    public val DIVISA_YEN_JAPONES           = "JPY"
    public val DIVISA_REAL_BRASIL           = "BRL"
    public val DIVISA_LIBRA_INGLESA         = "GBP"
    public val DIVISA_PESO_URUGUAYO         = "UYU"
    public val DIVISA_PESO_CHILENO          = "CLP"

    val divisasDisponibles = arrayOf(DIVISA_PESO_ARGENTINO, DIVISA_DOLAR_ESTADOUNIDENSE, DIVISA_EURO_ESPANIOL, DIVISA_YEN_JAPONES,
            DIVISA_REAL_BRASIL, DIVISA_LIBRA_INGLESA, DIVISA_PESO_URUGUAYO, DIVISA_PESO_CHILENO)

    val divisaBaseDefault = DIVISA_PESO_ARGENTINO

    private var divisa : Divisa? = null

    fun create(codigo : String) : Divisa?{
        when(codigo){

            DIVISA_PESO_ARGENTINO -> this.divisa =  this.crearDivisa(
                    FactoryDivisa.DIVISA_PESO_ARGENTINO,
                    "Argentina",
                    "Peso Argentino",
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
                    R.drawable.pais_jp
            )

            DIVISA_REAL_BRASIL -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_REAL_BRASIL,
                    "Brasil",
                    "Real",
                    R.drawable.pais_br
            )

            DIVISA_LIBRA_INGLESA -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_LIBRA_INGLESA,
                    "Reino Unido",
                    "Libra",
                    R.drawable.pais_uk
            )

            DIVISA_PESO_URUGUAYO -> this.divisa =  this.crearDivisa(
                    FactoryDivisa.DIVISA_PESO_ARGENTINO,
                    "Uruguay",
                    "Peso Uruguayo",
                    R.drawable.pais_uy
            )

            DIVISA_PESO_CHILENO -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_PESO_CHILENO,
                    "Chile",
                    "Peso Chileno",
                    R.drawable.pais_ch
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