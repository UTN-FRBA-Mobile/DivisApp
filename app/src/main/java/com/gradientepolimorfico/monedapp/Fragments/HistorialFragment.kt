package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Adapters.DivisaAdapter
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.configureForHistory


class HistorialFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var vista = inflater.inflate(R.layout.pager_historial, container, false)
        var mainActivity = context as MainActivity
        mainActivity.iniciarFragmentsPagers(vista.findViewById(R.id.viewpagerHistorial),childFragmentManager)
        return vista
    }
}
