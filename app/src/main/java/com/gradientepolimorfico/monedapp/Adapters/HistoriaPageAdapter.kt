package com.gradientepolimorfico.monedapp.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Fragments.HistoriaFragment

class HistoriaPageAdapter : FragmentPagerAdapter {
    private var divisas = ArrayList<Divisa>()
    private val titles = ArrayList<String>()

    constructor(fm: FragmentManager?) : super(fm) {

    }

    override fun getItem(position: Int): Fragment {
        return HistoriaFragment().also { it.agregarDivisa(divisas[position]) }
    }

    override fun getCount(): Int {
        return this.divisas.size
    }

    override fun getItemId(position: Int): Long {
        return divisas[position].bandera?.toLong() ?: super.getItemId(position)
    }

    fun agregarDivisas(fragments: ArrayList<Divisa>) {
        this.divisas.clear()
        this.divisas = fragments
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    fun removeDivisa(position: Int) {
        this.divisas.removeAt(position)
    }

    fun addDivisa(divisa: Divisa) {
        this.divisas.add(divisa)
    }
}