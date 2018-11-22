package com.gradientepolimorfico.monedapp.Fragments

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.configureForHistory

class HistoriaFragment : Fragment() {
    var divisa : Divisa? = null

    public fun agregarDivisa(divisa : Divisa){
        this.divisa = divisa
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var vista = inflater.inflate(R.layout.fragment_historia, container, false)

        this.init(vista)

        if(this.divisa!!.hayDatos()){
            val mChart : LineChart = vista.findViewById<LineChart>(R.id.priceHistoricGraph)
            mChart.configureForHistory(activity!!.applicationContext, this.divisa!!.timeSeriesData)
        }

        return vista
    }

    private fun divisaBase() : Divisa?{
        val mainActivity = this.context!! as MainActivity
        return mainActivity.divisaBase
    }

    private fun valorMonedaSegunBase() : String{
        return this.divisa!!.valor.toString()+" "+ this.divisaBase()!!.codigo
    }

    private fun init(vista : View){
        if(this.divisa!!.hayDatos()){

            var ultimoCambio = this.divisa!!.ultimoCambio()
            var ultimoCambioEnPorcentaje = this.divisa!!.ultimoCambioEnPorcentaje()

            /**----------------------------- HEADERS ---------------------------------- **/
            vista.findViewById<TextView>(R.id.tvPais).text      = this.divisa!!.pais
            vista.findViewById<TextView>(R.id.tvDivisa).text    = this.divisa!!.moneda
            vista.findViewById<ImageView>(R.id.iwBandera).setImageResource(this.divisa!!.bandera!!)
            vista.findViewById<TextView>(R.id.tvValorDivisa).text = this.valorMonedaSegunBase()

            vista.findViewById<TextView>(R.id.tvAmbosCambios).text =
                    ("%.3f".format(ultimoCambio) + " (" + "%.3f".format(ultimoCambioEnPorcentaje) + "%)")

            vista.findViewById<TextView>(R.id.tvCambio).text = ("$"+"%.4f".format(ultimoCambio))
            /**----------------------------- END HEADERS ------------------------------- **/

            var resource = 1
            var moneda = this.divisa!!.moneda!!.toLowerCase()
            var detalle = ""
            var colorDetalle = 1

            if(ultimoCambio<0){
                resource = R.drawable.ic_down_palito_rojo
                detalle  = "(%.4f".format(ultimoCambioEnPorcentaje) + "%)"
                colorDetalle = R.color.colorTextNegative
            }
            else{
                resource = R.drawable.ic_up_palito_verde
                detalle = "(+"+ultimoCambioEnPorcentaje.toString()+"%)"
                colorDetalle = R.color.colorTextPositive
            }

            /**----------------------------- DETALLES ---------------------------------- **/
            vista.findViewById<ImageView>(R.id.ivSubaBaja).setImageResource(resource)

            var tvDetalle = vista.findViewById<TextView>(R.id.tvDetalleSubaBaja)
            tvDetalle.setTextColor(resources.getColor(colorDetalle))

            vista.findViewById<TextView>(R.id.tvDetalleSubaBaja).text = detalle
            vista.findViewById<TextView>(R.id.tvCambio).setTextColor(resources.getColor(colorDetalle))

            vista.findViewById<TextView>(R.id.tvValorMonedaBase).text = "1 "+ this.divisaBase()!!.codigo
            vista.findViewById<TextView>(R.id.tvValorDivisaSegunBase).text = "%.3f".format((1.toFloat()) / this.divisa!!.valor!!).toString() + " "+this.divisa!!.codigo


            vista.findViewById<TextView>(R.id.tvValorBaseSegunDivisa).text = valorMonedaSegunBase()
            vista.findViewById<TextView>(R.id.tvUnidadDivisa).text = "1 "+ this.divisa!!.codigo

            /**----------------------------- END DETALLES ---------------------------------- **/
        }
    }
}