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
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class MailDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(this.context!!)
        var inflater: LayoutInflater = activity!!.layoutInflater

        var vista = inflater.inflate(R.layout.dialog_loginmail,null)
        builder .setTitle("Ingrese su dirección de correo electrónico")
                .setView(vista)
                .setPositiveButton("Aceptar") { _ , _ ->
                    Preferencias.setLogged(this.context!!, true)
                    Preferencias.setUsername(this.context!!, dialog.findViewById<EditText>(R.id.etMail).text.toString())
                    Preferencias.setLoginFrom(this.context!!, "Usuario registrado por Mail")
                    (context as MainActivity).irAPerfil()
                }

        return builder.create()
    }

}
