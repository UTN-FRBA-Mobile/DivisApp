package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.github.mikephil.charting.data.Entry
import com.google.firebase.messaging.FirebaseMessaging
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Adapters.GraficoHistorialAdapter
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import java.text.SimpleDateFormat
import java.util.*

class HistoriaFragment : Fragment(), GraficoHistorialFragment.OnChartValueSelectedListener {
    var divisa: Divisa? = null
    var vista: View? = null

    public fun agregarDivisa(divisa: Divisa) {
        this.divisa = divisa
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun setDetallesSegunFecha(valor: Float, fechaEnDias: Float) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        var base = this.divisaBase()
        var stringFecha = dateFormat.format(Date(fechaEnDias.toLong() * (60000 * 60 * 24)))

        vista!!.findViewById<TextView>(R.id.tvValorDivisaSegunBase).text = "%.3f".format((1.toFloat()) / valor) + " " + this.divisa!!.codigo
        vista!!.findViewById<TextView>(R.id.tvValorBaseSegunDivisa).text = valorMonedaSegunBase(valor)
        vista!!.findViewById<TextView>(R.id.tvFecha).text = stringFecha

        /**----------------------------- CONVERTIBILIDAD -------------------------------- **/
        var monederoDivisa = Preferencias.monedero(this.context!!, this.divisa!!.codigo!!)
        var monederoBase = Preferencias.monedero(this.context!!, base!!.codigo!!)

        var ventaDivisa = monederoDivisa * (this.divisa!!.valor!!)
        var compraDivisa = monederoBase / (this.divisa!!.valor!!)

        if(fechaEnDias != divisa!!.timeSeriesData.last().y){
            vista!!.findViewById<LinearLayout>(R.id.layoutDevaluacion).visibility = View.VISIBLE
            vista!!.findViewById<TextView>(R.id.historiaTvDevaluacion).text = "Tu saldo actual de " + monederoBase + " " + base!!.codigo!! + " equivale a haber tenido " + monederoBase * (divisa!!.timeSeriesData.last().x / valor) + " " + base!!.codigo!! + " el " + stringFecha+ " " + this.divisa!!.codigo
        } else {
            vista!!.findViewById<LinearLayout>(R.id.layoutDevaluacion).visibility = View.GONE
        }

        vista!!.findViewById<TextView>(R.id.historiaTvVentaDivisa).text = getString(R.string.convertibilidad_venta_base_1)+" "+ monederoDivisa.toString() + " " + this.divisa!!.codigo + " "+getString(R.string.convertibilidad_venta_base_2) + " "+"%.2f".format(ventaDivisa).toString() + " " + base!!.codigo
        vista!!.findViewById<TextView>(R.id.historiaTvCompraDivisa).text = getString(R.string.convertibilidad_compra_divisa_1) + " "+monederoBase + " " + base!!.codigo + " "+getString(R.string.convertibilidad_compra_divisa_2) + " "+"%.2f".format(compraDivisa).toString() + " " + this.divisa!!.codigo
        /**----------------------------- END CONVERTIBILIDAD ---------------------------- **/
    }

    override fun onValueSelected(entry: Entry?) {
        val viewPager = vista!!.findViewById<ViewPager>(R.id.viewPagerGraficos)
        val adapter = viewPager.adapter as GraficoHistorialAdapter
        var entryData = entry
        if(entry === null) {
            entryData = divisa!!.timeSeriesData.last()
        }
        setDetallesSegunFecha(entryData!!.y, entryData!!.x)
        adapter.setSelectedValue(entry)
    }

    fun valorMonedaSegunBase(valor: Float): String {
        return valor.toString() + " " + this.divisaBase()!!.codigo
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (this.divisa!!.hayDatos()) {
            vista = inflater.inflate(R.layout.fragment_historia, container, false)

            val pageAdapter = GraficoHistorialAdapter(childFragmentManager!!, this.context!!)

            pageAdapter.agregarDivisa(this.divisa!!)
            val viewPager = vista!!.findViewById<ViewPager>(R.id.viewPagerGraficos)
            viewPager.adapter = pageAdapter
            vista!!.findViewById<TabLayout>(R.id.tabsGraficoHistorial).setupWithViewPager(viewPager)

            this.init(vista!!)
            return vista
        } else {
            vista = inflater.inflate(R.layout.fragment_error_conexion, container, false)
            vista!!.findViewById<TextView>(R.id.tvPais).text = this.context!!.getString(this.divisa!!.paisResource!!)
            vista!!.findViewById<TextView>(R.id.tvDivisa).text = this.context!!.getString(this.divisa!!.monedaResource!!)
            vista!!.findViewById<ImageView>(R.id.iwBandera).setImageResource(this.divisa!!.bandera!!)
            return vista
        }
    }

    private fun divisaBase(): Divisa? {
        val mainActivity = this.context!! as MainActivity
        return mainActivity.divisaBase
    }

