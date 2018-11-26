package com.gradientepolimorfico.monedapp.Activities

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.view.View
import com.facebook.CallbackManager
import com.facebook.ProfileTracker
import com.facebook.login.LoginManager
import com.google.firebase.messaging.FirebaseMessaging
import com.gradientepolimorfico.monedapp.Adapters.HistoriaPageAdapter
import com.gradientepolimorfico.monedapp.Entities.Configuracion
import com.gradientepolimorfico.monedapp.Entities.Divisa
import com.gradientepolimorfico.monedapp.Entities.Usuario
import com.gradientepolimorfico.monedapp.Factories.FactoryDivisa
import com.gradientepolimorfico.monedapp.Fragments.*
import com.gradientepolimorfico.monedapp.R
import com.gradientepolimorfico.monedapp.Storage.Preferencias
import com.gradientepolimorfico.monedapp.Permissions.Permissions
import java.security.Permission

class MainActivity : AppCompatActivity() {
    var fab: FloatingActionButton? = null
    var usuario = Usuario()

    var callbackManager: CallbackManager? = CallbackManager.Factory.create()
    var profileTracker: ProfileTracker? = null

    var divisasFragment: DivisasFragment? = null
    var configFragment: ConfigFragment? = null
    var perfilFragment: PerfilFragment? = null
    var loginFragment: LoginFragment? = null
    var historialFragment: HistorialFragment? = null
    var divisaBase: Divisa? = null
    var divisaPreferida: Divisa? = null
    var historialAdapter: HistoriaPageAdapter? = null
    var cambioEnMonedaBase: Boolean = false
    var posicionPantallaParaNotificacion: Int? = null

    private fun init() {

        if (Preferencias.getMonedaBase(this) != null) {
            this.divisaBase = FactoryDivisa.create(Preferencias.getMonedaBase(this)!!)
        }

        if (this.divisaBase == null) {
            this.iniciarPrimeraVez()
        }

        this.usuario.nombreDeUsuario = "Steve"

        var configuracion = Configuracion()

        this.cargarDivisasEnConfiguracion(configuracion)

        this.usuario.configuracion = configuracion
    }

    private fun cargarDivisasEnConfiguracion(configuracion: Configuracion) {
        configuracion.divisas.clear()
        var monedasSuscritas = Preferencias.getMonedasSuscritas(this)
        monedasSuscritas!!.forEach { m ->
            this.iniciarDivisaEn(configuracion, m)
        }
    }

    private fun iniciarDivisaEn(configuracion: Configuracion, codigoDivisa: String) {
        var divisa = FactoryDivisa.create(codigoDivisa)
        Preferencias.recuperarDivisa(this, divisa!!)
        configuracion.agregarDivisa(divisa!!)
    }

    private fun iniciarFragments() {
        this.divisasFragment = DivisasFragment()
        this.configFragment = ConfigFragment()
        this.historialFragment = HistorialFragment()
        this.perfilFragment = PerfilFragment()
        this.loginFragment = LoginFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.iniciarBottomNav()
        this.init()
        this.iniciarFragments()

        this.paisActualConPermiso()

        var codigoDivisa = this.intent?.extras?.getString("codigoDivisa")
        if (codigoDivisa != null) {
            this.mostrarPantallaSegunNotificacion(codigoDivisa)
        }
        else{
            this.irAPrincipal()
        }


    }

    private fun paisActualConPermiso(){
        var razon = "Podemos cambiar la moneda base según tu ubicación."
        Permissions.checkForPermissions(this, android.Manifest.permission.ACCESS_FINE_LOCATION,razon, object : Permissions.Callback{
            override fun onSuccess() {
                paisActual()
            }
        })
    }

