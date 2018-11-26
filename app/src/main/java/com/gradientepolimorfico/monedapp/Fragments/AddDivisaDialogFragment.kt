package com.gradientepolimorfico.monedapp.Fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Factories.FactoryDivisa
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import com.gradientepolimorfico.monedapp.R

class AddDivisaDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(this.context!!)
        val divisas = FactoryDivisa.monedasDisponibles

        var divsasActivas = Preferencias.getMonedasSuscritas(this.context!!)
        var seleccionados = ArrayList<Boolean>()

        divisas.forEach { d -> if (divsasActivas!!.contains(FactoryDivisa.codigoSegunNombreMoneda(d))) seleccionados.add(true) else seleccionados.add(false) }

        builder.setTitle(R.string.divisas)

                .setMultiChoiceItems(divisas, seleccionados.toBooleanArray(),
                        { dialog, which, isChecked ->
                            seleccionados[which] = isChecked
                        }
                )

                .setPositiveButton(getString(R.string.aceptar)) { _, _ ->
                    for (i in 0 until divisas.size) {
                        val checked = seleccionados.toBooleanArray()[i]
                        if (checked) {
                            Preferencias.suscribirMoneda(this.context!!, FactoryDivisa.codigoSegunNombreMoneda(divisas[i]))
                        } else {
                            Preferencias.desuscribirMoneda(this.context!!, FactoryDivisa.codigoSegunNombreMoneda(divisas[i]))
                        }
                    }
                    (context as MainActivity).reloadDivisas()
                    (context as MainActivity).irAPrincipal()
                }

                .setNegativeButton(getString(R.string.cancelar)){dialog,_ ->
                    dialog.dismiss()
                }

        return builder.create()
    }

}
