package com.gradientepolimorfico.monedapp.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias

class PerfilFragment : Fragment() {

    var btnSaldoActual: TextView? = null
    var btnCargarDivisaIntercambio: TextView? = null
    var btnEvolucion: TextView? = null
    var btnInvitarAmigos: TextView? = null
    var btnCerrarSesion: TextView? = null


    private fun inicializarStrings(vista:View){
        vista.findViewById<TextView>(R.id.tvNombreUsuario).text = (Preferencias.getUsername(this.context!!))
        vista.findViewById<TextView>(R.id.tvModoIngreso).text =  (Preferencias.getLoginFrom(this.context!!))
    }

    private fun inicializarBotones(vista: View){
        this.btnSaldoActual = vista.findViewById(R.id.btnSaldoActual)
        this.btnCargarDivisaIntercambio = vista.findViewById(R.id.btnCargarDivisaIntercambio)
        this.btnEvolucion = vista.findViewById(R.id.btnEvolucion)
        this.btnInvitarAmigos = vista.findViewById(R.id.btnInvitarAmigos)
        this.btnCerrarSesion = vista.findViewById(R.id.btnCerrarSesion)

        val addSaldoDialog = AddSaldoDialogFragment()
        this.btnSaldoActual?.setOnClickListener(View.OnClickListener { addSaldoDialog.show(this.childFragmentManager!!,"Divisas") })

        val addFavoriteDialog = AddDivisaPrefDialogFragment()
        this.btnCargarDivisaIntercambio?.setOnClickListener(View.OnClickListener { addFavoriteDialog.show(this.childFragmentManager!!,"Divisas") })

        this.btnEvolucion?.setOnClickListener {

        }

        this.btnInvitarAmigos?.setOnClickListener {
            var myIntent = Intent(Intent.ACTION_SEND)
            myIntent.setType("text/plain")
            var shareBody = "Obtené informacion y alertas de distintas divisas, en tiempo real!.Descargá DivisAPP ahora mismo en divisapp.com/download"
            var shareSub = "Usá DivisAPP"
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub)
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(myIntent,"Compartir esto en..."))
        }

        this.btnCerrarSesion?.setOnClickListener {
            Preferencias.setLogged(this.context!!,false)
            Preferencias.setTokenFacebook(this.context!!,"")
            (this.context!! as MainActivity).irALoginOptions()
            (this.context!! as MainActivity).desloguearFacebook()
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
        inicializarStrings(vista)
        return vista
    }

}
