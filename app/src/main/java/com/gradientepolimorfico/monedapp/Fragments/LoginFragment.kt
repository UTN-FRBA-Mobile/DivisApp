package com.gradientepolimorfico.monedapp.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.gradientepolimorfico.monedapp.R

class LoginFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    //1
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    //2
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val botonLogin = view.findViewById<Button>(R.id.botonLogin)
        botonLogin.setOnClickListener({ onLogin() })

        return view
    }

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