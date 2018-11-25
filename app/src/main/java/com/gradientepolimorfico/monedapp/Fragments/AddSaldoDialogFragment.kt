package com.gradientepolimorfico.monedapp.Fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Factories.FactoryDivisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import kotlinx.android.synthetic.main.fragment_divisa_precioactual.*
import org.w3c.dom.Text

class AddSaldoDialogFragment : DialogFragment() {

    //TODO: Ver por que no muestra en el EditText
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(Preferencias.existeMonedero(this.context!!,Preferencias.getMonedaBase(this.context!!)!!)){
            dialog.findViewById<EditText>(R.id.etSaldoActual).setText(Preferencias.monedero(this.context!!, Preferencias.getMonedaBase(this.context!!)!!).toString())
        }
    }


   override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.context!!)
        var inflater: LayoutInflater = activity!!.layoutInflater

        builder .setTitle("Ingrese su saldo actual")
                .setView(inflater.inflate(R.layout.dialog_saldoactual,null))
                .setPositiveButton("Aceptar") { _ , _ ->
                    var valor = dialog.findViewById<EditText>(R.id.etSaldoActual).text.toString()
                    if (!valor.isNullOrEmpty()) {
                        Preferencias.saveMonedero(this.context!!,Preferencias.getMonedaBase(this.context!!)!!,valor.toFloat())
                    //    Log.e("ERROR",Preferencias.monedero(this.context!!, Preferencias.getMonedaBase(this.context!!)!!).toString())
                    }
                }
//                    (context as MainActivity).reloadDivisas()
//                    (context as MainActivity).irAPrincipal()
//

        return builder.create()
    }

}
