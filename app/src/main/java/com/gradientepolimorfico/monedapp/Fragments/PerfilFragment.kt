package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.R

class PerfilFragment : Fragment() {

    var btnSaldoActual: TextView? = null
    var btnCargarDivisaIntercambio: TextView? = null
    var btnEvolucion: TextView? = null
    var btnInvitarAmigos: TextView? = null
    var btnCerrarSesion: TextView? = null


    private fun inicializarBotones(vista: View){
        this.btnSaldoActual = vista.findViewById(R.id.btnSaldoActual)
        this.btnCargarDivisaIntercambio = vista.findViewById(R.id.btnCargarDivisaIntercambio)
        this.btnEvolucion = vista.findViewById(R.id.btnEvolucion)
        this.btnInvitarAmigos = vista.findViewById(R.id.btnInvitarAmigos)
        this.btnCerrarSesion = vista.findViewById(R.id.btnCerrarSesion)

        val addSaldoDialog = AddSaldoDialogFragment()
        this.btnSaldoActual?.setOnClickListener(View.OnClickListener { addSaldoDialog.show(this.childFragmentManager!!,"Divisas") })

        this.btnCargarDivisaIntercambio?.setOnClickListener {
            Toast.makeText(activity, "CARGAR DIVISA INTERCAMBIO", Toast.LENGTH_SHORT).show()
        }

        this.btnEvolucion?.setOnClickListener {
            Toast.makeText(activity, "VER EVOLUCION", Toast.LENGTH_SHORT).show()
        }

        this.btnInvitarAmigos?.setOnClickListener {
            Toast.makeText(activity, "INVITAR AMIGOS", Toast.LENGTH_SHORT).show()
        }

        this.btnCerrarSesion?.setOnClickListener {
            Toast.makeText(activity, "CERRAR SESION", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var vista = inflater.inflate(R.layout.fragment_profile, container, false)
        var mainActivity = context as MainActivity
        inicializarBotones(vista)
        return vista
    }


}
