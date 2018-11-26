package com.gradientepolimorfico.monedapp.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.configureForList
import android.util.Log
import android.widget.Toast
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.gradientepolimorfico.monedapp.Fragments.DetalleFragment
import com.gradientepolimorfico.monedapp.Services.ExchangeRateResponse
import com.gradientepolimorfico.monedapp.Services.MonedasService
import com.gradientepolimorfico.monedapp.Services.RetrofitClientInstance
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import retrofit2.Call
import retrofit2.Callback
import java.security.KeyStore
import java.util.*
import kotlin.collections.ArrayList

class DivisaAdapter: RecyclerView.Adapter<DivisaAdapter.MyViewHolder>{
    var divisas:ArrayList<Divisa>? = null
    private var context:Context? = null

    constructor(divisas:ArrayList<Divisa>, context: Context) : super(){
        this.divisas = divisas
        this.context = context
    }

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DivisaAdapter.MyViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.fragment_divisa,parent,false)
        return MyViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return  (this.context!! as MainActivity).getDivisas().count()
        return this.divisas!!.size
    }

    private fun monedaBase() : String?{
        var monedaBase = Preferencias.getMonedaBase(this.context!!)
        if(monedaBase.isNullOrEmpty()){
            monedaBase = "ARS"
        }
        return monedaBase
    }

    private fun desactualizada(unaDivisa: Divisa) : Boolean{
        val desactualizada = (Date().time - unaDivisa.lastUpdated.time) > Preferencias.getIntervaloNotificacionesAsLong(this.context!!)
        return (!unaDivisa.hayDatos() || desactualizada)
    }

    private fun cambioMonedaBase(divisa: Divisa) : Boolean{
        return divisa.hayDatos() && divisa.from!= ('"'.toString()+this.monedaBase()+'"'.toString())
        //return divisa.hayDatos() && divisa.from!= this.monedaBase()!!
    }

    fun requestDataFromAPI(unaDivisa: Divisa, holder: MyViewHolder) {
        if(this.desactualizada(unaDivisa) || this.cambioMonedaBase(unaDivisa)) {
            unaDivisa.dataRequested = true
            val service = RetrofitClientInstance.retrofitInstance!!.create<MonedasService>(MonedasService::class.java)
            val call = service.getTimeSeries(unaDivisa.codigo, this.monedaBase())
            Log.d("MAINACT--", "------------------------ Getting currency exchange data for: " + unaDivisa.codigo)
            Log.d("MAINACT--", this.desactualizada(unaDivisa).toString()+" "+this.cambioMonedaBase(unaDivisa).toString())

            call.enqueue(object : Callback<ExchangeRateResponse> {
                override fun onFailure(call: Call<ExchangeRateResponse>?, t: Throwable?) {
                    Log.e("DivisApp", Log.getStackTraceString(t))
                    unaDivisa.dataRequested = false
                    holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).setNoDataText("Carga fallida, reintente nuevamente")
                    holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).invalidate()
                    Toast.makeText(holder.view.context, "No se pudo cargar información sobre la divisa: " + unaDivisa.moneda, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ExchangeRateResponse>?, response: retrofit2.Response<ExchangeRateResponse>?) {
                    var body = response!!.body()!!

                    unaDivisa.setDatos(body)

                    Preferencias.saveDivisa(context!!, unaDivisa)

                    holder.view.findViewById<TextView>(R.id.tvValorDivisa).text = ("$" + unaDivisa.valor.toString())
                    holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).configureForList(holder.view.context, unaDivisa.timeSeriesData)
                    holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).invalidate()
                }

            })

        }
        else {
            holder.view.findViewById<TextView>(R.id.tvValorDivisa).text = ("$" + unaDivisa.valor.toString())
            Log.d("I",  "MAINACT-- CHECKEO VACIO"+unaDivisa.timeSeriesData.isEmpty().toString()+" "+unaDivisa.timeSeriesData.count().toString())
            holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).configureForList(holder.view.context, unaDivisa.timeSeriesData)
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var unaDivisa = (this.context!! as MainActivity).getDivisaByPosition(position)
        //var unaDivisa = this.getDivisa(position)

        holder.view.findViewById<TextView>(R.id.tvPais).text = unaDivisa.pais
        holder.view.findViewById<TextView>(R.id.tvDivisa).text = unaDivisa.moneda
        holder.view.findViewById<ImageView>(R.id.iwBandera).setImageResource(unaDivisa.bandera!!)
        holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).data = null
        holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).setNoDataTextColor(Color.LTGRAY)
        holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).setNoDataText("Cargando...")

        requestDataFromAPI(unaDivisa, holder)

        //Cambio de color según aumento o baja
        var color: String? = null
        if( unaDivisa.ultimoCambio() < 0) color = "#FF5252"
        else color = "#00E789"

        holder.view.findViewById<TextView>(R.id.tvValorDivisa).textColor = Color.parseColor(color).toLong()

        this.listenerDetalle(holder, position)
    }

    fun String.toColor(): Int = Color.parseColor(this)

    private fun listenerDetalle(holder: MyViewHolder, position: Int){
        holder.itemView.setOnClickListener {
            var args = Bundle()
            args.putInt("divisaIndex", position)
            var fragment = DetalleFragment()
            fragment.arguments = args
            (context as MainActivity).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
        }
    }

    var TextView.textColor: Long
        get() {
            return this.textColor
        }
        set(value: Long) {
            this.setTextColor(value.toInt())
        }
}