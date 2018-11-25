package com.gradientepolimorfico.monedapp.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class LoginFragment : Fragment() {

    var btnFB: Button?          = null
    var btnMail: Button?        = null
    var btnInvitado: Button?    = null

    private fun inicializarBotones(vista: View){
        this.btnFB = vista.findViewById(R.id.btnFB)
        this.btnMail = vista.findViewById(R.id.btnMail)
        this.btnInvitado = vista.findViewById(R.id.btnGuest)

        this.btnFB?.setOnClickListener {
            Toast.makeText(activity, "FB LOGIN", Toast.LENGTH_SHORT).show()
        }
        this.btnMail?.setOnClickListener {
            Toast.makeText(activity, "MAIL LOGIN", Toast.LENGTH_SHORT).show()
        }
        this.btnInvitado?.setOnClickListener {
            Preferencias.setLogged(this.context!!,true)
            Preferencias.setUsername(this.context!!, "Stov")
            Preferencias.setLoginFrom(this.context!!,"Usuario invitado")
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var vista: View? = null
        if (Preferencias.isLogged(this.context!!)) {
            (this.context!! as MainActivity).irAPerfil()
        } else {
            vista = inflater.inflate(R.layout.fragment_loginoptions, container, false)
            inicializarBotones(vista)
        }
        return vista
    }


    private var listener: OnFragmentInteractionListener? = null

    //1
    override fun onAttach(context: Context) {
        super.onAttach(context)
/*        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    //2
    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val botonLogin = view.findViewById<Button>(R.id.botonLogin)
        botonLogin.setOnClickListener({ onLogin() })

        return view
    }
*/
    //3
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun onLogin() {
        val nombreDeUsuario = activity!!.findViewById<EditText>(R.id.nombreDeUsuario)
        val contrasenia = activity!!.findViewById<EditText>(R.id.contrasenia)

        listener?.onLogin(nombreDeUsuario.text.toString(), contrasenia.text.toString())
    }

    interface OnFragmentInteractionListener {
        fun onLogin(nombreDeUsuario: String, contrasenia: String)
    }
}