package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging
import com.gradientepolimorfico.monedapp.Dialogs.IntervaloNotificacionesDialog
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import kotlinx.android.synthetic.main.fragment_config.view.*

class ConfigFragment : PreferenceFragmentCompat(){
    //var vista : View = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_general)

        this.subscribirANotificaciones(this.view)
    }

    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var vista = inflater.inflate(R.layout.fragment_config, container, false)

        this.initOpciones(vista)
        this.agregarListeners(vista)
        this.vista = vista
        return vista
    }

    private fun agregarListeners(vista: View){

        vista.lyIntervalo.setOnClickListener{
            v -> dialogoIntervalo()
        }
        
        vista.switchNotificaciones.setOnCheckedChangeListener {
            buttonView, isChecked -> Preferencias.setNotificacionesActivas(this.context!!,isChecked)
        }
    }

    private fun dialogoIntervalo(){
        val intervaloNotificacionesDialog = IntervaloNotificacionesDialog()
        intervaloNotificacionesDialog.setRetornoFragment(this)
        intervaloNotificacionesDialog.show(this.fragmentManager,"Intervalo de notificaciones")
    }

    private fun initOpciones(vista: View){
        //var textoIntervalo  = vista.findViewById<TextView>(R.id.tvDescripcion2)
        var intervaloNotificaciones = Preferencias.getIntervaloNotificaciones(this.context!!)

        when(intervaloNotificaciones){
            1 -> textoIntervalo.text = "Diaramente"
            2 -> textoIntervalo.text = "Semanalmente"
            3 -> textoIntervalo.text = "Mensualmente"
        }
        this.subscribirANotificaciones(vista)
    }

    public fun actualizarOpciones(){
        this.initOpciones(this.vista!!)
    }*/

    private fun subscribirANotificaciones(vista: View?){
        //vista!!.switchNotificaciones.isChecked = Preferencias.getNotificacionesActivas(this.context!!)
        if(Preferencias.getNotificacionesActivas(this.context!!)){
            FirebaseMessaging.getInstance().subscribeToTopic("notificaciones")
        }
        else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic("notificaciones")
        }
    }
}