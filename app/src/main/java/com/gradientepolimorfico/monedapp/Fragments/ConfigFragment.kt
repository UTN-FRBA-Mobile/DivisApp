package com.gradientepolimorfico.monedapp.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class ConfigFragment : PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener{

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key){
            Preferencias.NOTIFICACIONES_ACTIVAS -> this.subscribirANotificaciones(sharedPreferences!!.getBoolean(Preferencias.NOTIFICACIONES_ACTIVAS,true))

            Preferencias.MONEDA_BASE -> this.cambiarMonedaBase(sharedPreferences!!.getString(Preferencias.MONEDA_BASE,"ARS")!!)

            Preferencias.DIVISA_INTERCAMBIO_PREF -> this.cambiarDivisaPref(sharedPreferences!!.getString(Preferencias.DIVISA_INTERCAMBIO_PREF,"USD")!!)

            Preferencias.INTERVALO_NOTIFICACIONES -> Preferencias.setIntervaloNotificaciones(this.context!!,sharedPreferences!!.getString(Preferencias.INTERVALO_NOTIFICACIONES,"1")!!)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = "DIVIS_APP_PREFERENCES"
        addPreferencesFromResource(R.xml.pref_general)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun subscribirANotificaciones(valor : Boolean){
        Preferencias.setNotificacionesActivas(this.context!!,valor)

        if(Preferencias.getNotificacionesActivas(this.context!!)){
            FirebaseMessaging.getInstance().subscribeToTopic("notificaciones")
        }
        else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic("notificaciones")
        }

    }

    private fun cambiarMonedaBase(moneda : String){
        Preferencias.setMonedaBase(this.context!!, moneda)
        (context as MainActivity).cambiarMonedaBase()
    }

    private fun cambiarDivisaPref(moneda : String){
        Preferencias.setDivisaIntercambioPreferida(this.context!!, moneda)
        (context as MainActivity).cambiarDivisaPreferida()
    }
}