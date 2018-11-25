package com.gradientepolimorfico.monedapp.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gradientepolimorfico.monedapp.Activities.MainActivity
import com.gradientepolimorfico.monedapp.Entities.DetalleDivisa
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Services.DetalleMonedaService
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.disposables.Disposable
import com.ms.square.android.expandabletextview.ExpandableTextView
import android.support.design.widget.FloatingActionButton
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gradientepolimorfico.monedapp.Entities.Ubicacion


class DetalleFragment : Fragment(), OnMapReadyCallback  {
    var disposable: Disposable? = null
    var map: MapView? = null
    var ubicacion: Ubicacion? = null
    var googleMap: GoogleMap? = null
    var divisa: Divisa? = null

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
        this.divisa = divisa
        this.mostrarDetalle(divisa!!, vista)
        this.botonVolver(vista)
        this.map = vista.findViewById(R.id.mapView)
        this.iniciarMapa(savedInstanceState)
        return vista
    }

    private fun mostrarDetalle(divisa: Divisa, vista : View){
        val detalleService = DetalleMonedaService.create()
        this.rellenarVista(divisa, vista)
        var disposable = detalleService.getDetalleDivisa(divisa.codigo!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            this.ubicacion = result.ubicacion
                            this.agregarMark(this.ubicacion!!)
                            this.rellenarDetalle(result, vista)
                        },
                        {
                            error ->
                            Log.d("I","DETALLE-"+error.message)
                            Toast.makeText(activity, "Información no disponible!", Toast.LENGTH_SHORT).show()
                        }
                )
    }

    private fun rellenarDetalle(divisaDetalle: DetalleDivisa, vista: View){
        vista.findViewById<ExpandableTextView>(R.id.detalle_historia_expandible).text = divisaDetalle.detalle!!
    }

    private fun rellenarVista(divisa: Divisa, vista: View){
        vista.findViewById<ImageView>(R.id.detalleIwBandera).setImageResource(divisa.bandera!!)
        vista.findViewById<TextView>(R.id.detalleTvPais).text = divisa.pais!!
        vista.findViewById<TextView>(R.id.detalleTvMoneda).text = divisa.moneda!! +" - "+ divisa.codigo!!
        vista.findViewById<TextView>(R.id.detalleTVCodigo).text = divisa.codigo!!
        var monedero = Preferencias.monedero(this.context!!, divisa.codigo!!)
        vista.findViewById<EditText>(R.id.detalleETCantidad).setText(monedero.toString())

        this.cambioEnMonedero(vista,divisa)
    }

    private fun cambioEnMonedero(vista: View, divisa: Divisa){
        var editor = vista.findViewById<EditText>(R.id.detalleETCantidad)
        editor.setOnEditorActionListener() {
            v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    var valorMonedero = v.findViewById<EditText>(R.id.detalleETCantidad).text
                    Preferencias.saveMonedero(this.context!!,divisa.codigo!!, valorMonedero.toString().toFloat())
                    this.ocultarTeclado(vista)
                    vista.findViewById<LinearLayout>(R.id.detalleLLY).requestFocus()
                    true
                } else {
false
                }
        }
    }

    private fun ocultarTeclado(vista: View){
        val imm = this.context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(vista.windowToken, 0)
    }

    private fun botonVolver(vista: View){
        vista.findViewById<FloatingActionButton>(R.id.detalleBackButton).setOnClickListener {
            (this.context!! as MainActivity).irAPrincipal()
        }
    }

    override fun onStop() {
        super.onStop()
        map?.onStop()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
        map?.onPause()
    }

    override fun onResume() {
        super.onResume()
        map?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        map?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        map?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map?.onSaveInstanceState(outState)
    }

    private fun iniciarMapa(savedInstanceState: Bundle?){
        this.map?.onCreate(savedInstanceState)
        this.map?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap?) {
        this.googleMap = p0
        if(this.ubicacion != null){
            this.agregarMark(this.ubicacion!!)
        }
    }

    private fun agregarMark(posicion : Ubicacion){
        try {

            MapsInitializer.initialize((this.context!! as MainActivity))
            var mMap = this.googleMap
            val point = LatLng(posicion.x.toDouble(),posicion.y.toDouble())
            mMap!!.addMarker(MarkerOptions().position(point).title(this.divisa?.pais))

            var zoom = (6.0).toFloat()
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(point,zoom))

        } catch (e : GooglePlayServicesNotAvailableException) {

            e.printStackTrace()

        }
    }
}