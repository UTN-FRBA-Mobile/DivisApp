package com.gradientepolimorfico.monedapp.Storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.github.mikephil.charting.data.Entry
import com.gradientepolimorfico.monedapp.Entities.Divisa
import java.util.*
import java.util.Arrays.asList
import kotlin.collections.ArrayList


object Preferencias {
    val PREF_NAME                   = "DIVIS_APP_PREFERENCES"
    val FIREBASE_TOKEN              = "FIREBASE_TOKEN"
    val INTERVALO_NOTIFICACIONES    = "INTERVALO_NOTIFICACIONES"
    val MONEDAS_SUSCRITAS           = "MONEDAS_SUSCRITAS"
    val NOTIFICACIONES_ACTIVAS      = "NOTIFICACIONES_ACTIVAS"
    val MONEDA_BASE                 = "MONEDA_BASE"

    private val NOTIFICACIONES_POR_DIVISA = "NOTIFICACIONES_"

    private val DIVISA_PARTICULAR               = "INFO_DIVISA_"
    private val DIVISA_PARTICULAR_VALOR         = "_VALOR"
    private val DIVISA_PARTICULAR_TIMES_SERIES  = "_TIMES_SERIES"
    private val DIVISA_PARTICULAR_FROM          = "_FROM"

    fun getMonedaBase(context: Context) : String?{
        return this.getPreferences(context).getString(MONEDA_BASE,null)
    }

    fun setMonedaBase(context: Context, monedaBase : String){
        val editor = this.getPreferencesEditor(context)
        editor.putString(MONEDA_BASE, monedaBase)
        editor.apply()
    }

    fun getNotificacionesActivas(context: Context) : Boolean{
        return this.getPreferences(context).getBoolean(NOTIFICACIONES_ACTIVAS,true)
    }

    fun notificacionesEstanActivas(context: Context) : Boolean{
        return this.getNotificacionesActivas(context)
    }

    fun setNotificacionesActivas(context: Context, notificaciones : Boolean){
        val editor = this.getPreferencesEditor(context)
        editor.putBoolean(NOTIFICACIONES_ACTIVAS,notificaciones)
        editor.apply()
    }

    fun notificacionesParaSubaDivisa(context: Context, divisa: String, notificaciones: Boolean){
        val editor = this.getPreferencesEditor(context)
        editor.putBoolean(NOTIFICACIONES_POR_DIVISA+"SUBA_"+divisa, notificaciones)
        editor.apply()
    }

    fun notificacionesParaBajaDivisa(context: Context, divisa: String, notificaciones: Boolean){
        val editor = this.getPreferencesEditor(context)
        editor.putBoolean(NOTIFICACIONES_POR_DIVISA+"BAJA_"+divisa, notificaciones)
        editor.apply()
    }

    fun getNotificacionesParaSubaDivisa(context: Context, divisa: String) : Boolean{
        return getPreferences(context).getBoolean(NOTIFICACIONES_POR_DIVISA+"SUBA_"+divisa,true)
    }

    fun getNotificacionesParaBajaDivisa(context: Context, divisa: String) : Boolean{
        return getPreferences(context).getBoolean(NOTIFICACIONES_POR_DIVISA+"BAJA_"+divisa,true)
    }

    fun getFirebaseToken(context: Context): String? {
        return getPreferences(context).getString(FIREBASE_TOKEN, null)
    }

    fun getMonedasSuscritas(context: Context): Set<String>?{
        return this.getPreferences(context).getStringSet(MONEDAS_SUSCRITAS,null)
    }

    fun setFirebaseToken(context: Context, token: String) {
        val editor = this.getPreferencesEditor(context)
        editor.putString(FIREBASE_TOKEN, token)
        editor.apply()
    }

    fun setIntervaloNotificaciones(context: Context, intervalo: String){
        val editor = this.getPreferencesEditor(context)
        editor.putString(INTERVALO_NOTIFICACIONES, intervalo)
        editor.apply()
    }

    fun getIntervaloNotificaciones(context: Context): String{
        return this.getPreferences(context).getString(INTERVALO_NOTIFICACIONES,"1")!!
    }

    fun existeMonedero(context: Context, divisa:String) : Boolean{
        return this.getPreferences(context).contains(divisa)
    }

    fun monedero(context: Context, divisa: String) : Float{
        return this.getPreferences(context).getFloat(divisa,(0.0).toFloat())
    }

    fun saveMonedero(context: Context, divisa: String, valor : Float){
        val editor = this.getPreferencesEditor(context)
        editor.putFloat(divisa, valor)
        editor.apply()
    }

    fun suscribirMoneda(context: Context, moneda : String){
        val monedasActuales = this.getMonedasSuscritas(context)
        val monedas = ArrayList<String>()
        if(monedasActuales != null){
            monedasActuales.forEach { m -> monedas.add(m) }
        }
        monedas.add(moneda)

        val editor = this.getPreferencesEditor(context)
        editor.putStringSet(MONEDAS_SUSCRITAS, monedas.toSet())
        editor.apply()
    }

    fun desuscribirMoneda(context: Context, moneda: String){
        val monedasActuales = this.getMonedasSuscritas(context)
        val monedas = ArrayList<String>()

        if(monedasActuales != null){
            monedasActuales.forEach { m -> kotlin.run{
                if(m!=moneda){
                    monedas.add(m)
                }
            }

            }
        }
        val editor = this.getPreferencesEditor(context)
        editor.remove(MONEDAS_SUSCRITAS)
        editor.apply()
        editor.putStringSet(MONEDAS_SUSCRITAS, monedas.toSet())
        editor.apply()
    }

    fun saveDivisa(context: Context, divisa: Divisa){
        val editor = this.getPreferencesEditor(context)
        val codigo = divisa.codigo!!

        editor.remove(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_TIMES_SERIES)
        editor.putString(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_TIMES_SERIES, divisa.timesSeriesDataToString())
        editor.apply()

        editor.remove(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_VALOR)
        editor.putFloat(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_VALOR, divisa.valor!!)
        editor.apply()

        editor.remove(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_FROM)
        editor.putString(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_FROM,divisa.from!!)
        editor.apply()
    }

    fun recuperarDivisa(context: Context, divisa: Divisa){
        val codigo = divisa.codigo!!
        val preferences = this.getPreferences(context)

        divisa.valor = preferences.getFloat(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_VALOR, (0.0).toFloat())
        divisa.from  = preferences.getString(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_FROM,"ARS")
        var stringEntries = preferences.getString(DIVISA_PARTICULAR+codigo+ DIVISA_PARTICULAR_TIMES_SERIES,null)

        if(stringEntries!= null){
            var entries: ArrayList<String> = ArrayList(stringEntries.split(";"))
            entries.forEach {
                e ->
                var spliteado = e.split("x:","y:")
                divisa.timeSeriesData.add(Entry(spliteado[1].toFloat(),spliteado[2].toFloat()))
            }
        }
    }

    private fun getPreferencesEditor(context: Context): SharedPreferences.Editor {
        return getPreferences(context).edit()
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}