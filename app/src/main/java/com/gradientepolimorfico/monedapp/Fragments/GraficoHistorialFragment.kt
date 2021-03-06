package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.gradientepolimorfico.monedapp.Adapters.GraficoHistorialAdapter
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.configureForHistory


class GraficoHistorialFragment : Fragment(), GraficoHistorialAdapter.onSelectedValueListener {

    var divisa: Divisa? = null
    var rangoGrafico: Int = 7
    var selectedValue: Entry? = null

    fun agregarDivisa(divisa: Divisa) {
        this.divisa = divisa
    }

    fun agregarRango(rango: Int) {
        this.rangoGrafico = rango
    }

    fun agregarSelectedValue(entry: Entry?) {
        this.selectedValue = entry
    }

    // Container Activity or Fragment must implement this interface
    interface OnChartValueSelectedListener {
        fun onValueSelected(entry: Entry?)
    }

    fun handleSelection(mChart: LineChart){
        if(selectedValue != null) {
            mChart.highlightValue(selectedValue!!.x, 0, false)
        } else {
            mChart.highlightValue(0f, -1, false)
        }
        mChart.invalidate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_grafico_historial, container, false)

        val mOnChartValueSelectedListener = parentFragment as OnChartValueSelectedListener

        val mChart: LineChart = view.findViewById(R.id.priceHistoricGraph)
        mChart.configureForHistory(activity!!.applicationContext, this.divisa!!.timeSeriesData, rangoGrafico)
        handleSelection(mChart)
        mChart.setOnChartValueSelectedListener(object : com.github.mikephil.charting.listener.OnChartValueSelectedListener {
            override fun onValueSelected(entry: Entry, h: Highlight) {
                mOnChartValueSelectedListener.onValueSelected(entry)
                selectedValue = entry
            }

            override fun onNothingSelected() {
                mOnChartValueSelectedListener.onValueSelected(null)
                selectedValue = null
            }
        })
        return view
    }

    override fun onSelectedValue(entry: Entry?) {
        selectedValue = entry
        if(view != null) {
            handleSelection(view!!.findViewById(R.id.priceHistoricGraph)) }
    }
}