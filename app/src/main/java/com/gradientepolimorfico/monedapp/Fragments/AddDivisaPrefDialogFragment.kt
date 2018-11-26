package com.gradientepolimorfico.monedapp.Fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.gradientepolimorfico.monedapp.Factories.FactoryDivisa
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class AddDivisaPrefDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.context!!)

        val divisas = FactoryDivisa.divisasDisponibles

        var divisaPreferida = Preferencias.getDivisaIntercambioPreferida(this.context!!)
        var seleccionados = ArrayList<Boolean>()

        divisas.forEach { d -> if (divisaPreferida == (d)) seleccionados.add(true) else seleccionados.add(false) }

        builder.setTitle("Seleccione su divisa de intercambio preferida")
                .setSingleChoiceItems(divisas, seleccionados.indexOf(true), { dialogInterface: DialogInterface, i: Int ->
                    if (seleccionados.indexOf(true) != -1) seleccionados[seleccionados.indexOf(true)] = false
                    seleccionados[i] = true
                }
                )
                .setPositiveButton("Aceptar") { _, _ ->
                    for (i in 0 until divisas.size) {
                        val checked = seleccionados.toBooleanArray()[i]
                        if (checked) {
                            Preferencias.setDivisaIntercambioPreferida(this.context!!, divisas[i])
                        }
                    }
                }

        return builder.create()
    }

}
