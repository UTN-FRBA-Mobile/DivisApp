package divisapp.dadm.frba.utn.edu.ar.divisapp.Fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import divisapp.dadm.frba.utn.edu.ar.divisapp.Adapters.DivisaAdapter
import divisapp.dadm.frba.utn.edu.ar.divisapp.Entities.Divisa
import divisapp.dadm.frba.utn.edu.ar.divisapp.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_divisas.*
import kotlinx.android.synthetic.main.fragment_divisas.view.*

class DivisasFragment : Fragment(){
    private var divisas:ArrayList<Divisa>? = null
    private var divisaAdapter:DivisaAdapter? = null
    private var recyclerView:RecyclerView? = null
    private var viewManager:RecyclerView.LayoutManager? = null

    private fun inicializar(){
        this.divisas?.add(Divisa("Estados Unidos","Dólar",R.drawable.pais_us,(40.40).toFloat()))
        this.divisas?.add(Divisa("Venezuela","Bolivar",R.drawable.pais_ve,(20.40).toFloat()))
        this.divisas?.add(Divisa("España","Euro",R.drawable.pais_es,(50.40).toFloat()))
        this.viewManager = LinearLayoutManager(context)
        this.divisaAdapter = DivisaAdapter(this.divisas!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.divisas = ArrayList<Divisa>()
        this.inicializar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var vista= inflater.inflate(R.layout.fragment_divisas, container, false)
        this.recyclerView = vista.findViewById(R.id.rvDivisas)
        this.recyclerView!!.layoutManager = this.viewManager
        this.recyclerView!!.adapter = this.divisaAdapter
        return vista
    }
}