    private fun paisActual(){
        var locationManager = (getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        var lastPosition = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val geocoder = Geocoder(this)
        var location = geocoder.getFromLocation(lastPosition.latitude,lastPosition.longitude,1)
        //Log.d("I","MAINACT: "+location.first().countryName)
        var pais = location.first().countryName
        var divisa = FactoryDivisa.codigoDivisaSegunPais(pais)
        Preferencias.setMonedaBase(this,divisa)
    }

    private fun iniciarPrimeraVez(){
        this.paisActualConPermiso()
        //Preferencias.setMonedaBase(this, FactoryDivisa.divisaBaseDefault)
        FactoryDivisa.divisasDisponibles.forEach { d ->
            if (d != FactoryDivisa.divisaBaseDefault) {
                this.iniciarPrimeraVezDivisa(d)
            }
        }

        Preferencias.setNotificacionesActivas(this, true)
    }

    private fun iniciarPrimeraVezDivisa(codigoDivisa: String) {
        Preferencias.suscribirMoneda(this, codigoDivisa)
        Preferencias.notificacionesParaSubaDivisa(this, codigoDivisa, true)
        Preferencias.notificacionesParaBajaDivisa(this, codigoDivisa, true)
        FirebaseMessaging.getInstance().subscribeToTopic("suba_" + codigoDivisa)
        FirebaseMessaging.getInstance().subscribeToTopic("baja_" + codigoDivisa)
    }

    private fun mostrarPantallaSegunNotificacion(fragment: String) {
        this.posicionPantallaParaNotificacion = this.getPosicionDivisaDeCodigo(fragment)
        this.mostrarFragment(R.id.fragment_container, this.historialFragment!!)
    }

    public fun iniciarFragmentsPagers(pager: ViewPager?, manager: FragmentManager) {
        val adapter = HistoriaPageAdapter(manager)
        adapter.agregarDivisas(getDivisas())
        adapter.notifyDataSetChanged()
        pager?.adapter = adapter

        if (this.posicionPantallaParaNotificacion != null) {
            pager?.currentItem = this.posicionPantallaParaNotificacion!!
        }
    }

    private fun iniciarBottomNav() {
        val navigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView

        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_divisas -> this.mostrarFragment(R.id.fragment_container, this.divisasFragment!!)

                R.id.nav_historial -> this.mostrarFragment(R.id.fragment_container, this.historialFragment!!)

                R.id.nav_configuracion -> this.mostrarFragment(R.id.fragment_container, this.configFragment!!)

                R.id.nav_perfil -> this.mostrarFragment(R.id.fragment_container, this.loginFragment!!)
            }
            true
        }
    }

    private fun mostrarFragment(containerViewId: Int, fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                //.setCustomAnimations(R.anim.fragment_slide_left_enter,R.anim.fragment_slide_left_exit, R.anim.fragment_slide_right_enter,R.anim.fragment_slide_right_exit)
                .replace(containerViewId, fragment)
                .commit()
    }

    fun irAPrincipal() {
        this.mostrarFragment(R.id.fragment_container, DivisasFragment())
    }

    fun irAPerfil() {
        this.mostrarFragment(R.id.fragment_container, PerfilFragment())
    }

    fun irALoginOptions() {
        this.mostrarFragment(R.id.fragment_container, LoginFragment())
    }

    fun getDivisas(): ArrayList<Divisa> {
        return usuario.configuracion!!.divisas
    }

    fun getDivisaByPosition(pos: Int): Divisa {
        return usuario.configuracion!!.divisas[pos]
    }

    fun getPosicionDivisaDeCodigo(codigo: String): Int {
        return this.usuario.configuracion!!.divisas.indexOfFirst { d -> d.codigo!! == codigo }
    }

    fun cambiarMonedaBase() {
        val codigoMonedaBase = Preferencias.getMonedaBase(this)!!
        this.divisaBase = FactoryDivisa.create(codigoMonedaBase)
        Preferencias.desuscribirMoneda(this, codigoMonedaBase)
        this.reloadDivisas()
    }

    fun cambiarDivisaPreferida() {
        val codigoMonedaBase = Preferencias.getDivisaIntercambioPreferida(this)!!
        this.divisaPreferida = FactoryDivisa.create(codigoMonedaBase)
    }

    fun reloadDivisas() {
        this.cambioEnMonedaBase = true
        this.cargarDivisasEnConfiguracion(this.usuario.configuracion!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    fun desloguearFacebook() {
        LoginManager.getInstance().logOut()
    }
}
