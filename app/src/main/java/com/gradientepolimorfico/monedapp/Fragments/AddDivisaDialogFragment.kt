package com.gradientepolimorfico.monedapp.Fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Factories.FactoryDivisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class AddDivisaDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var mSelectedItems = ArrayList<Integer>()

        val builder = AlertDialog.Builder(this.context!!)

        val divisas  = FactoryDivisa.divisasDisponibles

        var divsasActivas   = Preferencias.getMonedasSuscritas(this.context!!)
        var seleccionados = ArrayList<Boolean>()

        divisas.forEach { d -> if(divsasActivas!!.contains(d)) seleccionados.add(true) else seleccionados.add(false) }

        builder .setTitle("Divisas")

                .setMultiChoiceItems(divisas, seleccionados.toBooleanArray(),
                        {
                            dialog,which,isChecked ->
                            seleccionados[which] = isChecked
                        }
                )

                .setPositiveButton("Aceptar") { _, _ ->
                    for (i in 0 until divisas.size) {
                        val checked = seleccionados.toBooleanArray()[i]
                        if (checked) {
                            Preferencias.suscribirMoneda(this.context!!, divisas[i])
                        }
                        else{
                            Preferencias.desuscribirMoneda(this.context!!,divisas[i])
                        }
                    }
                    (context as MainActivity).reloadDivisas()
                    (context as MainActivity).irAPrincipal()
                }

        return builder.create()
    }

}
