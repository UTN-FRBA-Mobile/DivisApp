package com.gradientepolimorfico.monedapp.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gradientepolimorfico.monedapp.Fragments.HistoriaFragment

class HistoriaPageAdapter : FragmentPagerAdapter {
    private var fragments = ArrayList<Fragment>()
    private val titles = ArrayList<String>()

    constructor(fm: FragmentManager?) : super(fm){

    }

    override fun getItem(position: Int): Fragment {
        return this.fragments[position]
    }

    override fun getCount(): Int {
        return this.fragments.size
    }

    override fun getItemId(position: Int): Long {
        return (fragments[position] as HistoriaFragment?)?.divisa?.bandera?.toLong() ?: super.getItemId(position)
    }

    fun agregarFragments(fragments : ArrayList<Fragment>){
        this.fragments.clear()
        this.fragments = fragments
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    fun removeFragment(position: Int){
        this.fragments.remove(this.getItem(position))
    }

    fun addFragment(fragment: Fragment){
        this.fragments.add(fragment)
    }
}