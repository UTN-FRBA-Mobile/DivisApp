package com.gradientepolimorfico.monedapp.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Entities.DetalleDivisa
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Services.DetalleMonedaService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.Disposable
import com.ms.square.android.expandabletextview.ExpandableTextView

class DetalleFragment : Fragment() {
    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var vista = inflater.inflate(R.layout.fragment_detalle, container, false)

        var index = arguments!!.getInt("divisaIndex")
        var divisa : Divisa? = null
        if(index!=-1){
             divisa = (context as MainActivity).getDivisaByPosition(index)
        }
        else{
             divisa = (context as MainActivity).divisaBase
        }
        this.mostrarDetalle(divisa!!, vista)

        return vista
    }

    private fun mostrarDetalle(divisa: Divisa, vista : View){
        val detalleService = DetalleMonedaService.create()
        var disposable = detalleService.getDetalleDivisa(divisa.codigo!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            this.rellenarVista(divisa, result, vista)
                        },
                        {
                            error ->
                            Log.d("I","DETALLE-"+error.message)
                            Toast.makeText(activity, "Información no disponible!", Toast.LENGTH_SHORT).show()
                        }
                )
    }

    private fun rellenarVista(divisa: Divisa, divisaDetalle: DetalleDivisa, vista: View){
        vista.findViewById<ImageView>(R.id.detalleIwBandera).setImageResource(divisa.bandera!!)
        vista.findViewById<TextView>(R.id.detalleTvPais).text = divisa.pais!!
        vista.findViewById<TextView>(R.id.detalleTvMoneda).text = divisa.moneda!! +" - "+ divisa.codigo!!

        vista.findViewById<ExpandableTextView>(R.id.detalle_historia_expandible).text = divisaDetalle.detalle!!
        //vista.findViewById<TextView>(R.id.detalleFhistoria).text = divisaDetalle.detalle!!
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}