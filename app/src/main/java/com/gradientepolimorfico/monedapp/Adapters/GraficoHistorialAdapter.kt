package com.gradientepolimorfico.monedapp.Adapters;

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.github.mikephil.charting.data.Entry
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Fragments.GraficoHistorialFragment
import com.gradientepolimorfico.monedapp.R

class GraficoHistorialAdapter : FragmentPagerAdapter {

    private var context : Context? = null
    private var divisa = Divisa()
    private val graphSizes = arrayOf(7, 30, 100)
    private var selectedValue: Entry? = null
    private val items = HashMap<Int, Fragment>()

    constructor(fm : FragmentManager, context: Context) : super(fm){
        this.context = context
    }

    interface onSelectedValueListener {
        fun onSelectedValue(entry: Entry?)
    }

    override fun getItem(position: Int): Fragment {
        if (items[position] == null) {
            items[position] = GraficoHistorialFragment().also {
                it.agregarDivisa(divisa)
                it.agregarRango(graphSizes[position])
                it.agregarSelectedValue(selectedValue)
            }
        }
        return items[position]!!
    }

    fun agregarDivisa(divisa: Divisa) {
        this.divisa = divisa
    }

    fun setSelectedValue(entry: Entry?) {
        this.selectedValue = entry
        arrayOf(0, 1, 2).forEach { n -> (getItem(n) as onSelectedValueListener).onSelectedValue(entry) }
    }

    override fun getCount(): Int {
        return graphSizes.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return graphSizes[position].toString() + " "+(this.context as MainActivity).getString(R.string.dias)
    }
}