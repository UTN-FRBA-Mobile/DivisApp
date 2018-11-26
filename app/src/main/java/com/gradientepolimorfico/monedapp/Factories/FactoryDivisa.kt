package com.gradientepolimorfico.monedapp.Factories

import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R

object FactoryDivisa {
    val DIVISA_PESO_ARGENTINO = "ARS"
    val DIVISA_DOLAR_ESTADOUNIDENSE = "USD"
    val DIVISA_EURO_ESPANIOL = "EUR"
    val DIVISA_YEN_JAPONES = "JPY"
    val DIVISA_REAL_BRASIL = "BRL"
    val DIVISA_LIBRA_INGLESA = "GBP"
    val DIVISA_PESO_URUGUAYO = "UYU"
    val DIVISA_PESO_CHILENO = "CLP"

    private val MONEDA_ARGENTINA = "Peso argentino"
    private val MONEDA_EEUU = "Dólar"
    private val MONEDA_ESPANIA = "Euro"
    private val MONEDA_JAPON = "Yen"
    private val MONEDA_BRASIL = "Real"
    private val MONEDA_REINO_UNIDO = "Libra"
    private val MONEDA_URUGUAY = "Peso uruguayo"
    private val MONEDA_CHILE = "Peso chileno"

    private val PAIS_ARGENTINA = "Argentina"
    private val PAIS_EEUU = "Estados Unidos"
    private val PAIS_ESPANIA = "España"
    private val PAIS_JAPON = "Japón"
    private val PAIS_BRASIL = "Brasil"
    private val PAIS_REINO_UNIDO = "Reino Unido"
    private val PAIS_URUGUAY = "Uruguay"
    private val PAIS_CHILE = "Chile"

    val divisasDisponibles = arrayOf(DIVISA_PESO_ARGENTINO, DIVISA_DOLAR_ESTADOUNIDENSE, DIVISA_EURO_ESPANIOL, DIVISA_YEN_JAPONES,
            DIVISA_REAL_BRASIL, DIVISA_LIBRA_INGLESA, DIVISA_PESO_URUGUAYO, DIVISA_PESO_CHILENO)

    val monedasDisponibles = arrayOf(MONEDA_ARGENTINA, MONEDA_EEUU, MONEDA_ESPANIA, MONEDA_JAPON, MONEDA_BRASIL, MONEDA_REINO_UNIDO, MONEDA_URUGUAY, MONEDA_CHILE)

    val divisaBaseDefault = DIVISA_PESO_ARGENTINO

    private var divisa: Divisa? = null

    fun create(codigo: String): Divisa? {
        when (codigo) {

            DIVISA_PESO_ARGENTINO -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_PESO_ARGENTINO,
                    PAIS_ARGENTINA,
                    this.nombreMonedaSegunPais(PAIS_ARGENTINA),
                    R.drawable.pais_ar,
                    R.string.PAIS_ARGENTINA,
                    R.string.MONEDA_ARGENTINA
            )

            DIVISA_DOLAR_ESTADOUNIDENSE -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_DOLAR_ESTADOUNIDENSE,
                    PAIS_EEUU,
                    this.nombreMonedaSegunPais(PAIS_EEUU),
                    R.drawable.pais_us,
                    R.string.PAIS_EEUU,
                    R.string.MONEDA_EEUU
            )

