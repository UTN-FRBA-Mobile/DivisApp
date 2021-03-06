package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Adapters.DivisaAdapter
import com.gradientepolimorfico.monedapp.Entities.Usuario
import com.gradientepolimorfico.monedapp.R
import kotlinx.android.synthetic.main.fragment_divisas.*
import java.util.*

class DivisasFragment : Fragment() {
    private var fab: FloatingActionButton? = null

    private var usuario: Usuario? = null

    private var adapter: DivisaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.usuario = (context as MainActivity).usuario
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val vista = inflater.inflate(R.layout.fragment_divisas, container, false)
        adapter = DivisaAdapter((context as MainActivity).getDivisas(), context!!)

        this.inflarRecycler(vista, adapter!!)
        this.iniciarBottomNav(vista)
        this.iniciarMonedaBase(vista)

        return vista
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swiperefresh.setOnRefreshListener {
            adapter!!.divisas!!.forEach { divisa -> divisa.lastUpdated = Date(0) }
            adapter!!.notifyDataSetChanged()
            swiperefresh.isRefreshing = false
        }

    }

    private fun iniciarMonedaBase(vista: View) {
        val context = (this.context as MainActivity)
        vista.findViewById<TextView>(R.id.tvDivisaBase).text = context.getString(context.divisaBase!!.monedaResource!!)
        vista.findViewById<TextView>(R.id.tvPaisBase).text = context.getString(context.divisaBase!!.paisResource!!)
        vista.findViewById<ImageView>(R.id.iwBanderaBase).setImageResource(context.divisaBase!!.bandera!!)

        vista.findViewById<FrameLayout>(R.id.divisaFLBase).setOnClickListener {
            var args = Bundle()
            args.putInt("divisaIndex", -1)
            var fragment = DetalleFragment()
            fragment.arguments = args
            (context as MainActivity).getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    private fun inflarRecycler(vista: View, divisaAdapter: DivisaAdapter) {
        val recyclerView = vista.findViewById<RecyclerView>(R.id.rvDivisas)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        recyclerView!!.adapter = divisaAdapter
    }

    private fun iniciarBottomNav(vista: View) {
        var fab = vista.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        val addDivisaDialog = AddDivisaDialogFragment()
        fab.setOnClickListener(View.OnClickListener { addDivisaDialog.show(this.childFragmentManager!!, "Divisas") })
    }
}