    private fun init(vista: View) {
        vista.findViewById<TextView>(R.id.tvPais).text = this.getString(this.divisa!!.paisResource!!)
        vista.findViewById<TextView>(R.id.tvDivisa).text = this.getString(this.divisa!!.monedaResource!!)
        vista.findViewById<ImageView>(R.id.iwBandera).setImageResource(this.divisa!!.bandera!!)

        if (this.divisa!!.hayDatos()) {

            var ultimoCambio = this.divisa!!.ultimoCambio()
            var ultimoCambioEnPorcentaje = this.divisa!!.ultimoCambioEnPorcentaje()

            /**----------------------------- HEADERS ---------------------------------- **/
            vista.findViewById<TextView>(R.id.tvValorDivisa).text = this.valorMonedaSegunBase(this.divisa!!.valor!!)

            vista.findViewById<TextView>(R.id.tvAmbosCambios).text =
                    ("%.3f".format(ultimoCambio) + " (" + "%.3f".format(ultimoCambioEnPorcentaje) + "%)")

            //vista.findViewById<TextView>(R.id.tvCambio).text = ("$"+"%.4f".format(ultimoCambio))

            /**----------------------------- END HEADERS ------------------------------- **/

            var resource = 1
            var moneda = this.divisa!!.moneda!!.toLowerCase()
            var detalle = ""
            var colorDetalle = 1
            var base = this.divisaBase()

            if (ultimoCambio < 0) {
                resource = R.drawable.ic_arrow_drop_down
                detalle = "(%.4f".format(ultimoCambioEnPorcentaje) + "%)"
                colorDetalle = R.color.colorTextNegative
            } else {
                resource = R.drawable.ic_arrow_drop_up
                detalle = "(+" + ultimoCambioEnPorcentaje.toString() + "%)"
                colorDetalle = R.color.colorTextPositive
            }

            /**----------------------------- DETALLES ---------------------------------- **/
            //vista.findViewById<ImageView>(R.id.ivSubaBaja).setImageResource(resource)

            vista.findViewById<ImageView>(R.id.ivUpDown).setImageResource(resource)
            vista.findViewById<TextView>(R.id.tvValorDivisa).setTextColor(resources.getColor(colorDetalle))
            vista.findViewById<TextView>(R.id.tvCodigoDivisa).setTextColor(resources.getColor(colorDetalle))
            vista.findViewById<TextView>(R.id.tvAmbosCambios).setTextColor(resources.getColor(colorDetalle))

            //var tvDetalle = vista.findViewById<TextView>(R.id.tvDetalleSubaBaja)
            //tvDetalle.setTextColor(resources.getColor(colorDetalle))

            /*vista.findViewById<TextView>(R.id.tvDetalleSubaBaja).text = detalle
            vista.findViewById<TextView>(R.id.tvCambio).setTextColor(resources.getColor(colorDetalle))*/

            vista.findViewById<TextView>(R.id.tvValorMonedaBase).text = "1 " + base!!.codigo
            vista.findViewById<TextView>(R.id.tvUnidadDivisa).text = "1 " + this.divisa!!.codigo
            setDetallesSegunFecha(this.divisa!!.valor!!, this.divisa!!.timeSeriesData.last().x)

            /**----------------------------- END DETALLES ---------------------------------- **/

            /**----------------------------- NOTIFICACIONES -------------------------------- **/
            vista.findViewById<Switch>(R.id.historiaNotiSuba).isChecked = Preferencias.getNotificacionesParaSubaDivisa(this.context!!, this.divisa!!.codigo!!)
            vista.findViewById<Switch>(R.id.historiaNotiBaja).isChecked = Preferencias.getNotificacionesParaBajaDivisa(this.context!!, this.divisa!!.codigo!!)

            vista.findViewById<Switch>(R.id.historiaNotiSuba).setOnCheckedChangeListener { buttonView, isChecked ->
                Preferencias.notificacionesParaSubaDivisa(this.context!!, this.divisa!!.codigo!!, isChecked)
                if (isChecked) FirebaseMessaging.getInstance().subscribeToTopic("suba_" + this.divisa!!.codigo!!)
                else FirebaseMessaging.getInstance().unsubscribeFromTopic("suba_" + this.divisa!!.codigo!!)
            }

            vista.findViewById<Switch>(R.id.historiaNotiBaja).setOnCheckedChangeListener { buttonView, isChecked ->
                Preferencias.notificacionesParaBajaDivisa(this.context!!, this.divisa!!.codigo!!, isChecked)
                if (isChecked) FirebaseMessaging.getInstance().subscribeToTopic("baja_" + this.divisa!!.codigo!!)
                else FirebaseMessaging.getInstance().unsubscribeFromTopic("baja_" + this.divisa!!.codigo!!)
            }
            /**----------------------------- END NOTIFICACIONES ---------------------------- **/
        }
    }
}