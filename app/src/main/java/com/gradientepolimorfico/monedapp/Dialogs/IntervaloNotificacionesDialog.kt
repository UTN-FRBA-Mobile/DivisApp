package com.gradientepolimorfico.monedapp.Dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.gradientepolimorfico.monedapp.Fragments.ConfigFragment
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import kotlinx.android.synthetic.main.intervalconfig.view.*

@Deprecated("No se usa más")
class IntervaloNotificacionesDialog : DialogFragment() {
    var  configFragment : ConfigFragment? = null

     public fun setRetornoFragment(configFragment: ConfigFragment){
        this.configFragment = configFragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var vista = inflater.inflate(R.layout.intervalconfig, container, false)

        this.recuperarOpcionSeleccionada(vista)
        this.agregarListeners(vista)

        return vista
    }

    private fun agregarListeners(vista : View){
        var rgIntervalo = vista.rgIntervalo

        vista.aceptar.setOnClickListener{
            v -> this.recuperarOpcion(rgIntervalo)
        }
    }

    private fun recuperarOpcionSeleccionada(vista : View){
        var radioGroup = vista.rgIntervalo
        var intervaloNotificaciones = Preferencias.getIntervaloNotificaciones(this.context!!)

        when(intervaloNotificaciones){

            "1" -> radioGroup.check(R.id.diarias)
            "2" -> radioGroup.check(R.id.semanales)
            "3" -> radioGroup.check(R.id.mensuales)

        }
    }

    private fun recuperarOpcion(radioGroup: RadioGroup){
        var opcionSeleccionada = radioGroup.checkedRadioButtonId

        when(opcionSeleccionada){

            R.id.diarias    -> Preferencias.setIntervaloNotificaciones(this.context!!,"1")
            R.id.semanales  -> Preferencias.setIntervaloNotificaciones(this.context!!,"2")
            R.id.mensuales  -> Preferencias.setIntervaloNotificaciones(this.context!!,"3")

        }
        this.dismiss()
    }

    /*override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        this.configFragment!!.actualizarOpciones()
    }*/
}