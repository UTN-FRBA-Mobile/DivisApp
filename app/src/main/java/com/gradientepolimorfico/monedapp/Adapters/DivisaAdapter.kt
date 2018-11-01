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
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Fragments.DivisasFragment
import com.gradientepolimorfico.monedapp.Fragments.PrincipalFragment
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.configureForList
import kotlinx.android.synthetic.main.fragment_divisa.view.*
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.github.mikephil.charting.data.Entry
import com.gradientepolimorfico.monedapp.Services.ExchangeRateResponse
import com.gradientepolimorfico.monedapp.Services.MonedasService
import com.gradientepolimorfico.monedapp.Services.RetrofitClientInstance
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import retrofit2.Call
import retrofit2.Callback
import java.util.*


class DivisaAdapter: RecyclerView.Adapter<DivisaAdapter.MyViewHolder>{
    private var divisas:ArrayList<Divisa>? = null
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
        return this.divisas!!.size
    }

    private fun monedaBase() : String?{
        var monedaBase = Preferencias.getMonedaBase(this.context!!)
        if(monedaBase.isNullOrEmpty()){
            monedaBase = "ARS"
        }
        return monedaBase
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var unaDivisa = this.getDivisa(position)
        holder.view.findViewById<TextView>(R.id.tvPais).text = unaDivisa.pais
        holder.view.findViewById<TextView>(R.id.tvDivisa).text = unaDivisa.moneda
        holder.view.findViewById<ImageView>(R.id.iwBandera).setImageResource(unaDivisa.bandera!!)
        holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).setNoDataTextColor(Color.LTGRAY)
        holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).setNoDataText("Cargando...")

        if(!unaDivisa.dataRequested && !unaDivisa.hayDatos()) {
            unaDivisa.dataRequested = true
            val service = RetrofitClientInstance.retrofitInstance!!.create<MonedasService>(MonedasService::class.java)
            val call = service.getTimeSeries(unaDivisa.codigo, this.monedaBase())
            Log.d("DivisApp", "------------------------ Getting currency exchange data for: " + unaDivisa.codigo)
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
                    unaDivisa.fecha = body.lastRefreshed
                    unaDivisa.valor = body.exchangeRate
                    holder.view.findViewById<TextView>(R.id.tvValorDivisa).text = "$" + unaDivisa.valor.toString()
                    unaDivisa.timeSeriesData = body.data
                    unaDivisa.timeSeriesData.reverse()
                    holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).configureForList(holder.view.context, unaDivisa.timeSeriesData)
                    holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).invalidate()
                    holder.itemView.setOnClickListener {
                        var args = Bundle()
                        args.putInt("DivisaIndex", position)
                        var fragment = PrincipalFragment()
                        fragment.arguments = args
                        (context as MainActivity).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .commit()
                    }
                }

            })
        }
        else {
            holder.view.findViewById<TextView>(R.id.tvValorDivisa).text = "$" + unaDivisa.valor.toString()
            holder.view.findViewById<LineChart>(R.id.priceHistoricGraph).configureForList(holder.view.context, unaDivisa.timeSeriesData)
            holder.itemView.setOnClickListener {
                var args = Bundle()
                args.putString("tipo", unaDivisa.codigo)
                var fragment = PrincipalFragment()
                fragment.arguments = args
                (context as MainActivity).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
            }
        }
//        TODO("refactorizar esta asquerosidad")
//        TODO("cambiar el color del grafico dependiendo al trend")
    }

    private fun getDivisa(position: Int) : Divisa{
        return this.divisas!!.get(position)
    }
}