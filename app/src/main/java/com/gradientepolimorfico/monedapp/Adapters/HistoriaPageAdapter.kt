package com.gradientepolimorfico.monedapp.Adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Fragments.HistoriaFragment

class HistoriaPageAdapter : FragmentStatePagerAdapter {
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

    fun addFragment(fragment: Fragment, title: String) {
        this.fragments.add(fragment)
        this.titles.add(title)
    }

    fun agregarFragments(fragments : ArrayList<Fragment>){
        this.fragments = fragments
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }
}