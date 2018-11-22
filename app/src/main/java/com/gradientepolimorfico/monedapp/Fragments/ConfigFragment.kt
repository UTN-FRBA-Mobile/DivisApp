package com.gradientepolimorfico.monedapp.Fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class ConfigFragment : PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener{

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key){
            Preferencias.NOTIFICACIONES_ACTIVAS -> this.subscribirANotificaciones(sharedPreferences!!.getBoolean(Preferencias.NOTIFICACIONES_ACTIVAS,true))
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
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
}