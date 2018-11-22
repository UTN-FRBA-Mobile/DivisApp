package com.gradientepolimorfico.monedapp.Storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object Preferencias {
    val PREF_NAME                   = "DIVIS_APP_PREFERENCES"
    val FIREBASE_TOKEN              = "FIREBASE_TOKEN"
    val INTERVALO_NOTIFICACIONES    = "INTERVALO_NOTIFICACIONES"
    val MONEDAS_SUSCRITAS           = "MONEDAS_SUSCRITAS"
    val NOTIFICACIONES_ACTIVAS      = "NOTIFICACIONES_ACTIVAS"
    val MONEDA_BASE                 = "MONEDA_BASE"

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

    fun setIntervaloNotificaciones(context: Context, intervalo: Int){
        val editor = this.getPreferencesEditor(context)
        editor.putInt(INTERVALO_NOTIFICACIONES, intervalo)
        editor.apply()
    }

    fun getIntervaloNotificaciones(context: Context): Int{
        return this.getPreferences(context).getInt(INTERVALO_NOTIFICACIONES,1)
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

    private fun getPreferencesEditor(context: Context): SharedPreferences.Editor {
        return getPreferences(context).edit()
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}