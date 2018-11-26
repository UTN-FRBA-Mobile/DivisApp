package com.gradientepolimorfico.monedapp.Fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class AddSaldoDialogFragment : DialogFragment() {

    //TODO: Ver por que no muestra en el EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.context!!)
        var inflater: LayoutInflater = activity!!.layoutInflater

        var vista = inflater.inflate(R.layout.dialog_saldoactual,null)
        vista.findViewById<EditText>(R.id.etSaldoActual).setText(Preferencias.monedero(this.context!!, Preferencias.getMonedaBase(this.context!!)!!).toString())
        builder .setTitle("Ingrese su saldo actual")
                .setView(vista)
                .setPositiveButton("Aceptar") { _ , _ ->
                    var valor = dialog.findViewById<EditText>(R.id.etSaldoActual).text.toString()
                    if (!valor.isNullOrEmpty()) {
                        Preferencias.saveMonedero(this.context!!, Preferencias.getMonedaBase(this.context!!)!!, valor.toFloat())
                        //    Log.e("ERROR",Preferencias.monedero(this.context!!, Preferencias.getMonedaBase(this.context!!)!!).toString())
                    }
                }
//                    (context as MainActivity).reloadDivisas()
//                    (context as MainActivity).irAPrincipal()
//

        return builder.create()
    }

}
