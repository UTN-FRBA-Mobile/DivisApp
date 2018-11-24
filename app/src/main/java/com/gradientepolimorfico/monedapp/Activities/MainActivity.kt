package com.gradientepolimorfico.monedapp.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.View
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Entities.Configuracion
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Entities.Usuario
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import android.util.Log
import com.gradientepolimorfico.monedapp.Adapters.HistoriaPageAdapter
import com.gradientepolimorfico.monedapp.Factories.FactoryDivisa
import com.gradientepolimorfico.monedapp.Fragments.*


class MainActivity : AppCompatActivity(){
    var fab: FloatingActionButton? = null
    var usuario = Usuario()

    var divisasFragment     : DivisasFragment?      = null
    var configFragment      : ConfigFragment?       = null
    var perfilFragment      : PerfilFragment?       = null
    var historialFragment   : HistorialFragment?    = null
    var divisaBase          : Divisa?               = null
    var historialAdapter    : HistoriaPageAdapter?  = null
    var cambioEnMonedaBase  : Boolean = false

    private fun init(){
        this.usuario.nombreDeUsuario = "Steve"

        var configuracion = Configuracion()

        if(Preferencias.getMonedaBase(this) != null){
            this.divisaBase = FactoryDivisa.create(Preferencias.getMonedaBase(this)!!)
        }

        this.cargarDivisasEnConfiguracion(configuracion)

        this.usuario.configuracion = configuracion
    }

    private fun cargarDivisasEnConfiguracion(configuracion: Configuracion){
        configuracion.divisas.clear()
        var monedasSuscritas = Preferencias.getMonedasSuscritas(this)
        monedasSuscritas!!.forEach {
            m -> this.iniciarDivisaEn(configuracion, m)
        }
    }

    private fun iniciarDivisaEn(configuracion : Configuracion, codigoDivisa : String){
        var divisa = FactoryDivisa.create(codigoDivisa)
       // Preferencias.recuperarDivisa(this, divisa!!)
        //Log.d("I","MAINACT--"+divisa!!.hayDatos().toString())
        configuracion.agregarDivisa(divisa!!)
    }


    private fun iniciarFragments(){
        this.divisasFragment    = DivisasFragment()
        this.configFragment     = ConfigFragment()
        this.historialFragment  = HistorialFragment()
        this.perfilFragment     = PerfilFragment()
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

    public fun iniciarFragmentsPagers(pager: ViewPager?, manager : FragmentManager){
        val adapter = HistoriaPageAdapter(manager)
        adapter.agregarDivisas(getDivisas())
        adapter.notifyDataSetChanged()
        pager?.adapter = adapter
    }

    private fun iniciarBottomNav() {
        val navigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_divisas        -> this.mostrarFragment(R.id.fragment_container,this.divisasFragment!!)

                R.id.nav_historial      -> this.mostrarFragment(R.id.fragment_container,this.historialFragment!!)

                R.id.nav_configuracion  -> this.mostrarFragment(R.id.fragment_container,this.configFragment!!)

                R.id.nav_perfil         -> this.mostrarFragment(R.id.fragment_container,this.perfilFragment!!)
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

    fun irAPrincipal(){
        this.mostrarFragment(R.id.fragment_container, DivisasFragment())
    }

    fun getDivisas() : ArrayList<Divisa>{
        return usuario.configuracion!!.divisas
    }

    fun getDivisaByPosition(pos: Int): Divisa {
        return usuario.configuracion!!.divisas[pos]
    }

    fun cambiarMonedaBase(){
        val codigoMonedaBase = Preferencias.getMonedaBase(this)!!
        this.divisaBase = FactoryDivisa.create(codigoMonedaBase)
        Preferencias.desuscribirMoneda(this, codigoMonedaBase)
        this.reloadDivisas()
    }

    fun reloadDivisas(){
        this.cambioEnMonedaBase = true
        this.cargarDivisasEnConfiguracion(this.usuario.configuracion!!)
    }
}
