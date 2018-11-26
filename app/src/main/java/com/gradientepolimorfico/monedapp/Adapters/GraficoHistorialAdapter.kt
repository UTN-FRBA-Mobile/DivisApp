package com.gradientepolimorfico.monedapp.Adapters;

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Fragments.GraficoHistorialFragment

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var divisa = Divisa()
    private val graphSizes = arrayOf(7, 30, 100)

    override fun getItem(position: Int): Fragment {
        return GraficoHistorialFragment().also {
            it.agregarDivisa(divisa)
            it.agregarRango(graphSizes[position])
        }
    }

    fun agregarDivisa(divisa: Divisa) {
        this.divisa = divisa
    }

    override fun getCount(): Int {
        return graphSizes.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return graphSizes[position].toString() + " Días"
    }
}