            DIVISA_EURO_ESPANIOL -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_EURO_ESPANIOL,
                    PAIS_ESPANIA,
                    this.nombreMonedaSegunPais(PAIS_ESPANIA),
                    R.drawable.pais_es,
                    R.string.PAIS_ESPANIA,
                    R.string.MONEDA_ESPANIA
            )

            DIVISA_YEN_JAPONES -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_YEN_JAPONES,
                    PAIS_JAPON,
                    this.nombreMonedaSegunPais(PAIS_JAPON),
                    R.drawable.pais_jp,
                    R.string.PAIS_JAPON,
                    R.string.MONEDA_JAPON
            )

            DIVISA_REAL_BRASIL -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_REAL_BRASIL,
                    PAIS_BRASIL,
                    this.nombreMonedaSegunPais(PAIS_BRASIL),
                    R.drawable.pais_br,
                    R.string.PAIS_BRASIL,
                    R.string.MONEDA_BRASIL
            )

            DIVISA_LIBRA_INGLESA -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_LIBRA_INGLESA,
                    PAIS_REINO_UNIDO,
                    this.nombreMonedaSegunPais(PAIS_REINO_UNIDO),
                    R.drawable.pais_uk,
                    R.string.PAIS_REINO_UNIDO,
                    R.string.MONEDA_REINO_UNIDO
            )

            DIVISA_PESO_URUGUAYO -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_PESO_URUGUAYO,
                    PAIS_URUGUAY,
                    this.nombreMonedaSegunPais(PAIS_URUGUAY),
                    R.drawable.pais_uy,
                    R.string.PAIS_URUGUAY,
                    R.string.MONEDA_URUGUAY
            )

            DIVISA_PESO_CHILENO -> this.divisa = this.crearDivisa(
                    FactoryDivisa.DIVISA_PESO_CHILENO,
                    PAIS_CHILE,
                    this.nombreMonedaSegunPais(PAIS_CHILE),
                    R.drawable.pais_ch,
                    R.string.PAIS_CHILE,
                    R.string.MONEDA_CHILE
            )

        }
        return this.divisa
    }

    private fun crearDivisa(codigo: String, pais: String, moneda: String, bandera: Int, paisResource : Int, monedaResource: Int): Divisa {
        val divisa = Divisa()
        divisa.codigo = codigo
        divisa.pais = pais
        divisa.moneda = moneda
        divisa.bandera = bandera
        divisa.valor = 1.0.toFloat()
        divisa.paisResource = paisResource
        divisa.monedaResource = monedaResource
        return divisa
    }

    fun codigoDivisaSegunPais(pais : String) : String{
        var codigo : String? = null
        when(pais){
            PAIS_ARGENTINA        -> codigo = DIVISA_PESO_ARGENTINO

            PAIS_EEUU   -> codigo = DIVISA_DOLAR_ESTADOUNIDENSE

            PAIS_ESPANIA            -> codigo = DIVISA_EURO_ESPANIOL

            PAIS_JAPON            -> codigo = DIVISA_YEN_JAPONES

            PAIS_BRASIL            -> codigo = DIVISA_REAL_BRASIL

            PAIS_REINO_UNIDO      -> codigo = DIVISA_LIBRA_INGLESA

            PAIS_URUGUAY            -> codigo = DIVISA_PESO_URUGUAYO

            PAIS_CHILE            -> codigo = DIVISA_PESO_CHILENO
        }
        return codigo!!
    }

    fun nombreMonedaSegunPais(pais : String) : String{
        var moneda: String? = null
        when(pais){
            PAIS_ARGENTINA      -> moneda = MONEDA_ARGENTINA

            PAIS_EEUU           -> moneda = MONEDA_EEUU

            PAIS_ESPANIA        -> moneda = MONEDA_ESPANIA

            PAIS_JAPON          -> moneda = MONEDA_JAPON

            PAIS_BRASIL         -> moneda = MONEDA_BRASIL

            PAIS_REINO_UNIDO    -> moneda = MONEDA_REINO_UNIDO

            PAIS_URUGUAY        -> moneda = MONEDA_URUGUAY

            PAIS_CHILE          -> moneda = MONEDA_CHILE
        }
        return moneda!!
    }

    fun nombreMonedaSegunCodigo(codigo : String) : String{
        var moneda: String? = null
        when(codigo){
            DIVISA_PESO_ARGENTINO -> moneda = MONEDA_ARGENTINA

            DIVISA_DOLAR_ESTADOUNIDENSE -> moneda = MONEDA_EEUU

            DIVISA_EURO_ESPANIOL -> moneda = MONEDA_ESPANIA

            DIVISA_YEN_JAPONES -> moneda = MONEDA_JAPON

            DIVISA_REAL_BRASIL -> moneda = MONEDA_BRASIL

            DIVISA_LIBRA_INGLESA -> moneda = MONEDA_REINO_UNIDO

            DIVISA_PESO_URUGUAYO -> moneda = MONEDA_URUGUAY

            DIVISA_PESO_CHILENO -> moneda = MONEDA_CHILE
        }
        return moneda!!
    }

    fun codigoSegunNombreMoneda(moneda : String) : String{
        var codigo : String? = null
        when(moneda){
            MONEDA_ARGENTINA -> codigo = DIVISA_PESO_ARGENTINO

            MONEDA_EEUU -> codigo = DIVISA_DOLAR_ESTADOUNIDENSE

            MONEDA_ESPANIA -> codigo = DIVISA_EURO_ESPANIOL

            MONEDA_JAPON -> codigo = DIVISA_YEN_JAPONES

            MONEDA_BRASIL -> codigo = DIVISA_REAL_BRASIL

            MONEDA_REINO_UNIDO -> codigo = DIVISA_LIBRA_INGLESA

            MONEDA_URUGUAY -> codigo = DIVISA_PESO_URUGUAYO

            MONEDA_CHILE -> codigo =  DIVISA_PESO_CHILENO
        }

        return codigo!!
    }
}