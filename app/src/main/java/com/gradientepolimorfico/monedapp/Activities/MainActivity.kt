package com.gradientepolimorfico.monedapp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import com.gradientepolimorfico.monedapp.Fragments.DivisasFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.View
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Entities.Configuracion
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Entities.Usuario
import com.gradientepolimorfico.monedapp.Fragments.HistorialFragment
import com.gradientepolimorfico.monedapp.Fragments.ConfigFragment
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import android.util.Log
import com.gradientepolimorfico.monedapp.Adapters.HistoriaPageAdapter
import com.gradientepolimorfico.monedapp.Factories.FactoryDivisa
import com.gradientepolimorfico.monedapp.Fragments.HistoriaFragment


class MainActivity : AppCompatActivity(){
    var fab: FloatingActionButton? = null
    var usuario = Usuario()

    var divisasFragment     : DivisasFragment?      = null
    var configFragment      : ConfigFragment?       = null
    var historialFragment   : HistorialFragment?    = null
    var divisaBase          : Divisa?               = null
    var historialAdapter    : HistoriaPageAdapter?  = null
    var fragments           = ArrayList<Fragment>()

    private fun init(){
        this.usuario.nombreDeUsuario = "Steve"

        var configuracion = Configuracion()

        if(Preferencias.getMonedaBase(this) != null){
            this.divisaBase = FactoryDivisa.create(Preferencias.getMonedaBase(this)!!)
        }

        var monedasSuscritas = Preferencias.getMonedasSuscritas(this)
        monedasSuscritas!!.forEach {
            m -> this.iniciarDivisaEn(configuracion, m)
        }

        this.usuario.configuracion = configuracion
    }

    private fun iniciarDivisaEn(configuracion : Configuracion, codigoDivisa : String){
        var divisa = FactoryDivisa.create(codigoDivisa)
        configuracion.agregarDivisa(divisa!!)
    }


    private fun iniciarFragments(){
        this.divisasFragment    = DivisasFragment()
        this.configFragment     = ConfigFragment()
        this.historialFragment  = HistorialFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Preferencias.getMonedaBase(this) ==null){
            Preferencias.setMonedaBase(this, "ARS")
            Preferencias.suscribirMoneda(this,"USD")
            Preferencias.suscribirMoneda(this,"EUR")
            Preferencias.suscribirMoneda(this,"JPY")
        }
        setContentView(R.layout.activity_main)

        this.iniciarBottomNav()
        this.init()
        this.iniciarFragments()
        this.irAPrincipal()
    }

    public fun fragmentsPagersIniciados() : Boolean{
        return this.historialAdapter != null
    }

    public fun getPagersAdapter() : HistoriaPageAdapter?{
        return this.historialAdapter
    }

    public fun iniciarFragmentsPagers(pager: ViewPager?, manager : FragmentManager){
        val adapter = HistoriaPageAdapter(manager)

        if(this.fragments.isEmpty()){
            var iterador = this.getDivisasIterator()
            iterador.forEach {
                d ->
                var fragment = HistoriaFragment()
                fragment.agregarDivisa(d)
                this.fragments.add(fragment)
                //adapter!!.addFragment(fragment,d.moneda!!)
            }
        }
        adapter.agregarFragments(this.fragments)
        pager?.adapter = adapter
    }

    private fun iniciarBottomNav() {
        val navigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_divisas        -> this.mostrarFragment(R.id.fragment_container,this.divisasFragment!!)

                R.id.nav_historial      -> this.mostrarFragment(R.id.fragment_container,this.historialFragment!!)

                R.id.nav_configuracion  -> this.mostrarFragment(R.id.fragment_container,this.configFragment!!)

                R.id.nav_perfil         -> this.mostrarFragment(R.id.fragment_container,this.divisasFragment!!)
            }
            true
        }
    }

    private fun mostrarFragment(containerViewId : Int, fragment : Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(containerViewId,fragment)
                .commit()
    }


    private fun irAPrincipal(){
        this.mostrarFragment(R.id.fragment_container, DivisasFragment())
    }

    public fun getDivisas() : ArrayList<Divisa>{
        return usuario.configuracion!!.divisas
    }

    public fun getDivisaByPosition(pos: Int): Divisa {
        return usuario.configuracion!!.divisas[pos]
    }

    public fun getDivisasIterator() : Iterator<Divisa>{
        return this.usuario.configuracion!!.divisas.iterator()
    }

}
