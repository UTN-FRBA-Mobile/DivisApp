package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Adapters.DivisaAdapter
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Entities.Usuario
import com.gradientepolimorfico.monedapp.R

class DivisasFragment : Fragment(){
    private var divisas:ArrayList<Divisa>? = null
    private var divisaAdapter:DivisaAdapter? = null
    private var recyclerView:RecyclerView? = null
    private var viewManager:RecyclerView.LayoutManager? = null
    private var fab: FloatingActionButton?=null

    private var usuario: Usuario? = null

    private fun inicializar(){
        this.divisas = this.usuario!!.configuracion!!.divisas
        this.viewManager    = LinearLayoutManager(context)
        this.divisaAdapter  = DivisaAdapter(this.divisas!!,context!!)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.usuario = (context as MainActivity).usuario
        this.divisas = ArrayList<Divisa>()
        this.inicializar()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var vista= inflater.inflate(R.layout.fragment_divisas, container, false)

        this.iniciarMonedaBase(vista)
        this.inflarRecycler(vista)
        this.iniciarBottomNav(vista)

        return vista
    }

    private fun iniciarMonedaBase(vista: View){
        vista.findViewById<TextView>(R.id.tvDivisaBase).text    = (this.context as MainActivity).divisaBase!!.moneda
        vista.findViewById<TextView>(R.id.tvPaisBase).text      = (this.context as MainActivity).divisaBase!!.pais
        //vista.findViewById<TextView>(R.id.tvValorDivisaBase).text = (this.context as MainActivity).divisaBase!!.valor.toString()
        vista.findViewById<TextView>(R.id.tvCotizaciones).text = ("COTIZACIONES (1 " + (this.context as MainActivity).divisaBase!!.moneda!!.toUpperCase() + ")")
        vista.findViewById<ImageView>(R.id.iwBanderaBase).setImageResource((this.context as MainActivity).divisaBase!!.bandera!!)
    }

    private fun inflarRecycler(vista: View){
        this.recyclerView = vista.findViewById(R.id.rvDivisas)
        this.fab = vista.findViewById(R.id.floatingActionButton)
        this.recyclerView!!.layoutManager = this.viewManager
        this.recyclerView!!.adapter = this.divisaAdapter
    }

    private fun iniciarBottomNav(vista : View){
        //TODO: Agregar compartamiento al button. Agregar un Dialog de agregar divisa.
        //TODO: Agregar options al spinner del fragement de agregar divisa.
    }
}