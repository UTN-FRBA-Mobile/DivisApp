package com.gradientepolimorfico.monedapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R

class MonedaAdapter: BaseAdapter {
    var listaDeMonedas = ArrayList<Divisa>()
    var context: Context?=null

    constructor(context:Context, listaDeMonedas: ArrayList<Divisa>):super(){
        this.listaDeMonedas = listaDeMonedas;
        this.context = context
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val animal = listaDeMonedas[p0]
        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var myView = inflator.inflate(R.layout.fragment_divisa_precioactual,null)
        return myView
    }

    override fun getItem(p0: Int): Any {
        return listaDeMonedas[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return this.listaDeMonedas.size
    }
}